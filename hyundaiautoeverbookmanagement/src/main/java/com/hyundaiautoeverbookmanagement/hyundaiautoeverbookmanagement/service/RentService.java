package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.service;

import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.type.BookStatus;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.RentResponseDTO;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.RentRequestDTO;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.*;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.exception.CopyException;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.exception.RentException;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.exception.UserException;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.repository.BookRepository;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.repository.CopyRepository;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.repository.MemberRepository;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.repository.RentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.util.SecurityUtil.checkAdminAuthority;

@Service
@RequiredArgsConstructor
@Slf4j
public class RentService {

    private final RentRepository rentRepository;
    private final MemberRepository memberRepository;
    private final CopyRepository copyRepository;
    private final BookRepository bookRepository;

    // 대출 현황 Get Service
    public List<RentResponseDTO> getCurrentRents(String email) {
        Member member = memberRepository.findByEmail(email).orElse(null);
        List<Rent> rents = rentRepository.findByMemberIdAndReturnedDateIsNull(member.getId());
        return rentListToRentResponseDTOList(rents);
    }

    // 대출 이력 Get Service
    public List<RentResponseDTO> getHistoryRents(String email) {
        Member member = memberRepository.findByEmail(email).orElse(null);
        List<Rent> rents = rentRepository.findByMemberId(member.getId());
        return rentListToRentResponseDTOList(rents);
    }

    // 대출하기 Service
    public String rent(RentRequestDTO form) {
        // 1. Member가 존재하는 지 체크
        Member member =  memberRepository.findByEmail(form.getEmail())
                .orElseThrow(() -> new UserException("해당 이메일을 한 유저를 찾을 수 없습니다. " + form.getEmail()));

        // 2. 예외처리
        // 2-1. 3권 이상 빌릴 경우 분기
        if (member.getRentCount() >= 3) {
            return "세권초과";
        }

        // 2-2. 대출 중인 도서 재대출 시 분기
        Book book = copyRepository.findBookByCopyId(form.getCopyId());
        List<String> currentRentedBookTitles = rentRepository.findRentedBookTitlesByMemberId(member.getId());
        if (currentRentedBookTitles.contains(book.getTitle())) {
            return "빌린도서";
        }

        // 3. Memeber의 Rent Count 증가 및 Book의 Rent Count 증가
        member.setRentCount(member.getRentCount() + 1);
        book.setRentCount(book.getRentCount() + 1);
        bookRepository.save(book);
        memberRepository.save(member);

        // 4. Copy의 Status를 UNAVALIABLE로 바꾸기
        Copy copy = copyRepository.findById(form.getCopyId())
                .orElseThrow(() -> new RuntimeException("No such copy"));
        copy.setBookStatus(BookStatus.UNAVAILABLE);
        copyRepository.save(copy);

        // 4. Rent를 save하기
        Rent rent = new Rent();
        rent.setMember(member);
        rent.setCopy(copy);
        rent.setStartDate(LocalDate.now());
        rent.setEndDate(LocalDate.now().plusDays(7));
        rent.setIsExtendable(true);
        rentRepository.save(rent);
        return "Success";
    }

    // 도서 반납 Service
    public String returnBook(RentRequestDTO form) {
        // 1. Member가 존재하는 지 체크
        Member member =  memberRepository.findByEmail(form.getEmail())
                .orElseThrow(() -> new UserException("returnBook : 해당 이메일을 한 유저를 찾을 수 없습니다. " + form.getEmail()));

        // 2. Member의 Rent Count를 하나 감소
        if (member.getRentCount() > 0) {
            member.setRentCount(member.getRentCount() - 1);
            memberRepository.save(member);
        }

        // 3. Rent에 Returned 데이터 넣기
        Rent rent = rentRepository.findByMemberIdAndCopyIdAndReturnedDateIsNull(member.getId(), form.getCopyId())
                .orElseThrow(() -> new RentException("returnBook : 해당 Member와 Copy에 대한 Rent가 존재하지 않습니다."));
        rent.setReturnedDate(LocalDate.now());
        rentRepository.save(rent);

        // 4. Copy의 Status를 AVALIABLE로 바꾸기
        Copy copy = copyRepository.findById(form.getCopyId())
                .orElseThrow(() -> new CopyException("returnBook : 해당 Copy가 존재하지 않습니다."));
        copy.setBookStatus(BookStatus.AVAILABLE);
        copyRepository.save(copy);
        return "Success";
    }

    // 도서 연장 Service
    public String extendBook(RentRequestDTO form) {
        // 1. Member가 존재하는 지 체크
        Member member =  memberRepository.findByEmail(form.getEmail())
                .orElseThrow(() -> new UserException("extendBook : 해당 이메일을 한 유저를 찾을 수 없습니다. " + form.getEmail()));

        // 2. 대출 중인 Rent가 존재하는 지 체크
        Rent rent = rentRepository.findByMemberIdAndCopyIdAndReturnedDateIsNull(member.getId(), form.getCopyId())
                .orElseThrow(() -> new RentException("extendBook : 해당 rent 정보가 없습니다."));

        // 3. 예외처리
        // 3-1. 이미 연장을 헀을 경우
        if(rent.getIsExtendable() == false) {
            return "이미 연장을 하였습니다.";
        }

        // 3-2. 연체 중인 경우
        LocalDate today = LocalDate.now();
        if(rent.getEndDate().isBefore(today)) {
            throw new RentException("extendBook : 연체 중이기에 연장할 수 없습니다.");
        }

        // 3-3. 연체가 불가능한 날짜인 경우
        if(rent.getEndDate().equals(today) || rent.getEndDate().equals(today.plusDays(1))) {
            rent.setIsExtendable(false);
            rent.setEndDate(rent.getEndDate().plusDays(7));
        } else {
            throw new RentException("extendBook : 반납 하루 전에만 연장가능합니다.");
        }
        rentRepository.save(rent);
        return "Success";
    }

    // -----------------------------
    // |        Admin 권한관련        |
    // -----------------------------

    // Admin의 모든 Rent Get Service
    public List<RentResponseDTO> getAllRents() {
        List<Rent> rents = rentRepository.findAll();
        return rentListToRentResponseDTOList(rents);
    }

    // Admin의 도서 반납 Service
    public String adminReturnBook(RentRequestDTO form) {
        // 1. 신청자가 admin인 지 확인
        checkAdminAuthority();

        // 2. Member가 존재하는 지 체크
        Member member =  memberRepository.findByEmail(form.getEmail())
                .orElseThrow(() -> new UserException("adminReturnBook : 해당 이메일을 한 유저를 찾을 수 없습니다. " + form.getEmail()));

        // 3. Member의 Rent Count를 하나 감소
        if (member.getRentCount() > 0) {
            member.setRentCount(member.getRentCount() - 1);
            memberRepository.save(member);
        }

        // 4. Rent에 Returned 데이터 넣기
        Rent rent = rentRepository.findByMemberIdAndCopyIdAndReturnedDateIsNull(member.getId(), form.getCopyId())
                .orElseThrow(() -> new RentException("adminReturnBook : 해당 Member와 Copy에 대한 Rent가 존재하지 않습니다."));
        rent.setReturnedDate(LocalDate.now());
        rentRepository.save(rent);

        // 5. Copy의 Status를 AVAILABLE로 바꾸기
        Copy copy = copyRepository.findById(form.getCopyId())
                .orElseThrow(() -> new CopyException("adminReturnBook : 해당 Copy가 존재하지 않습니다."));
        copy.setBookStatus(BookStatus.AVAILABLE);
        copyRepository.save(copy);
        return "Success";
    }

    // -----------------------------
    // |        내장 함수 관련         |
    // -----------------------------
    // Rent(ENTITY) -> RentRequestDTO(DTO)
    private List<RentResponseDTO> rentListToRentResponseDTOList(List<Rent> rents) {
        List<RentResponseDTO> rentResponseDTOS = new ArrayList<>();
        for (Rent rent: rents) {
            RentResponseDTO rentResponseDTO = new RentResponseDTO();
            Book book = copyRepository.findBookByCopyId(rent.getCopy().getId());
            Long bookId = book.getId();
            rentResponseDTO.setId(rent.getId());
            rentResponseDTO.setCopyId(rent.getCopy().getId());
            rentResponseDTO.setMemberEmail(rent.getMember().getEmail());
            rentResponseDTO.setStartDate(rent.getStartDate());
            rentResponseDTO.setEndDate(rent.getEndDate());
            rentResponseDTO.setReturnedDate(Optional.ofNullable(rent.getReturnedDate()));
            rentResponseDTO.setTitle(bookRepository.findTitleById(bookId));
            rentResponseDTO.setExtendable(rent.getIsExtendable());
            rentResponseDTOS.add(rentResponseDTO);
        }
        return rentResponseDTOS;
    }
}

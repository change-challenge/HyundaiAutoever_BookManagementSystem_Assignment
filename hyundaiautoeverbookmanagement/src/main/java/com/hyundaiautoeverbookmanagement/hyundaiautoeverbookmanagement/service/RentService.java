package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.service;

import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.RentResponseDTO;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.RentRequestDTO;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.*;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.exception.NoSuchUserException;
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

@Service
@RequiredArgsConstructor
@Slf4j
public class RentService {

    private final RentRepository rentRepository;
    private final MemberRepository memberRepository;
    private final CopyRepository copyRepository;
    private final BookRepository bookRepository;

    public String rent(RentRequestDTO form) {
        log.info("form.getCopyId() : {} ", form.getCopyId());
        // 1. 유저인 지 체크
        Member member =  memberRepository.findByEmail(form.getEmail())
                .orElseThrow(() -> new NoSuchUserException("No such user found with email: " + form.getEmail()));

        if (member.getRentCount() >= 3) {
            return "세권초과";
        }

        // 2. 대출 중인데 또 빌리려고 할 때 체크
        Book book = copyRepository.findBookByCopyId(form.getCopyId());

        List<String> currentRentedBookTitles = rentRepository.findRentedBookTitlesByMemberId(member.getId());
        if (currentRentedBookTitles.contains(book.getTitle())) {
            return "빌린도서";
        }

        member.setRentCount(member.getRentCount() + 1);
        memberRepository.save(member);

        // 3. Copy의 Status를 UNAVALIABLE로 바꾸기
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

        // 5. Book의 rent_count를 하나 올리기
        book.setRentCount(book.getRentCount() + 1);
        bookRepository.save(book);
        return "Success";
    }

    public String returnBook(RentRequestDTO form) {
        // 1. 유저인 지 체크
        Member member =  memberRepository.findByEmail(form.getEmail())
                .orElseThrow(() -> new NoSuchUserException("No such user found with email: " + form.getEmail()));

        if (member.getRentCount() > 0) {
            member.setRentCount(member.getRentCount() - 1);
            memberRepository.save(member);
        }

        // 2. Rent에 Returned 데이터 넣기
        Rent rent = rentRepository.findByMemberIdAndCopyIdAndReturnedDateIsNull(member.getId(), form.getCopyId())
                .orElseThrow(() -> new RuntimeException("No such rent"));
        rent.setReturnedDate(LocalDate.now());
        rentRepository.save(rent);

        // 3. Copy의 Status를 AVALIABLE로 바꾸기
        Copy copy = copyRepository.findById(form.getCopyId())
                .orElseThrow(() -> new RuntimeException("No such copy"));
        copy.setBookStatus(BookStatus.AVAILABLE);
        copyRepository.save(copy);
        return "Success";
    }

    public String extendBook(RentRequestDTO form) {
        Member member =  memberRepository.findByEmail(form.getEmail())
                .orElseThrow(() -> new NoSuchUserException("No such user found with email: " + form.getEmail()));
        Rent rent = rentRepository.findByMemberIdAndCopyIdAndReturnedDateIsNull(member.getId(), form.getCopyId())
                .orElseThrow(() -> new RuntimeException("해당 rent 정보가 없습니다."));
        if(rent.getIsExtendable() == false) {
            return "이미 연장을 하였습니다.";
        }

        LocalDate today = LocalDate.now();
        if(rent.getEndDate().isBefore(today)) {
            throw new RuntimeException("연체 중이기에 연장할 수 없습니다.");
        }
        if(rent.getEndDate().equals(today) || rent.getEndDate().equals(today.minusDays(1))) {
            rent.setIsExtendable(false);
            rent.setEndDate(rent.getEndDate().plusDays(7));
        } else {
            throw new RuntimeException("반납 하루 전에만 연장가능합니다.");
        }
        rentRepository.save(rent);
        return "Success";
    }

    // READ
    public List<RentResponseDTO> getAllRents() {
        List<Rent> rents = rentRepository.findAll();
        return getRentResponseDTOS(rents);
    }

    public List<RentResponseDTO> getCurrentRents(String email) {
        Member member = memberRepository.findByEmail(email).orElse(null);
        List<Rent> rents = rentRepository.findByMemberIdAndReturnedDateIsNull(member.getId()); // returned_date가 null이면 대출중
        return getRentResponseDTOS(rents);
    }

    public List<RentResponseDTO> getHistoryRents(String email) {
        Member member = memberRepository.findByEmail(email).orElse(null);
        List<Rent> rents = rentRepository.findByMemberId(member.getId());
        return getRentResponseDTOS(rents);
    }

    // Rent(ENTITY) -> RentRequestDTO(DTO)
    private List<RentResponseDTO> getRentResponseDTOS(List<Rent> rents) {
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

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
        Member member =  memberRepository.findByEmail(form.getUserEmail())
                .orElseThrow(() -> new NoSuchUserException("No such user found with email: " + form.getUserEmail()));

        // 2. 대출 중인데 또 빌리려고 할 때 체크
        Book book = copyRepository.findBookByCopyId(form.getCopyId());

        List<String> currentRentedBookTitles = rentRepository.findRentedBookTitlesByUserId(member.getId());
        if (currentRentedBookTitles.contains(book.getTitle())) {
            return "이미 도서를 빌렸습니다. ";
        }

        // 3. Copy의 Status를 UNAVALIABLE로 바꾸기
        Copy copy = copyRepository.findById(form.getCopyId())
                .orElseThrow(() -> new RuntimeException("No such copy"));
        copy.setBookStatus(BookStatus.UNAVAILABLE);
        log.info("copy : {} ", copy);
        log.info("copy.getBook().getId() : {} ", copy.getBook().getId());
        copyRepository.save(copy);

        // 4. Rent를 save하기
        Rent rent = new Rent();
        rent.setUser(member);
        rent.setCopy(copy);
        rent.setRentStartDate(LocalDate.now());
        rent.setRentEndDate(LocalDate.now().plusDays(7));
        rent.setExtendable(true);

        rentRepository.save(rent);
        log.info("rent.getId() : {} ", rent.getId());

        // 5. Book의 rent_count를 하나 올리기
        book.setRentCount(book.getRentCount() + 1);
        bookRepository.save(book);
        return "Success";
    }

    public String returnBook(RentRequestDTO form) {
        // 1. 유저인 지 체크
        Member member =  memberRepository.findByEmail(form.getUserEmail())
                .orElseThrow(() -> new NoSuchUserException("No such user found with email: " + form.getUserEmail()));

        // 2. Rent에 Returned 데이터 넣기
        Rent rent = rentRepository.findByUserIdAndCopyIdAndRentReturnedDateIsNull(member.getId(), form.getCopyId())
                .orElseThrow(() -> new RuntimeException("No such rent"));
        rent.setRentReturnedDate(LocalDate.now());
        rentRepository.save(rent);

        // 3. Copy의 Status를 AVALIABLE로 바꾸기
        Copy copy = copyRepository.findById(form.getCopyId())
                .orElseThrow(() -> new RuntimeException("No such copy"));
        copy.setBookStatus(BookStatus.AVAILABLE);
        copyRepository.save(copy);
        return "Success";
    }

    public String extendBook(RentRequestDTO form) {
        Member member =  memberRepository.findByEmail(form.getUserEmail())
                .orElseThrow(() -> new NoSuchUserException("No such user found with email: " + form.getUserEmail()));
        Rent rent = rentRepository.findByUserIdAndCopyIdAndRentReturnedDateIsNull(member.getId(), form.getCopyId())
                .orElseThrow(() -> new RuntimeException("해당 rent 정보가 없습니다."));
        if(rent.getExtendable() == false) {
            return "이미 연장을 하였습니다.";
        }

        LocalDate today = LocalDate.now();
        if(rent.getRentEndDate().isBefore(today)) {
            throw new RuntimeException("연체 중이기에 연장할 수 없습니다.");
        }
        if(rent.getRentEndDate().equals(today) || rent.getRentEndDate().equals(today.minusDays(1))) {
            rent.setExtendable(false);
            rent.setRentEndDate(rent.getRentEndDate().plusDays(7));
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

    public List<RentResponseDTO> getCurrentRents(String userEmail) {
        Member member = memberRepository.findByEmail(userEmail).orElse(null);
        List<Rent> rents = rentRepository.findByUserIdAndRentReturnedDateIsNull(member.getId()); // returned_date가 null이면 대출중
        return getRentResponseDTOS(rents);
    }

    public List<RentResponseDTO> getHistoryRents(String userEmail) {
        Member member = memberRepository.findByEmail(userEmail).orElse(null);
        List<Rent> rents = rentRepository.findByUserId(member.getId());
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
            rentResponseDTO.setUserEmail(rent.getUser().getEmail());
            rentResponseDTO.setRentStartDate(rent.getRentStartDate());
            rentResponseDTO.setRentEndDate(rent.getRentEndDate());
            rentResponseDTO.setRentReturnedDate(Optional.ofNullable(rent.getRentReturnedDate()));
            rentResponseDTO.setTitle(bookRepository.findTitleById(bookId));
            rentResponseDTO.setExtendable(rent.getExtendable());
            rentResponseDTOS.add(rentResponseDTO);
        }
        return rentResponseDTOS;
    }


}

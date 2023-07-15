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

    public List<RentResponseDTO> getAllRents() {
        List<Rent> rents = rentRepository.findAll();
        return getRentResponseDTOS(rents);
    }

    public String rent(RentRequestDTO form) {


        log.info("form.getCopyId() : {} ", form.getCopyId());

        // 1. 유저인 지 체크
        Member member =  memberRepository.findByEmail(form.getUserEmail())
                .orElseThrow(() -> new NoSuchUserException("No such user found with email: " + form.getUserEmail()));

        log.info("member.getId() : {} ", member.getId());
        // 2. Copy의 Status를 UNAVALIABLE로 바꾸기
        Copy copy = copyRepository.findById(form.getCopyId())
                .orElseThrow(() -> new RuntimeException("No such copy"));
        copy.setBookStatus(BookStatus.UNAVAILABLE);
        log.info("copy : {} ", copy);
        log.info("copy.getBook().getId() : {} ", copy.getBook().getId());
        copyRepository.save(copy);


        // 3. Rent를 save하기
        Rent rent = new Rent();
        rent.setUser(member);
        rent.setCopy(copy);
        rent.setRentStartDate(form.getRentDate());
        rent.setRentEndDate(form.getRentDate().plusDays(7));

        rentRepository.save(rent);
        log.info("rent.getId() : {} ", rent.getId());

        // 4. Book의 rent_count를 하나 올리기
        Book book = bookRepository.findById(copy.getBook().getId())
                .orElseThrow(() -> new RuntimeException("No such book "));
        book.setRentCount(book.getRentCount() + 1);
        bookRepository.save(book);
        return "Success";
    }

    public List<RentResponseDTO> getCurrentRents(String userEmail) {
        Member member = memberRepository.findByEmail(userEmail).orElse(null);
        List<Rent> rents = rentRepository.findByUserIdAndRentReturnedDateIsNull(member.getId()); // returned_date가 null이면 대출중
        return getRentResponseDTOS(rents);
    }

    private List<RentResponseDTO> getRentResponseDTOS(List<Rent> rents) {
        List<RentResponseDTO> rentResponseDTOS = new ArrayList<>();
        for (Rent rent: rents) {
            RentResponseDTO rentResponseDTO = new RentResponseDTO();
            Book book = copyRepository.findBookByCopyId(rent.getCopy().getCopyId());
            Long bookId = book.getId();
            rentResponseDTO.setId(rent.getId());
            rentResponseDTO.setUserEmail(rent.getUser().getEmail());
            rentResponseDTO.setRentStartDate(rent.getRentStartDate());
            rentResponseDTO.setRentEndDate(rent.getRentEndDate());
            rentResponseDTO.setRentReturnedDate(Optional.ofNullable(rent.getRentReturnedDate()));
            rentResponseDTO.setTitle(bookRepository.findTitleById(bookId));
            rentResponseDTOS.add(rentResponseDTO);
        }
        return rentResponseDTOS;
    }

    public List<RentResponseDTO> getHistoryRents(String userEmail) {
        Member member = memberRepository.findByEmail(userEmail).orElse(null);
        List<Rent> rents = rentRepository.findByUserId(member.getId());


        return getRentResponseDTOS(rents);
    }
}

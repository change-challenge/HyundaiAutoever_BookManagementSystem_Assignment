package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.service;

import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.RentAdminResponseDTO;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Book;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Copy;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Member;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Rent;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.repository.BookRepository;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.repository.CopyRepository;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.repository.MemberRepository;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.repository.RentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RentService {

    private final RentRepository rentRepository;
    private final MemberRepository memberRepository;
    private final CopyRepository copyRepository;
    private final BookRepository bookRepository;

    public List<RentAdminResponseDTO> getAllRents() {
        List<Rent> rents = rentRepository.findAll();
        List<RentAdminResponseDTO> rentAdminResponseDTOS = new ArrayList<>();
        for (Rent rent: rents) {
            RentAdminResponseDTO rentAdminResponseDTO = new RentAdminResponseDTO();
            Book book = copyRepository.findBookByCopyId(rent.getCopy().getCopyId());
            Long bookId = book.getId();
            rentAdminResponseDTO.setId(rent.getId());
            rentAdminResponseDTO.setUserEmail(rent.getUser().getEmail());
            rentAdminResponseDTO.setRentStartDate(rent.getRentStartDate());
            rentAdminResponseDTO.setRentEndDate(rent.getRentEndDate());
            rentAdminResponseDTO.setRentReturnedDate(Optional.ofNullable(rent.getRentReturnedDate()));
            rentAdminResponseDTO.setTitle(bookRepository.findTitleById(bookId));
            rentAdminResponseDTOS.add(rentAdminResponseDTO);
        }
        return rentAdminResponseDTOS;
    }

    public String rent(RentAdminResponseDTO form) {
        Long copyId = bookRepository.findIdByTitle(form.getTitle());
        Member member = memberRepository.findByEmail(form.getUserEmail()).orElse(null);
        Copy copy = copyRepository.findById(copyId).orElse(null);
        Rent rent = form.toEntity(member, copy);
        Rent saved = rentRepository.save(rent);
        return "Success";
    }
}

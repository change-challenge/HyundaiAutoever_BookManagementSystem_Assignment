package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.service;

import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.RentDTO;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.WishResponseDTO;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Copy;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Member;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Rent;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Wish;
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

    public List<RentDTO> getAllRents() {
        List<Rent> rents = rentRepository.findAll();
        List<RentDTO> rentDTOS = new ArrayList<>();

        for (Rent rent: rents) {
            RentDTO rentDTO = new RentDTO();
            rentDTO.setId(rent.getId());
            rentDTO.setUserEmail(rent.getUser().getEmail());
            rentDTO.setRentStartDate(rent.getRentStartDate());
            rentDTO.setRentEndDate(rent.getRentEndDate());
            rentDTO.setRentReturnedDate(Optional.ofNullable(rent.getRentReturnedDate()));
            rentDTO.setCopyId(rent.getCopy().getCopyId());

            rentDTOS.add(rentDTO);
        }
        return rentDTOS;
    }

    public String rent(RentDTO form) {
        Member member = memberRepository.findByEmail(form.getUserEmail()).orElse(null);
        Copy copy = copyRepository.findById(form.getCopyId()).orElse(null);
        Rent rent = form.toEntity(member, copy);
        Rent saved = rentRepository.save(rent);
        return "Success";
    }
}

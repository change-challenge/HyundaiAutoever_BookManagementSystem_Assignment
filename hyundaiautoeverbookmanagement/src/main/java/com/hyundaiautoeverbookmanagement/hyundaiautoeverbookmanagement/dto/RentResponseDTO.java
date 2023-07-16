package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto;

import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Copy;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Member;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Rent;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@ToString
public class RentResponseDTO {

    private Long id;
    private Long copyId;
    private String title;
    private String memberEmail;
    private LocalDate startDate;
    private LocalDate endDate;
    private Optional<LocalDate> returnedDate;
    private boolean isExtendable;

    public Rent toEntity(Member member, Copy copy) {
        Rent rent = new Rent();
        rent.setId(this.id);
        rent.setStartDate(this.startDate);
        rent.setEndDate(this.endDate);
        rent.setReturnedDate(this.returnedDate.orElse(null));
        rent.setMember(member);
        rent.setCopy(copy);
        rent.setIsExtendable(this.isExtendable);
        return rent;
    }
}

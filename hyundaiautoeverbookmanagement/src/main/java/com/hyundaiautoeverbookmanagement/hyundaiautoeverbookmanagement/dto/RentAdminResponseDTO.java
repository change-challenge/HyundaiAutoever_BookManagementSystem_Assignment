package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto;

import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Copy;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Member;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Rent;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@ToString
public class RentAdminResponseDTO {

    private Long id;
    private String title;
    private String userEmail;
    private LocalDate rentStartDate;
    private LocalDate rentEndDate;
    private Optional<LocalDate> rentReturnedDate;

    public Rent toEntity(Member user, Copy copy) {
        Rent rent = new Rent();
        rent.setId(this.id);
        rent.setRentStartDate(this.rentStartDate);
        rent.setRentEndDate(this.rentEndDate);
        rent.setRentReturnedDate(this.rentReturnedDate.orElse(null));
        rent.setUser(user);
        rent.setCopy(copy);

        log.info("user!!!!!! ", user);
        log.info("copy!!!!!! ", copy);

        return rent;
    }
}

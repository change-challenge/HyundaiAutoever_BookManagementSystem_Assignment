package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto;

import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Copy;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Member;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Rent;
import lombok.*;

import java.util.Date;
import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RentAdminResponseDTO {

    private Long id;
    private String title;
    private String userEmail;
    private Date rentStartDate;
    private Date rentEndDate;
    private Optional<Date> rentReturnedDate;

    public Rent toEntity(Member user, Copy copy) {
        Rent rent = new Rent();
        rent.setId(this.id);
        rent.setRentStartDate(this.rentStartDate);
        rent.setRentEndDate(this.rentEndDate);
        rent.setRentReturnedDate(this.rentReturnedDate.orElse(null));
        rent.setUser(user);
        rent.setCopy(copy);
        return rent;
    }
}

package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RentRequestDTO {

    private LocalDate rentDate;
    private String userEmail;
    private Long copyId;

}

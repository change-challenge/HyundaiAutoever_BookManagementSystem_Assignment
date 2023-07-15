package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RentRequestDTO {

    private String email;
    private Long copyId;
}

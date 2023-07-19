package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RentRequestDTO {

    private String email;
    private Long copyId;
}

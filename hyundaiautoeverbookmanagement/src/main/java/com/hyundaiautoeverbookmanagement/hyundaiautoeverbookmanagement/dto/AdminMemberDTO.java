package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto;

import lombok.*;

import java.util.Date;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AdminMemberDTO {

    private String adminEmail;
    private String adminToken;
    private String userEmail;
}

package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto;

import lombok.*;

import java.util.Date;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {

    private Long id;
    private String email;
    private String name;
    private MemberType memberType;
    private Date registDate;

}

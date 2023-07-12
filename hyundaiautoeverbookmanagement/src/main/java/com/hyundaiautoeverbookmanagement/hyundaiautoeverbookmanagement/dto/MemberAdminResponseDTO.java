package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto;

import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Member;
import lombok.*;

import java.util.Date;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberAdminResponseDTO {

    private Long id;
    private String email;
    private String name;
    private int rentCount;
    private int lateDay;
    private Date registDate;
    private UserType userType;


    public Member toEntity() {
        Member user = new Member();
        user.setId(null);
        user.setEmail(this.email);
        user.setName(this.name);
        user.setUsertype(UserType.USER);
        return user;
    }
}

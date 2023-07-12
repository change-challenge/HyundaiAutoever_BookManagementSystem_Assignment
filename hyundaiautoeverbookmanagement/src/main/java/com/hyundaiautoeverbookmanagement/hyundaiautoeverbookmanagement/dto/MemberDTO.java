package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto;

import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Member;
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
    private UserType userType;
    private Date registDate;


    public Member toEntity() {
        Member user = new Member();
        user.setId(null);
        user.setEmail(this.email);
        user.setName(this.name);
        user.setUsertype(UserType.USER);
        return user;
    }
}

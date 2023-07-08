package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto;

import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Member;
import lombok.*;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {

    private String email;
    private String password;
    private String name;
    private UserType userType;

    // 생성자, getter, setter, toString 등 필요한 메서드 추가

    public Member toEntity() {
        Member user = new Member();
        user.setId(null);
        user.setEmail(this.email);
        user.setPassword(this.password);
        user.setName(this.name);
        user.setUsertype(UserType.USER);
        return user;
    }
}

package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto;

import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.User;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.WishBook;
import lombok.*;

import java.util.Date;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private String email;
    private String password;
    private String name;
    private String address;
    private String phone;
    private UserType userType;

    // 생성자, getter, setter, toString 등 필요한 메서드 추가

    public User toEntity() {
        User user = new User();
        user.setId(null);
        user.setEmail(this.email);
        user.setPassword(this.password);
        user.setName(this.name);
        user.setUserType(UserType.USER);
        return user;
    }
}

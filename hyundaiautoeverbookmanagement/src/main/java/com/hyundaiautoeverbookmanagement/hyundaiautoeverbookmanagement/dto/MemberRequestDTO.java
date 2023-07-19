package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto;

import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.type.MemberType;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Member;
import lombok.*;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MemberRequestDTO {

    private String name;
    private String email;
    private String password;
    private MemberType memberType;
    private LocalDate registerDate = LocalDate.now();

    public Member toUser(PasswordEncoder passwordEncoder) {
        Member user = new Member();
        user.setId(null);
        user.setEmail(this.email);
        user.setPassword(passwordEncoder.encode(password));
        user.setName(this.name);
        user.setMemberType(MemberType.MEMBER);
        user.setRegistDate(this.registerDate);
        user.setRentCount(0);
        return user;
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(email, password);
    }
}

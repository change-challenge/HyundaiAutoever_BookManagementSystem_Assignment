package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto;

import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import lombok.ToString;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MemberRequestDto {

    private String email;
    private String password;

    public Member toUser(PasswordEncoder passwordEncoder) {
        Member user = new Member();
        user.setId(null);
        user.setEmail(this.email);
        user.setPassword(passwordEncoder.encode(password));
        user.setUsertype(UserType.USER);
        return user;
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(email, password);
    }
}

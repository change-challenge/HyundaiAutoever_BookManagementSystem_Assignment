package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto;

import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponseDTO {
    private String email;
    private String name;


    public static MemberResponseDTO of(Member user) {
        return new MemberResponseDTO(user.getEmail(), user.getName()); // 이름을 반환하도록 수정
    }
}

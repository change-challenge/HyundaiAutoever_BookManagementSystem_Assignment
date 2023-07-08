package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto;

import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponseDto {
    private String email;
    private String name;


    public static MemberResponseDto of(Member user) {
        return new MemberResponseDto(user.getEmail(), user.getName()); // 이름을 반환하도록 수정
    }
}

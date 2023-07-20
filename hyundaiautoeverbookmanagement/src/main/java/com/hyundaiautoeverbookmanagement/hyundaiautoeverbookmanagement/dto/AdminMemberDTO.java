package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto;

import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.type.MemberType;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Member;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AdminMemberDTO {

    private Long id;
    private String email;
    private String name;
    private int rentCount;
    private int lateDay;
    private LocalDate registDate;
    private MemberType memberType;

    public Member toEntity() {
        Member member = new Member();
        member.setId(null);
        member.setEmail(this.email);
        member.setName(this.name);
        member.setMemberType(MemberType.MEMBER);
        return member;
    }
}

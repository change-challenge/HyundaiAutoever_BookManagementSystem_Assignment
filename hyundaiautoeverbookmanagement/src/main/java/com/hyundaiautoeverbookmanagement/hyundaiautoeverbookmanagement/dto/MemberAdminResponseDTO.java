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

package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.type.MemberType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "MEMBER")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String email;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "MEMBER_TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberType memberType;

    @Column(name = "REGIST_DATE", nullable = false)
    private Date registDate;

    @Column(name = "RENT_COUNT", nullable = false)
    private int RentCount;
}

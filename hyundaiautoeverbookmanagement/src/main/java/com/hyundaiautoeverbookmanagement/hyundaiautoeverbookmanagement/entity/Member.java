package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.UserType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "USERS")
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

    @Column
    private String password;

    @Column
    private String name;

    @Column
    @Enumerated(EnumType.STRING)
    private UserType usertype;
}

package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Entity // DB가 해당 객체를 인식 가능!
@Table(name = "WISH")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Wish {

    @Id // 대표값을 지정! like a 주민등록번호
    @GeneratedValue(strategy = GenerationType.AUTO) // 1, 2, 3, .... 자동 생성 어노테이션!
    private Long id;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "AUTHOR", nullable = false)
    private String author;

    @Column(name = "PUBLISHER", nullable = false)
    private String publisher;

    @Column(name = "ISBN")
    private String ISBN;

    @JoinColumn(name = "user_email", nullable = false)
    private String user_email;

    @Column(name = "WISH_DATE", nullable = false)
    private Date wish_date;
}

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
public class WishBook {

    @Id // 대표값을 지정! like a 주민등록번호
    @GeneratedValue(strategy = GenerationType.AUTO) // 1, 2, 3, .... 자동 생성 어노테이션!
    private Long id;
    @Column
    private String title;
    @Column
    private String author;
    @Column
    private String publisher;
    @Column
    private String ISBN;
    @Column
    private int user_id;
    @Column
    private Date create_date;


}

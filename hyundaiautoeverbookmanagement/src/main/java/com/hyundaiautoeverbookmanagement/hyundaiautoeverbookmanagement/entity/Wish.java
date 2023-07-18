package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity;

import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.type.CategoryType;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.type.CategoryTypeConverter;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.type.WishStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity // DB가 해당 객체를 인식 가능!
@Table(name = "WISH")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Wish {

    @Id // 대표값을 지정! like a 주민등록번호
    @GeneratedValue(strategy = GenerationType.AUTO) // 1, 2, 3, .... 자동 생성 어노테이션!
    private Long id;

    @JoinColumn(name = "EMAIL", nullable = false)
    private String memberEmail;

    @Column(name = "WISH_DATE", nullable = false)
    private LocalDate wishDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false)
    private WishStatus wishStatus = WishStatus.PENDING;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "AUTHOR", nullable = false)
    private String author;

    @Column(name = "PUBLISHER", nullable = false)
    private String publisher;

    @Column(name = "ISBN")
    private String ISBN;

    @Column(name = "CATEGORY")
    @Convert(converter = CategoryTypeConverter.class)
    private CategoryType category = CategoryType.CALENDAR_ETC;

    @Column(name = "INFO", length = 300)
    private String info;

    @Column(name = "COVER", length = 255)
    private String cover;

    @Column(name = "PUB_DATE")
    private LocalDate pubDate;

    @Column(name = "RENT_COUNT")
    private int rentCount;
}

package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Entity
@Table(name="BOOK")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "AUTHOR")
    private String author;

    @Column(name = "PUBLISHER")
    private String publisher;

    @Column(name = "CATEGORY")
    @Convert(converter = CategoryTypeConverter.class)
    private CategoryType category;

    @Column(name = "RENT_COUNT")
    private int rentCount;

    @Column(name = "ISBN")
    private String isbn;

    @Column(name = "COVER")
    private String cover;

    @Column(name = "INFO")
    private String info;

    @Column(name="PUB_DATE")
    private LocalDate pubDate;
}
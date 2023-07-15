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

    @Column
    private String title;

    @Column
    private String author;

    @Column
    private String publisher;

    @Column
    private String category;

    @Column
    private int rentCount;

    @Column
    private String isbn;

    @Column
    private String cover;

    @Column
    private String info;

    @Column(name="pub_date")
    private LocalDate pubDate;
}
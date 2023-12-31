package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity;

import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.type.CategoryType;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.type.CategoryTypeConverter;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Entity
@Table(name="BOOK", uniqueConstraints={@UniqueConstraint(columnNames={"id"})})
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "AUTHOR")
    private String author;

    @Column(name = "PUBLISHER")
    private String publisher;

    @Column(name = "CATEGORY")
    @Convert(converter = CategoryTypeConverter.class)
    private CategoryType category = CategoryType.CALENDAR_ETC;;

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
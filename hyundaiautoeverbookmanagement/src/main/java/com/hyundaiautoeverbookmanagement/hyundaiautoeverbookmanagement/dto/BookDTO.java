package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto;

import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Book;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.CategoryType;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class BookDTO {

    private Long id;
    private String title;
    private String author;
    private String publisher;
    private String category;
    private String isbn;
    private String cover;
    private String info;
    private int rentCount;
    private LocalDate pubDate;
    private boolean bookStatus;
    private int bookCount;

    public Book toEntity() {
        return new Book(null, title, author, publisher, CategoryType.ToEnglish(category), rentCount, isbn, cover, info, pubDate);
    }
}

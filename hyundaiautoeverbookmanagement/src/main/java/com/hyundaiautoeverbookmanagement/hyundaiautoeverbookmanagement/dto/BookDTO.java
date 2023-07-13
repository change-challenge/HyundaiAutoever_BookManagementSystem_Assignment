package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto;

import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Book;
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
    private int rent_count;
    private LocalDate pubDate;
    private boolean bookStatus;
    private int bookCount;

    public Book toEntity() {
        return new Book(null, title, author, publisher, category, isbn, cover, info, pubDate);
    }
}

package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto;

import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.WishBook;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;
import java.util.Optional;

@AllArgsConstructor
@ToString
@Getter
public class WishBookDTO {

    private String title;
    private String author;
    private String publisher;
    private Optional<String> ISBN;
    private int user_id;
    private Date create_date;


    public WishBook toEntity() {
        return new WishBook(null, title, author, publisher, ISBN.orElse(null), user_id, create_date);
    }
}

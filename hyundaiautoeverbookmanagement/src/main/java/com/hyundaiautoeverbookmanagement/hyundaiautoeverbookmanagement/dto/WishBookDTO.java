package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto;

import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Member;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Wish;
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
    private String user_email;
    private Date wish_date;


    public Wish toEntity() {
        return new Wish(null, title, author, publisher, ISBN.orElse(null), user_email, wish_date);
    }
}

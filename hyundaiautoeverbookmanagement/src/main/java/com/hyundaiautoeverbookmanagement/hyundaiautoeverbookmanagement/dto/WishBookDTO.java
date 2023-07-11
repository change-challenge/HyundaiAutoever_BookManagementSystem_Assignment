package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Wish;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.WishType;
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
    private String userEmail;
    private Date wishDate;


    public Wish toEntity() {
        return new Wish(null, title, author, publisher, ISBN.orElse(null), this.userEmail, wishDate, WishType.PENDING);
    }
}

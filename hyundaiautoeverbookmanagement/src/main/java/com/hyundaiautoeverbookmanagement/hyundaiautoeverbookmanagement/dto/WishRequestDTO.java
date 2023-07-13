package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto;

import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Book;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Wish;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.WishType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@ToString
@Getter
@Setter
public class WishRequestDTO {

    private LocalDate wish_date;
    private String status;
    private String user_email;
    private BookDTO book;

    public Wish toEntity() {
        return new Wish(null, user_email, wish_date, WishType.PENDING, book.getTitle(), book.getAuthor(), book.getPublisher(), book.getIsbn(), book.getCategory(), book.getInfo(), book.getCover(), book.getPubDate(), book.getRent_count());
    }
}
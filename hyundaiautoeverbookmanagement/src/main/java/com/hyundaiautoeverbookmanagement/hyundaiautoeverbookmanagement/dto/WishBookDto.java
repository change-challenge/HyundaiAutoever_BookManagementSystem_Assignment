package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto;

import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.WishBook;

import java.util.Date;

public class WishBookDto {

    private String title;
    private String author;
    private String publisher;
    private String ISBN;
    private int user_id;
    private Date createDate;

    public WishBookDto(String title, String author, String publisher, String ISBN, int user_id, Date createDate) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.ISBN = ISBN;
        this.user_id = user_id;
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "WishBookDto{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", publisher='" + publisher + '\'' +
                ", ISBN='" + ISBN + '\'' +
                ", user_id=" + user_id +
                ", createDate=" + createDate +
                '}';
    }

    public WishBook toEntity() {
        return new WishBook(null, title, author, publisher, ISBN, user_id,createDate);
    }
}

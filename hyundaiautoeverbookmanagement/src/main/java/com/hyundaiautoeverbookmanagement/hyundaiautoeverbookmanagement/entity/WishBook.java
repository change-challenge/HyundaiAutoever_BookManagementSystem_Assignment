package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity // DB가 해당 객체를 인식 가능!
@Table(name = "WISH")
public class WishBook {

    @Id // 대표값을 지정! like a 주민등록번호
    @GeneratedValue // 1, 2, 3, .... 자동 생성 어노테이션!
    private Long id;
    @Column
    private String title;
    @Column
    private String author;
    @Column
    private String publisher;
    @Column
    private String ISBN;
    @Column
    private int user_id;
    @Column
    private Date createDate;

    public WishBook() {
    }

    public WishBook(Long id, String title, String author, String publisher, String ISBN, int user_id, Date createDate) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.ISBN = ISBN;
        this.user_id = user_id;
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "WishBook{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", publisher='" + publisher + '\'' +
                ", ISBN='" + ISBN + '\'' +
                ", user_id=" + user_id +
                ", createDate=" + createDate +
                '}';
    }
}

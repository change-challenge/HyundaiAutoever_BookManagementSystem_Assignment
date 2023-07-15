package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "COPY")
@Getter
@Setter
public class Copy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "BOOK_ID")
    private Book book;

    @Enumerated(EnumType.STRING)
    @Column(name = "BOOK_STATUS")
    private BookStatus bookStatus; // Enum type: AVAILABLE, UNAVAILABLE
}

package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto;

import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Book;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Wish;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.WishType;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class WishResponseDTO {

    private Long id;
    private String userEmail;
    private String title;
    private String author;
    private LocalDate wishDate;
    private String publisher;
    private String ISBN;
    private WishType wishType;
}

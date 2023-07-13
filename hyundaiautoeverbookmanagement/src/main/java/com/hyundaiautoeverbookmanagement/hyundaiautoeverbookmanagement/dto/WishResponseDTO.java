package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto;

import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Book;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Wish;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.WishType;
import lombok.*;

import java.util.Date;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class WishResponseDTO {

    private Long id;
    private Book book;
    private String userEmail;
    private Date wishDate;
    private WishType wishType;

}

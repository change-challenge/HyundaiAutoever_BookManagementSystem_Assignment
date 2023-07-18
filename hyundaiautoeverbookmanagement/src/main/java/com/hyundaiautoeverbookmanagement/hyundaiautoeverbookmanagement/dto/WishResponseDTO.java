package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto;

import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.type.WishStatus;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class WishResponseDTO {

    private Long id;
    private String email;
    private String title;
    private String author;
    private LocalDate wishDate;
    private String publisher;
    private String ISBN;
    private WishStatus wishStatus;
}

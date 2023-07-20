package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto;

import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.type.CategoryType;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.type.WishStatus;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Member;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Wish;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class WishRequestDTO {

    private Long id;
    private LocalDate wishDate;
    private String status;
    private String email;
    private BookDTO book;

    public Wish toEntity(Member member) {
        return new Wish(null, member, LocalDate.now(), WishStatus.PENDING, book.getTitle(), book.getAuthor(), book.getPublisher(), book.getIsbn(), CategoryType.ToEnglish(book.getCategory()), book.getInfo(), book.getCover(), book.getPubDate(), book.getRentCount());
    }
}
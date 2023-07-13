package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.service;


import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.BookDTO;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.UserType;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.WishRequestDTO;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.WishResponseDTO;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.BookStatus;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Wish;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.repository.WishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class WishService {

    private final WishRepository wishRepository;

    public List<WishResponseDTO> getAllWishs() {
        List<Wish> wishBooks = wishRepository.findAll();
        List<WishResponseDTO> wishResponseDTOS = new ArrayList<>();

        for (Wish wish: wishBooks) {
            WishResponseDTO wishResponseDTO = new WishResponseDTO();
            BookDTO bookDTO = new BookDTO();
//            private Long id;
//            private String email;
//            private String name;
//            private int rentCount;
//            private int lateDay;
//            private Date registDate;
//            private UserType userType;

            bookDTO.setBookCount(0);
            bookDTO.setBookStatus(true);
            bookDTO.setId(null);
            bookDTO.setCover();


            wishResponseDTO.setId(wish.getId());
            wishResponseDTO.setWishDate(wish.getWish_date());



            wishResponseDTO.setTitle(wish.getTitle());
            wishResponseDTO.setAuthor(wish.getAuthor());
            wishResponseDTO.setISBN(Optional.ofNullable(wish.getISBN()));
            wishResponseDTO.setUserEmail(wish.getUser_email());

            wishResponseDTO.setPublisher(wish.getPublisher());
            wishResponseDTO.setWishType(wish.getWishType());
            wishResponseDTOS.add(wishResponseDTO);
        }
        return wishResponseDTOS;
    }

    public String saveWish(WishRequestDTO form) {
        Wish wish = form.toEntity();
        Wish saved = wishRepository.save(wish);
        return "Success";
    }
}

package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.service;


import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.WishRequestDTO;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.WishResponseDTO;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Wish;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.repository.WishRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class WishService {

    private final WishRepository wishRepository;

    public List<WishResponseDTO> getWishBooks() {
        List<Wish> wishBooks = wishRepository.findAll();
        List<WishResponseDTO> wishResponseDTOS = new ArrayList<>();

        for (Wish wish: wishBooks) {
            WishResponseDTO wishResponseDTO = new WishResponseDTO();

            wishResponseDTO.setId(wish.getId());
            wishResponseDTO.setTitle(wish.getTitle());
            wishResponseDTO.setAuthor(wish.getAuthor());
            wishResponseDTO.setISBN(Optional.ofNullable(wish.getISBN()));
            wishResponseDTO.setUserEmail(wish.getUser_email());
            wishResponseDTO.setWishDate(wish.getWish_date());
            wishResponseDTO.setPublisher(wish.getPublisher());
            wishResponseDTO.setWishType(wish.getWishType());
            wishResponseDTOS.add(wishResponseDTO);
        }
        return wishResponseDTOS;
    }


    public String saveWishBook(WishRequestDTO form) {
        Wish wish = form.toEntity();
        Wish saved = wishRepository.save(wish);
        return "Success";
    }







}

package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.service;


import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.BookDTO;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.WishRequestDTO;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Wish;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.repository.WishRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class WishService {

    private final WishRepository wishRepository;

    public List<WishRequestDTO> getAllWishs() {
        List<Wish> wishBooks = wishRepository.findAll();
        List<WishRequestDTO> wishRequestDTOS = new ArrayList<>();

        for (Wish wish: wishBooks) {
            WishRequestDTO wishRequestDTO = new WishRequestDTO();
            BookDTO bookDTO = new BookDTO();
            wishRequestDTO.setId(wish.getId());
            wishRequestDTO.setEmail(wish.getMemberEmail());
            wishRequestDTO.setStatus(wish.getWishType().toString());
            wishRequestDTO.setWishDate(wish.getWishDate());
            bookDTO.setId(null);
            bookDTO.setTitle(wish.getTitle());
            bookDTO.setAuthor(wish.getAuthor());
            bookDTO.setPublisher(wish.getPublisher());
            bookDTO.setIsbn(wish.getISBN());
            bookDTO.setBookCount(0);
            bookDTO.setCover(wish.getCover());
            bookDTO.setBookStatus(false);
            try {
                bookDTO.setCategory(wish.getCategory().getDescription());
            } catch (IllegalArgumentException e) {
                log.info("wish Category 문제");
            }
            bookDTO.setPubDate(wish.getPubDate());
            bookDTO.setInfo(wish.getInfo());
            bookDTO.setRentCount(0);
            wishRequestDTO.setBook(bookDTO);
            wishRequestDTOS.add(wishRequestDTO);
        }
        return wishRequestDTOS;
    }

    public List<WishRequestDTO> getWish(String email) {
        List<Wish> wishBooks = wishRepository.findByMemberEmail(email);
        List<WishRequestDTO> wishRequestDTOS = new ArrayList<>();

        for (Wish wish: wishBooks) {
            WishRequestDTO wishRequestDTO = new WishRequestDTO();
            BookDTO bookDTO = new BookDTO();
            wishRequestDTO.setId(wish.getId());
            wishRequestDTO.setEmail(wish.getMemberEmail());
            wishRequestDTO.setStatus(wish.getWishType().toString());
            wishRequestDTO.setWishDate(wish.getWishDate());
            bookDTO.setId(null);
            bookDTO.setTitle(wish.getTitle());
            bookDTO.setAuthor(wish.getAuthor());
            bookDTO.setPublisher(wish.getPublisher());
            bookDTO.setIsbn(wish.getISBN());
            bookDTO.setBookCount(0);
            bookDTO.setCover(wish.getCover());
            bookDTO.setBookStatus(false);
            try {
                bookDTO.setCategory(wish.getCategory().getDescription());
            } catch (IllegalArgumentException e) {
                log.info("wish Category 문제");
            }
            bookDTO.setPubDate(wish.getPubDate());
            bookDTO.setInfo(wish.getInfo());
            bookDTO.setRentCount(0);
            wishRequestDTO.setBook(bookDTO);
            wishRequestDTOS.add(wishRequestDTO);
        }
        return wishRequestDTOS;
    }

    public String saveWish(WishRequestDTO form) {
        Wish wish = form.toEntity();
        Wish saved = wishRepository.save(wish);
        return "Success";
    }
}

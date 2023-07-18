package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.service;


import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.BookDTO;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.MemberType;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.WishRequestDTO;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.*;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.repository.BookRepository;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.repository.CopyRepository;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.repository.WishRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.util.SecurityUtil.getCurrentMemberType;


@Service
@RequiredArgsConstructor
@Slf4j
public class WishService {

    private final WishRepository wishRepository;
    private final BookRepository bookRepository;
    private final CopyRepository copyRepository;
    private final BookService bookService;

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
        // 1. 책 존재하는 지 확인
        if (bookRepository.existsByIsbn(form.getBook().getIsbn())) {
            throw new RuntimeException("이미 존재하는 책입니다.");
        }

        Wish wish = form.toEntity();
        Wish saved = wishRepository.save(wish);
        return "Success";
    }

    public String rejectedWish(String wishIdStr) {
        Long wishId = Long.parseLong(wishIdStr);

        // 1. 신청자가 admin인 지 확인
        if (getCurrentMemberType() != MemberType.ADMIN) {
            return "당신은 Admin이 아닙니다.";
        }

        // 2. wishId로 Wish 찾기
        Wish wish = wishRepository.findById(wishId)
                .orElseThrow(() -> new RuntimeException("해당 wish가 존재하지 않습니다."));

        // 3. wish Status 변경
        wish.setWishType(WishType.REJECTED);
        wishRepository.save(wish);

        return "Success";
    }

    public String createWishBook(WishRequestDTO wishDTO) {
        // 1. 신청자가 admin인 지 확인
        if (getCurrentMemberType() != MemberType.ADMIN) {
            return "당신은 Admin이 아닙니다.";
        }
        // 2. bookDTO를 Entity로 변경
        Book book = wishDTO.getBook().toEntity();

        // 3. wishId로 Wish 찾기
        Wish wish = wishRepository.findById(wishDTO.getId())
                .orElseThrow(() -> new RuntimeException("해당 wish가 존재하지 않습니다."));

        // 4. wish Status 변경
        wish.setWishType(WishType.APPROVED);
        wishRepository.save(wish);

        // 5. Book 추가
        bookRepository.save(book);

        // 6. Copy 추가
        Copy copy = new Copy();
        copy.setBook(book);
        copy.setBookStatus(BookStatus.AVAILABLE);
        copyRepository.save(copy);
        return "Success";
    }
}

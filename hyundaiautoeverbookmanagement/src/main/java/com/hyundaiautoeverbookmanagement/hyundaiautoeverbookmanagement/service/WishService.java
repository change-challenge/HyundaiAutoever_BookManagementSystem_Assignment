package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.service;


import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.BookRequestDTO;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.type.BookStatus;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.WishRequestDTO;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.type.WishStatus;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.*;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.exception.BookException;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.exception.UserException;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.exception.WishException;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.repository.BookRepository;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.repository.CopyRepository;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.repository.MemberRepository;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.repository.WishRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.util.SecurityUtil.checkAdminAuthority;

@Service
@RequiredArgsConstructor
@Component
@Slf4j
public class WishService {

    private final WishRepository wishRepository;
    private final BookRepository bookRepository;
    private final CopyRepository copyRepository;
    private final MemberRepository memberRepository;

    // 내 서재에서 나의 희망도서 Get Service
    public List<WishRequestDTO> getWishByEmail(String email) {
        List<Wish> wishBooks = wishRepository.findByMemberEmail(email);
        return wishListToWishRequestDTOList(wishBooks);
    }
    // 희망도서 신청 Post Service

    @Transactional
    public String saveWish(WishRequestDTO form) {
        // 1. 책 존재하는 지 확인
        if (bookRepository.existsByIsbn(form.getBook().getIsbn())) {
            throw new BookException("이미 존재하는 책입니다.");
        }
        // 2. 이메일로 Member 찾기
        Member member = memberRepository.findByEmail(form.getEmail())
                .orElseThrow(() -> new UserException("해당 유저는 존재하지 않습니다."));

        // 3. 희망도서 Entity로 변환
        Wish wish = form.toEntity(member);
        wish.setMember(member);

        // 4. 희망도서 DB에 저장
        Wish saved = wishRepository.save(wish);
        return "Success";
    }

    // -----------------------------
    // |        Admin 권한관련        |
    // -----------------------------

    // Admin의 모든 Wish Get Service
    public List<WishRequestDTO> getAllWishs() {
        List<Wish> wishBooks = wishRepository.findAll();
        return wishListToWishRequestDTOList(wishBooks);
    }

    // Admin의 희망도서 반려
    @Transactional
    public String rejectedWish(String wishIdStr) {
        Long wishId = Long.parseLong(wishIdStr);

        // 1. 신청자가 Admin인 지 확인
        checkAdminAuthority();

        // 2. Wish Id로 Wish 찾기
        Wish wish = wishRepository.findById(wishId)
                .orElseThrow(() -> new RuntimeException("해당 wish가 존재하지 않습니다."));

        // 3. Wish Status 변경
        wish.setWishStatus(WishStatus.REJECTED);
        wishRepository.save(wish);
        return "Success";
    }

    // Admin의 희망도서 허락
    @Transactional
    public String approveWish(WishRequestDTO wishDTO) {
        // 1. 신청자가 Admin인 지 확인
        checkAdminAuthority();

        // 2. bookDTO를 Entity로 변경
        Book book = wishDTO.getBook().toEntity();

        // 3. wishId로 Wish 찾기
        Wish wish = wishRepository.findById(wishDTO.getId())
                .orElseThrow(() -> new WishException("해당 wish가 존재하지 않습니다."));

        // 4. 중복된 책 확인 후 Book 추가
        if (bookRepository.existsByIsbn(wishDTO.getBook().getIsbn())) {
            throw new BookException("이미 존재하는 책입니다.");
        }
        bookRepository.save(book);

        // 5. Copy 추가
        Copy copy = new Copy();
        copy.setBook(book);
        copy.setBookStatus(BookStatus.AVAILABLE);
        copyRepository.save(copy);

        // 6. Wish Status 변경
        wish.setWishStatus(WishStatus.APPROVED);
        wishRepository.save(wish);
        return "Success";
    }

    // -----------------------------
    // |        내장 함수 관련         |
    // -----------------------------

    // WishRequestDTO List to Wish List (ENTITY)
    private List<WishRequestDTO> wishListToWishRequestDTOList(List<Wish> wishBooks) {
        return wishBooks.stream()
                .map(this::wishToWishRequestDTO)
                .collect(Collectors.toList());
    }

    // WishRequestDTO to Wish (ENTITY)
    private WishRequestDTO wishToWishRequestDTO(Wish wish) {
        WishRequestDTO wishRequestDTO = new WishRequestDTO();
        wishRequestDTO.setId(wish.getId());
        wishRequestDTO.setEmail(wish.getMember().getEmail());
        wishRequestDTO.setStatus(wish.getWishStatus().toString());
        wishRequestDTO.setWishDate(wish.getWishDate());
        wishRequestDTO.setBook(convertToBookDTO(wish));
        return wishRequestDTO;
    }

    private BookRequestDTO convertToBookDTO(Wish wish) {
        BookRequestDTO bookRequestDTO = new BookRequestDTO();
        bookRequestDTO.setId(null);
        bookRequestDTO.setTitle(wish.getTitle());
        bookRequestDTO.setAuthor(wish.getAuthor());
        bookRequestDTO.setPublisher(wish.getPublisher());
        bookRequestDTO.setIsbn(wish.getISBN());
        bookRequestDTO.setBookCount(0);
        bookRequestDTO.setCover(wish.getCover());
        bookRequestDTO.setBookStatus(false);
        try {
            bookRequestDTO.setCategory(wish.getCategory().getDescription());
        } catch (IllegalArgumentException e) {
            log.info("wish Category 문제");
        }
        bookRequestDTO.setPubDate(wish.getPubDate());
        bookRequestDTO.setInfo(wish.getInfo());
        bookRequestDTO.setRentCount(0);
        return bookRequestDTO;
    }
}

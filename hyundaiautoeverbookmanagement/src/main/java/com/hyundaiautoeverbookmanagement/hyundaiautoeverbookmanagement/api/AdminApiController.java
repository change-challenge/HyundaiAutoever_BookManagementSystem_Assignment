package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.api;


import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.*;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/admin")
public class AdminApiController {

    private final MemberService memberService;
    private final RentService rentService;
    private final WishService wishService;
    private final BookService bookService;

    @GetMapping("/member")
    public ResponseEntity<List<MemberAdminResponseDTO>> getMembers() {
        List<MemberAdminResponseDTO> users = memberService.getAllMembers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/rent")
    public ResponseEntity<List<RentResponseDTO>> getRents() {
        List<RentResponseDTO> rent = rentService.getAllRents();
        log.info("!!rent!! " + rent);
        return new ResponseEntity<>(rent, HttpStatus.OK);
    }

    @GetMapping("/book")
    public ResponseEntity<List<BookDTO>> getBooks() {
        List<BookDTO> books = bookService.getAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/wish")
    public ResponseEntity<List<WishRequestDTO>> getWishBooks() {
        List<WishRequestDTO> wishBooks = wishService.getAllWishs();
        return new ResponseEntity<>(wishBooks, HttpStatus.OK);
    }
}

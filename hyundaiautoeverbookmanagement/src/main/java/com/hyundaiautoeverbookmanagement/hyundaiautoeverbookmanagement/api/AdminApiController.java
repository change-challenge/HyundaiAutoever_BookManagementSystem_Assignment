package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.api;


import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.BookDTO;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.MemberDTO;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.RentDTO;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.WishResponseDTO;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminApiController {

    private final MemberService memberService;
    private final RentService rentService;
    private final WishService wishService;
    private final BookService bookService;

    @GetMapping("/user")
    public ResponseEntity<List<MemberDTO>> getUsers() {
        List<MemberDTO> users = memberService.getAllMembers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/rent")
    public ResponseEntity<List<RentDTO>> getRents() {
        List<RentDTO> rent = rentService.getAllRents();
        return new ResponseEntity<>(rent, HttpStatus.OK);
    }

    @GetMapping("/book")
    public ResponseEntity<List<BookDTO>> getBooks() {
        List<BookDTO> books = bookService.getAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/wish")
    public ResponseEntity<List<WishResponseDTO>> getWishBooks() {
        List<WishResponseDTO> wishBooks = wishService.getAllWishs();
        return new ResponseEntity<>(wishBooks, HttpStatus.OK);
    }
}

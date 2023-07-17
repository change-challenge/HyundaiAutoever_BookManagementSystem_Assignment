package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.api;


import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.*;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


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

    @PostMapping("/member/type")
    public ResponseEntity<String> changeMemberType(@RequestBody Map<String, String> request) {
        try {
            String email = request.get("email");
            String myEmail = request.get("myEmail");
            String result = memberService.changeMemberType(email, myEmail);
            if ("Success".equals(result)) {
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/rent")
    public ResponseEntity<List<RentResponseDTO>> getRents() {
        List<RentResponseDTO> rent = rentService.getAllRents();
        log.info("!!rent!! " + rent);
        return new ResponseEntity<>(rent, HttpStatus.OK);
    }

    @PostMapping("/return/{copyId}")
    public ResponseEntity<String> returnBook(@RequestBody RentRequestDTO dto) {
        try {
            String result = rentService.adminReturnBook(dto);
            if ("Success".equals(result)) {
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/book")
    public ResponseEntity<List<BookDTO>> getBooks() {
        List<BookDTO> books = bookService.getAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @PostMapping("/book/update")
    public ResponseEntity<String> updateBook(@RequestBody Map<String, String> request) {
        try {
            String bookId = request.get("bookId");
            String bookCount = request.get("bookCount");
            String result = bookService.updateBook(bookId, bookCount);
            if ("Success".equals(result)) {
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/book/delete")
    public ResponseEntity<String> deleteBook(@RequestBody Map<String, String> request) {
        try {
            String bookId = request.get("bookId");
            String result = bookService.deleteBook(bookId);
            if ("Success".equals(result)) {
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/wish")
    public ResponseEntity<List<WishRequestDTO>> getWishBooks() {
        List<WishRequestDTO> wishBooks = wishService.getAllWishs();
        return new ResponseEntity<>(wishBooks, HttpStatus.OK);
    }

}

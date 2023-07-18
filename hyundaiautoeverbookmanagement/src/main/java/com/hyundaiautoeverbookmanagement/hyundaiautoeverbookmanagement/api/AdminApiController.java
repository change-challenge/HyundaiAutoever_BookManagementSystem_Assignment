package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.api;


import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.*;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.service.*;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.util.SecurityUtil;
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


    // GET 관련 메소드
    @GetMapping("/member")
    public ResponseEntity<List<MemberAdminResponseDTO>> getMembers() {
        List<MemberAdminResponseDTO> users = memberService.getAllMembers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/book")
    public ResponseEntity<List<BookDTO>> getBooks() {
        List<BookDTO> books = bookService.getAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/rent")
    public ResponseEntity<List<RentResponseDTO>> getRents() {
        List<RentResponseDTO> rent = rentService.getAllRents();
        return new ResponseEntity<>(rent, HttpStatus.OK);
    }

    @GetMapping("/wish")
    public ResponseEntity<List<WishRequestDTO>> getWishs() {
        List<WishRequestDTO> wishBooks = wishService.getAllWishs();
        return new ResponseEntity<>(wishBooks, HttpStatus.OK);
    }

    // Member의 권한 바꾸기
    @PatchMapping("/member/type")
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

    // Admin에서 반납하기
    @PatchMapping("/return/{copyId}")
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

    // Copy 증가 및 삭제
    @PatchMapping("/book/update")
    public ResponseEntity<String> updateBookCount(@RequestBody Map<String, String> request) {
        try {
            String bookId = request.get("bookId");
            String bookCount = request.get("bookCount");
            String result = bookService.updateBookCount(bookId, bookCount);
            if ("Success".equals(result)) {
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Book 삭제
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

    //
    @PostMapping("/wish/approve")
    public ResponseEntity<String> approveWish(@RequestBody WishRequestDTO WishDTO) {
        try {
            String result = wishService.approveWish(WishDTO);
            if ("Success".equals(result)) {
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/wish/reject")
    public ResponseEntity<String> rejectWish(@RequestBody Map<String, String> request) {
        try {
            String wishId = request.get("wishId");
            String result = wishService.rejectedWish(wishId);
            if ("Success".equals(result)) {
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/allow")
    public  ResponseEntity<String> isAllowed() {
        try {
            if (SecurityUtil.getCurrentMemberType().equals(MemberType.ADMIN)) {
                return new ResponseEntity<String>("Success", HttpStatus.OK);
            }
            else {
                return new ResponseEntity<String>("Failed", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.api;


import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.BookDTO;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.MemberDTO;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.WishRequestDTO;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.service.AdminService;
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

    private final AdminService adminService;

//    @GetMapping("/user")
//    public ResponseEntity<List<MemberDTO>> getUsers() {
//        List<MemberDTO> users = adminService.getUser();
//        return new ResponseEntity<>(users, HttpStatus.OK);
//    }
//
//    @GetMapping("/rent")
//    public ResponseEntity<String> getRents() {
//        return new ResponseEntity<String>("WHAT'S UP RENT", HttpStatus.OK);
//    }
//
//    @GetMapping("/book")
//    public ResponseEntity<List<BookDTO>> getBooks() {
//        List<BookDTO> books = adminService.getBooks();
//        return new ResponseEntity<>(books, HttpStatus.OK);
//    }
//
//    @GetMapping("/wish")
//    public ResponseEntity<List<WishRequestDTO>> getWishBooks() {
//        List<WishRequestDTO> wishBooks = adminService.getWishBooks();
//        return new ResponseEntity<>(wishBooks, HttpStatus.OK);
//    }
}

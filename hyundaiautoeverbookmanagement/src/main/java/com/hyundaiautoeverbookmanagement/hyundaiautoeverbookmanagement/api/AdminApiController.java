package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.api;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminApiController {

//    private final AdminService adminService;

    @GetMapping("/user/")
    public ResponseEntity<String> getUsers() {
        return new ResponseEntity<String>("WHAT'S UP! USER", HttpStatus.OK);
    }

    @GetMapping("/rent/")
    public ResponseEntity<String> getRents() {
        return new ResponseEntity<String>("WHAT'S UP RENT", HttpStatus.OK);
    }

    @GetMapping("/book/")
    public ResponseEntity<String> getBooks() {
        return new ResponseEntity<String>("WHAT'S UP BOOK", HttpStatus.OK);
    }

    @GetMapping("/wish/")
    public ResponseEntity<String> getWishBooks() {
        return new ResponseEntity<String>("WHAT'S UP WISH", HttpStatus.OK);
    }
}

package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.api;

import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.WishRequestDTO;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Wish;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.service.WishService;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class WishBookApiController {

    @Autowired
    private WishService wishService;

    @PostMapping("/api/wishbook/create")
    public ResponseEntity<String> createWishBook(@RequestBody WishRequestDTO form) {
        return ResponseEntity.ok(wishService.saveWishBook(form));

    }
}

package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.api;

import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.WishRequestDTO;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Wish;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.service.WishService;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishbook")
@Slf4j
public class WishBookApiController {

    @Autowired
    private WishService wishService;

    @PostMapping("/create")
    public ResponseEntity<String> createWishBook(@RequestBody WishRequestDTO form) {
        log.info("form!!! : " + form);
        return ResponseEntity.ok(wishService.saveWish(form));
    }

    @GetMapping("/read")
    public ResponseEntity<List<WishRequestDTO>> getWishBook(@RequestParam String userEmail) {
        List<WishRequestDTO> wishBooks = wishService.getWish(userEmail);
        return new ResponseEntity<>(wishBooks, HttpStatus.OK);
    }
}

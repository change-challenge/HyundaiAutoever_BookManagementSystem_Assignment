package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.api;

import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.WishRequestDTO;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.service.WishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wish")
@Slf4j
public class WishApiController {

    @Autowired
    private WishService wishService;

    @PostMapping("/create")
    public ResponseEntity<String> createWish(@RequestBody WishRequestDTO form) {
        return ResponseEntity.ok(wishService.saveWish(form));
    }

    @GetMapping("/read")
    public ResponseEntity<List<WishRequestDTO>> getWish(@RequestParam String email) {
        List<WishRequestDTO> wishBooks = wishService.getWishByEmail(email);
        return new ResponseEntity<>(wishBooks, HttpStatus.OK);
    }
}

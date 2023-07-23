package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.api;

import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.BookRequestDTO;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/book")
@Slf4j
public class BookApiController {
    private final BookService bookService;

    @GetMapping("/search")
    public ResponseEntity<List<BookRequestDTO>> searchBookList(@RequestParam String title) {
        List<BookRequestDTO> books = bookService.searchBooks(title);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<BookRequestDTO> getBookDetail(@PathVariable Long id) {
        BookRequestDTO book = bookService.getBookDetail(id);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

}

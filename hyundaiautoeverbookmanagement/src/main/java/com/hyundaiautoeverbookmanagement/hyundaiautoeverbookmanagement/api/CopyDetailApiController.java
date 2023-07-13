package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.api;

import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.CopyDetailDTO;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/bookdetail")
@Slf4j
public class CopyDetailApiController {

    private final BookService bookService;
    @GetMapping("/{bookId}")
    public ResponseEntity<List<CopyDetailDTO>> getBookDetail(@PathVariable Long bookId) {
        List<CopyDetailDTO> copyDetail = bookService.getCopyDetails(bookId);
        return new ResponseEntity<>(copyDetail, HttpStatus.OK);
    }

}

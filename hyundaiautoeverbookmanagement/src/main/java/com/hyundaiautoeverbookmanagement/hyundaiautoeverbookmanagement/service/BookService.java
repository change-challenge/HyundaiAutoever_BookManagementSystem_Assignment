package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.service;

import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.BookDTO;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Book;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.BookStatus;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Copy;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.repository.BookRepository;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.repository.CopyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;
    private final CopyRepository copyRepository;

    public List<BookDTO> searchBooks(String title) {
        List<Book> books = bookRepository.findByTitleContaining(title);
        List<BookDTO> bookDtos = new ArrayList<>();

        for (Book book : books) {
            BookDTO bookDto = new BookDTO();

            bookDto.setId(book.getId());
            bookDto.setTitle(book.getTitle());
            bookDto.setAuthor(book.getAuthor());
            bookDto.setPublisher(book.getPublisher());
            bookDto.setCategory(book.getCategory());
            bookDto.setIsbn(book.getIsbn());
            bookDto.setInfo(book.getInfo());
            bookDto.setCover(book.getCover());
            bookDto.setPubDate(book.getPubDate());

            List<Copy> copies = copyRepository.findByBook(book);
            bookDto.setBookStatus(copies.stream().anyMatch((copy) ->
                    copy.getBookStatus() == BookStatus.AVAILABLE) ? true : false);
            bookDtos.add(bookDto);
        }
        return bookDtos;
    }


//    public BookDTO findBookById(Long bookId) {
//        return bookRepository.findById(bookId)
//                .map(BookDTO::of)
//                .orElseThrow(() -> new RuntimeException("해당 책 정보가 없습니다."));
//    }
}

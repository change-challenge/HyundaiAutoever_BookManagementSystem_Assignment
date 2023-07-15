package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.service;

import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.BookDTO;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.CopyDetailDTO;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Book;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.BookStatus;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Copy;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Rent;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.repository.BookRepository;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.repository.CopyRepository;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.repository.RentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;
    private final CopyRepository copyRepository;
    private final RentRepository rentRepository;

    public List<BookDTO> searchBooks(String title) {
        List<Book> books = bookRepository.findByTitleContaining(title);

        return books.stream()
                .map(this::mapToBookDTO)
                .collect(Collectors.toList());
    }

    public List<BookDTO> getAllBooks() {
        List<Book> books = bookRepository.findAll();

        return books.stream()
                .map(this::mapToBookDTO)
                .collect(Collectors.toList());
    }

    private BookDTO mapToBookDTO(Book book) {
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

        int bookCount = copyRepository.countByBook(book);
        bookDto.setBookCount(bookCount);

        List<Copy> copies = copyRepository.findByBook(book);
        bookDto.setBookStatus(copies.stream()
                .anyMatch(copy -> copy.getBookStatus() == BookStatus.AVAILABLE));

        return bookDto;
    }

    public BookDTO getBookDetail(Long id) {
        Book book = bookRepository.findById(id).orElse(null);
        return mapToBookDTO(book);
    }

    public List<CopyDetailDTO> getCopyDetails(Long bookId) {
        // 책에 해당하는 모든 사본 찾기
        List<Copy> copies = copyRepository.findByBookId(bookId);

        // 결과를 저장할 리스트 생성
        List<CopyDetailDTO> bookDetails = new ArrayList<>();

        for (Copy copy : copies) {
            // 사본의 상태가 UNAVAILABLE인 경우에만 처리
            CopyDetailDTO dto = new CopyDetailDTO();
            dto.setCopyId(copy.getId());
            if (copy.getBookStatus().equals(BookStatus.UNAVAILABLE)) {
                Rent rent = rentRepository.findFirstByCopyIdOrderByEndDateDesc(copy.getId());
                if (rent != null) {
                    dto.setEndDate(Optional.ofNullable(rent.getEndDate()));
                } else {
                    dto.setEndDate(Optional.empty());
                }
            }
            bookDetails.add(dto);
        }
        return bookDetails;
    }
}
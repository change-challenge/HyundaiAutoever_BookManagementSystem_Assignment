package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.service;

import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.BookRequestDTO;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.type.BookStatus;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.CopyDetailDTO;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.*;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.exception.BookException;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.repository.BookRepository;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.repository.CopyRepository;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.repository.MemberRepository;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.repository.RentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.util.SecurityUtil.checkAdminAuthority;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;
    private final CopyRepository copyRepository;
    private final RentRepository rentRepository;
    private final MemberRepository memberRepository;


    // 도서명으로 BOOK 검색
    public List<BookRequestDTO> searchBooks(String title) {
        List<Book> books = bookRepository.findByTitleContaining(title);

        return books.stream()
                .map(this::ToBookDTO)
                .collect(Collectors.toList());
    }


    // BOOK ID를 통해 BOOK DETAIL 정보 가져오기
    public BookRequestDTO getBookDetail(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookException("getBookDetail : 해당 ID에 대한 Book이 존재하지 않습니다."));
        return ToBookDTO(book);
    }

    // BOOK ID를 통해 COPY 정보 가쟈오기 (CopyDetailDTO)
    public List<CopyDetailDTO> getCopyDetails(Long bookId) {

        // 1. BOOK ID에 해당하는 모든 COPY 가져오기
        List<Copy> copies = copyRepository.findByBookId(bookId);

        // 2. CopyDetailDTO에 해당 정보 채우기
        List<CopyDetailDTO> bookDetails = new ArrayList<>();
        for (Copy copy : copies) {
            // Copy의 상태가 UNAVAILABLE인 경우에만 처리
            CopyDetailDTO dto = new CopyDetailDTO();
            dto.setCopyId(copy.getId());
            if (copy.getBookStatus().equals(BookStatus.UNAVAILABLE)) {
                // Copy에 따라 Rent는 여러개일 수 있다.
                // 그러기에 가장 최근꺼를 가져와야한다.
                // 다만, 같은 책을 한 사람이 여러번 빌릴 수 없기에 '대출중'인 것이 있다면 가장 최근 Rent이다.
                Rent rent = rentRepository.findFirstByCopyIdAndReturnedDateIsNullOrderByEndDateDesc(copy.getId());
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

    // -----------------------------
    // |        Admin 권한관련        |
    // -----------------------------

    // ADMIN의 모든 BOOK GET SERVICE
    public List<BookRequestDTO> getAllBooks() {
        List<Book> books = bookRepository.findAll();

        return books.stream()
                .map(this::ToBookDTO)
                .collect(Collectors.toList());
    }

    // ADMIN의 도서 수량 변경 Service
    @Transactional
    public String updateBookCount(String bookIdStr, String bookCountStr) {
        Long bookId = Long.parseLong(bookIdStr);
        int bookCount = Integer.parseInt(bookCountStr);

        // 1. 신청자가 ADMIN인 지 확인
        checkAdminAuthority();

        // 2. BOOK ID로 BOOK을 찾는다.
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookException("updateBookCount : 해당 ID의 도서가 존재하지 않습니다."));

        // 3. Copy의 갯수를 새어서 BOOK COUNT와 비교하여 BOOK COUNT가 더 많으면 그 만큼 Copy를 만들어야한다.
        List<Copy> copies = copyRepository.findByBook(book);
        int currentCount = copies.size();
        if (currentCount < bookCount) {
            for (int i = 0; i < bookCount - currentCount; i++) {
                Copy newCopy = new Copy();
                newCopy.setBook(book);
                newCopy.setBookStatus(BookStatus.AVAILABLE);
                copyRepository.save(newCopy);
            }
        }
        // 3-1. 반대로 BOOK COUNT가 더 적으면 COPY ID 큰 순으로 지워야한다. (최신순)
        else if (currentCount > bookCount) {
            copies.sort(Comparator.comparing(Copy::getId).reversed());
            for (int i = 0; i < currentCount - bookCount; i++) {
                deleteRentRelatedCopyAndCopy(copies, i);
            }
        }
        return "Success";
    }

    // ADMIN의 도서 삭제 Service
    @Transactional
    public String deleteBook(String bookIdStr) {
        Long bookId = Long.parseLong(bookIdStr);

        // 1. 신청자가 ADMIN인 지 확인
        checkAdminAuthority();

        // 2. BOOK ID로 BOOK을 찾는다.
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookException("deleteBook : 해당 ID의 도서가 존재하지 않습니다."));

        // 3. BOOK ID를 가지고 있는 모든 Copy를 삭제한다.
        List<Copy> copies = copyRepository.findByBook(book);
        int currentCount = copies.size();
        if (currentCount != 0) {
            for (int i = 0; i < currentCount; i++) {
                deleteRentRelatedCopyAndCopy(copies, i);
            }
        }
        // 4. 마지막으로 BOOK ID에 해당하는 BOOK도 삭제한다.
        bookRepository.delete(book);
        return "Success";
    }

    // -----------------------------
    // |        내장 함수 관련         |
    // -----------------------------

    // COPY에 해당하는 RENT가 존재 시, 모두 삭제
    public void deleteRentRelatedCopyAndCopy(List<Copy> copies, int i) {
        List<Rent> rents = rentRepository.findByCopyId(copies.get(i).getId());
        int currentRentCount = rents.size();
        for (int j = 0; j < currentRentCount; j++) {
            Member member = rents.get(j).getMember();
            member.setRentCount(member.getRentCount() - 1);
            memberRepository.save(member);
            rentRepository.delete(rents.get(j));
        }
        copyRepository.delete(copies.get(i));
    }

    // BOOK(ENTITY) -> BOOKDTO(DTO)
    private BookRequestDTO ToBookDTO(Book book) {
        BookRequestDTO bookRequestDto = new BookRequestDTO();

        bookRequestDto.setId(book.getId());
        bookRequestDto.setTitle(book.getTitle());
        bookRequestDto.setAuthor(book.getAuthor());
        bookRequestDto.setPublisher(book.getPublisher());
        try {
            bookRequestDto.setCategory(book.getCategory().getDescription());
        } catch (IllegalArgumentException e) {
            log.info("book Category 문제");
        }
        bookRequestDto.setIsbn(book.getIsbn());
        bookRequestDto.setInfo(book.getInfo());
        bookRequestDto.setCover(book.getCover());
        bookRequestDto.setPubDate(book.getPubDate());

        int bookCount = copyRepository.countByBook(book);
        bookRequestDto.setBookCount(bookCount);

        List<Copy> copies = copyRepository.findByBook(book);
        bookRequestDto.setBookStatus(copies.stream()
                .anyMatch(copy -> copy.getBookStatus() == BookStatus.AVAILABLE));

        return bookRequestDto;
    }
}
package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.service;

import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.BookDTO;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.CopyDetailDTO;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.type.BookStatus;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Book;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Copy;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Rent;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.repository.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
@DisplayName("BookService에 대한 테스트")
class BookServiceTest {

    @Mock
    WishRepository wishRepository;
    @Mock
    BookRepository bookRepository;

    @Mock
    CopyRepository copyRepository;
    @Mock
    RentRepository rentRepository;
    @Mock
    MemberRepository memberRepository;

    @InjectMocks
    BookService bookService;

    @AfterEach
    @DisplayName("SecurityContextHolder 컨텍스트 클리어")
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("Book DB에 있는 Title 검색한 테스트")
    public void ShouldSearchBooksWhenGivenTitleMatches() {
        List<Book> expectedBooks = Arrays.asList(new Book(), new Book());
        when(bookRepository.findByTitleContaining("sampleTitle")).thenReturn(expectedBooks);

        List<BookDTO> result = bookService.searchBooks("sampleTitle");

        assertEquals(expectedBooks.size(), result.size());
    }

    @Test
    @DisplayName("Book DB에 없는 Title 검색한 테스트")
    public void ShouldReturnEmptyListWhenGivenTitleDoesNotMatch() {
        when(bookRepository.findByTitleContaining("wrongTitle")).thenReturn(Collections.emptyList());

        List<BookDTO> result = bookService.searchBooks("wrongTitle");

        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("모든 Book 가져오는 테스트")
    public void ShouldReturnAllBooks() {
        List<Book> expectedBooks = Arrays.asList(new Book(), new Book(), new Book());
        when(bookRepository.findAll()).thenReturn(expectedBooks);

        List<BookDTO> result = bookService.getAllBooks();

        assertEquals(expectedBooks.size(), result.size());
    }

    @Test
    @DisplayName("Book 하나를 가져오는 테스트")
    public void ShouldReturnOneBook() {
        List<Book> expectedBooks = Arrays.asList(new Book());
        when(bookRepository.findAll()).thenReturn(expectedBooks);

        List<BookDTO> result = bookService.getAllBooks();

        assertEquals(expectedBooks.size(), result.size());
    }

    @Test
    @DisplayName("Book가 없을 때 가져오는 테스트")
    public void ShouldNotReturnAllBooks() {;
        when(bookRepository.findAll()).thenReturn(Collections.emptyList());

        List<BookDTO> result = bookService.getAllBooks();

        assertEquals(0, result.size());
    }

    @Test
    @DisplayName("id가 Valid하면 BookDetail을 가져오는 테스트")
    public void ShouldReturnBookDetail() {
        Book expectedBook = new Book();
        when(bookRepository.findById(1L)).thenReturn(Optional.of(expectedBook));

        BookDTO result = bookService.getBookDetail(1L);

        assertNotNull(result);
    }

    @Test
    @DisplayName("id가 Valid하지않아 BookDetail을 가져오지 않는 테스트")
    public void ShouldNotReturnBookDetail() {
        when(bookRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> bookService.getBookDetail(99L));
    }

    @Test
    @DisplayName("bookId가 정확하지 않아 Copy를 가져오지 못하는 케이스 - getCopyDetails")
    public void ShouldNotReturnCopyDetailsIfBookIdIsNotValid() {

        when(copyRepository.findByBookId(anyLong())).thenReturn(Collections.emptyList());

        List<CopyDetailDTO> result = bookService.getCopyDetails(anyLong());

        assertEquals(0, result.size());
    }


    @Test
    @DisplayName("모든 Copy가 대출 중이 아닌 상태에서 CopyDetail 가져오는 테스트 - getCopyDetails")
    public void ShouldReturnCopyDetailsWhenAllBookStatusIsAvailable() {
        List<Copy> copies = Arrays.asList(new Copy(), new Copy(), new Copy());
        copies.stream().map(copy -> {
            copy.setBookStatus(BookStatus.AVAILABLE);
            return copy;
        }).collect(Collectors.toList());
        when(copyRepository.findByBookId(1L)).thenReturn(copies);
        List<CopyDetailDTO> result = bookService.getCopyDetails(1L);

        assertEquals(3, result.size());
        assertNull(result.get(0).getEndDate());
    }

    @Test
    @DisplayName("모든 Copy가 대출 중인 상태에서 CopyDetail 가져오는 테스트 - getCopyDetails")
    public void ShouldReturnCopyDetailsWhenAllBookStatusIsUnavailable() {
        List<Copy> copies = IntStream.range(0, 3)
                .mapToObj(index -> {
                    Copy copy = new Copy();
                    copy.setBookStatus(BookStatus.UNAVAILABLE);
                     copy.setId((long) index);
                    return copy;
                })
                .collect(Collectors.toList());
        when(copyRepository.findByBookId(1L)).thenReturn(copies);
        Rent rent = new Rent();
        rent.setEndDate(LocalDate.now());
        when(rentRepository.findFirstByCopyIdAndReturnedDateIsNullOrderByEndDateDesc(anyLong())).thenReturn(rent);

        List<CopyDetailDTO> result = bookService.getCopyDetails(1L);

        assertEquals(3, result.size());
        assertNotNull(result.get(0).getEndDate());
    }

    @Test
    @DisplayName("Admin이 아니기 때문에 UpdateBookCount 실패 사례 - 초기")
    void shouldNotUpdateBookCountIfNotAdmin() {
        // 예상
        // MEMBER로 사용자로 설정
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("1"); // 원하는 사용자 ID를 설정
        doReturn(List.of(new SimpleGrantedAuthority("MEMBER"))).when(authentication).getAuthorities();
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // 실제 및 결과
        assertThrows(RuntimeException.class, () -> bookService.updateBookCount("1", "5"));
    }

    @Test
    @DisplayName("BookId가 Valid하지 않기 때문에 UpdateBookCount 실패 사례")
    void shouldNotUpdateBookCountIfBookIdNotValid() {
        // 예상
        // ADMIN으로 사용자로 설정
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("1"); // 원하는 사용자 ID를 설정
        doReturn(List.of(new SimpleGrantedAuthority("ADMIN"))).when(authentication).getAuthorities();
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> bookService.updateBookCount("1", "5"));
    }

    @Test
    @DisplayName("현재 Copy 수보다 더 많은 bookCount를 지정했을 때, 올바른 수의 Copy가 생성되는 케이스")
    public void shouldUpdateBookCountWhenBookCountMoreThanCopy() {
        // 예상
        // ADMIN으로 사용자로 설정
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("1"); // 원하는 사용자 ID를 설정
        doReturn(List.of(new SimpleGrantedAuthority("ADMIN"))).when(authentication).getAuthorities();
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // 예상
        Book book = new Book();
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        when(copyRepository.findByBook(book)).thenReturn(Collections.emptyList());

        // When
        bookService.updateBookCount("1", "5");

        // Then
        verify(copyRepository, times(5)).save(any());
    }

    @Test
    @DisplayName("현재 Copy 수보다 더 적은 bookCount를 지정했을 때, 올바른 수의 Copy가 삭제되는 케이스")
    public void shouldUpdateBookCountWhenCopyMoreThanBookCount() {
        // 예상
        // ADMIN으로 사용자로 설정
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("1"); // 원하는 사용자 ID를 설정
        doReturn(List.of(new SimpleGrantedAuthority("ADMIN"))).when(authentication).getAuthorities();
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // 예상
        Book mockBook = new Book();
        Copy mockCopy1 = new Copy();
        mockCopy1.setId(1L);
        Copy mockCopy2 = new Copy();
        mockCopy2.setId(2L);
        Copy mockCopy3 = new Copy();
        mockCopy3.setId(2L);
        Copy mockCopy4 = new Copy();
        mockCopy4.setId(2L);

        List<Copy> mockCopies = Arrays.asList(mockCopy1, mockCopy2,mockCopy3,mockCopy4);
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(mockBook));
        when(copyRepository.findByBook(mockBook)).thenReturn(mockCopies);

        // Spy 생성
        BookService bookServiceSpy = Mockito.spy(bookService);

        // deleteRentRelatedCopyAndCopy 메서드를 mock 처리
        doNothing().when(bookServiceSpy).deleteRentRelatedCopyAndCopy(mockCopies, 0);

        // When
        bookServiceSpy.updateBookCount("1", "1");

        // Then
        verify(bookServiceSpy, times(1)).deleteRentRelatedCopyAndCopy(mockCopies, 0);
    }

    @Test
    @DisplayName("Admin이 아니기 때문에 deleteBook 실패 사례")
    void shouldNotDeleteBookIfNotAdmin() {
        // 예상
        // MEMBER로 사용자로 설정
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("1"); // 원하는 사용자 ID를 설정
        doReturn(List.of(new SimpleGrantedAuthority("MEMBER"))).when(authentication).getAuthorities();
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // 실제 및 결과
        assertThrows(RuntimeException.class, () -> bookService.deleteBook("1"));
    }

    @Test
    @DisplayName("BookId가 Valid하지 않기 때문에 deleteBook 실패 사례")
    void shouldNotDeleteBookIfNotAdminBookIdNotValid() {
        // 예상
        // ADMIN로 사용자로 설정
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("1"); // 원하는 사용자 ID를 설정
        doReturn(List.of(new SimpleGrantedAuthority("ADMIN"))).when(authentication).getAuthorities();
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        // 실제 및 결과
        assertThrows(RuntimeException.class, () -> bookService.deleteBook("1"));
    }

    @Test
    @DisplayName("Book 삭제가 성공하는 케이스")
    public void shouldDeleteBook() {
        // 예상
        // ADMIN으로 사용자로 설정
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("1"); // 원하는 사용자 ID를 설정
        doReturn(List.of(new SimpleGrantedAuthority("ADMIN"))).when(authentication).getAuthorities();
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // 예상
        Book mockBook = new Book();
        Copy mockCopy1 = new Copy();
        mockCopy1.setId(1L);
        Copy mockCopy2 = new Copy();
        mockCopy2.setId(2L);
        Copy mockCopy3 = new Copy();
        mockCopy3.setId(2L);
        Copy mockCopy4 = new Copy();
        mockCopy4.setId(2L);
        List<Copy> mockCopies = Arrays.asList(mockCopy1, mockCopy2, mockCopy3, mockCopy4);

        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(mockBook));
        when(copyRepository.findByBook(mockBook)).thenReturn(mockCopies);

        // Spy 생성
        BookService bookServiceSpy = Mockito.spy(bookService);

        // deleteRentRelatedCopyAndCopy 메서드를 mock 처리
        doNothing().when(bookServiceSpy).deleteRentRelatedCopyAndCopy(mockCopies, 0);

        // When
        bookServiceSpy.deleteBook("1");

        // Then
        verify(bookServiceSpy, times(1)).deleteRentRelatedCopyAndCopy(mockCopies, 0);
    }
}
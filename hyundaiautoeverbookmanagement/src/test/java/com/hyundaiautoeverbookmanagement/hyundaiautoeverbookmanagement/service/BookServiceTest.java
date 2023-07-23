package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.service;

import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.BookRequestDTO;
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
    @DisplayName("searchBooks 시, Book DB에 있는 Title 검색한 성공 사례")
    public void ShouldSearchBooksWhenGivenTitleMatches() {
        // given
        List<Book> expectedBooks = Arrays.asList(new Book(), new Book());
        when(bookRepository.findByTitleContaining("sampleTitle")).thenReturn(expectedBooks);

        // when
        List<BookRequestDTO> result = bookService.searchBooks("sampleTitle");

        // then
        assertEquals(expectedBooks.size(), result.size());
    }

    @Test
    @DisplayName("SearchBooks 시, Book DB에 없는 Title 검색한 실패 사례")
    public void ShouldReturnEmptyListWhenGivenTitleDoesNotMatch() {
        // given
        when(bookRepository.findByTitleContaining("wrongTitle")).thenReturn(Collections.emptyList());

        // when
        List<BookRequestDTO> result = bookService.searchBooks("wrongTitle");

        // then
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("getAllBooks 시, 모든 Book 가져오는 성공 사례")
    public void ShouldReturnAllBooks() {
        // given
        List<Book> expectedBooks = Arrays.asList(new Book(), new Book(), new Book());
        when(bookRepository.findAll()).thenReturn(expectedBooks);

        // when
        List<BookRequestDTO> result = bookService.getAllBooks();

        // then
        assertEquals(expectedBooks.size(), result.size());
    }

    @Test
    @DisplayName("getAllBooks 시, Book 하나를 가져오는 성공 사례")
    public void ShouldReturnOneBook() {
        // given
        List<Book> expectedBooks = Arrays.asList(new Book());
        when(bookRepository.findAll()).thenReturn(expectedBooks);

        // when
        List<BookRequestDTO> result = bookService.getAllBooks();

        // then
        assertEquals(expectedBooks.size(), result.size());
    }

    @Test
    @DisplayName("getAllBooks 시, Book이 없는 실패 사례")
    public void ShouldNotReturnAllBooks() {;
        // given
        when(bookRepository.findAll()).thenReturn(Collections.emptyList());

        // when
        List<BookRequestDTO> result = bookService.getAllBooks();

        // then
        assertEquals(0, result.size());
    }

    @Test
    @DisplayName("getBookDetail 시, id가 Valid하면 BookDetail을 가져오는 성공 사례")
    public void ShouldReturnBookDetail() {
        // given
        Book expectedBook = new Book();
        when(bookRepository.findById(1L)).thenReturn(Optional.of(expectedBook));

        // when
        BookRequestDTO result = bookService.getBookDetail(1L);

        // then
        assertNotNull(result);
    }

    @Test
    @DisplayName("getBookDetail 시, id가 Valid 하지않아 BookDetail을 가져오지 않는 실패 사례")
    public void ShouldNotReturnBookDetail() {
        // given & when
        when(bookRepository.findById(99L)).thenReturn(Optional.empty());

        // then
        assertThrows(RuntimeException.class, () -> bookService.getBookDetail(99L));
    }

    @Test
    @DisplayName("getCopyDetails 시, bookId가 정확하지 않아 Copy를 가져오지 못하는 실패 사례")
    public void ShouldNotReturnCopyDetailsIfBookIdIsNotValid() {
        // given
        when(copyRepository.findByBookId(anyLong())).thenReturn(Collections.emptyList());

        // when
        List<CopyDetailDTO> result = bookService.getCopyDetails(anyLong());

        // then
        assertEquals(0, result.size());
    }


    @Test
    @DisplayName("getCopyDetails 시, 모든 Copy가 대출 중이 아닌 상태에서 CopyDetail 가져오는 성공 사례")
    public void ShouldReturnCopyDetailsWhenAllBookStatusIsAvailable() {
        // given
        List<Copy> copies = Arrays.asList(new Copy(), new Copy(), new Copy());
        copies.stream().map(copy -> {
            copy.setBookStatus(BookStatus.AVAILABLE);
            return copy;
        }).collect(Collectors.toList());

        // when
        when(copyRepository.findByBookId(1L)).thenReturn(copies);
        List<CopyDetailDTO> result = bookService.getCopyDetails(1L);

        // then
        assertEquals(3, result.size());
        assertNull(result.get(0).getEndDate());
    }

    @Test
    @DisplayName("getCopyDetails 시, 모든 Copy가 대출 중인 상태에서 CopyDetail 가져오는 성공 사례")
    public void ShouldReturnCopyDetailsWhenAllBookStatusIsUnavailable() {
        // given
        List<Copy> copies = IntStream.range(0, 3)
                .mapToObj(index -> {
                    Copy copy = new Copy();
                    copy.setBookStatus(BookStatus.UNAVAILABLE);
                     copy.setId((long) index);
                    return copy;
                })
                .collect(Collectors.toList());

        // when
        when(copyRepository.findByBookId(1L)).thenReturn(copies);
        Rent rent = new Rent();
        rent.setEndDate(LocalDate.now());
        when(rentRepository.findFirstByCopyIdAndReturnedDateIsNullOrderByEndDateDesc(anyLong())).thenReturn(rent);
        List<CopyDetailDTO> result = bookService.getCopyDetails(1L);

        // then
        assertEquals(3, result.size());
        assertNotNull(result.get(0).getEndDate());
    }

    @Test
    @DisplayName("updateBookCount 시, Admin이 아니기 때문에 UpdateBookCount 실패 사례")
    void shouldNotUpdateBookCountIfNotAdmin() {
        // given
        // MEMBER로 사용자로 설정
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("1"); // 원하는 사용자 ID를 설정
        doReturn(List.of(new SimpleGrantedAuthority("MEMBER"))).when(authentication).getAuthorities();
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // when & then
        assertThrows(RuntimeException.class, () -> bookService.updateBookCount("1", "5"));
    }

    @Test
    @DisplayName("updateBookCount 시, BookId가 Valid하지 않는 실패 사례")
    void shouldNotUpdateBookCountIfBookIdNotValid() {
        // given
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
    @DisplayName("updateBookCount 시, 현재 Copy 수보다 더 많은 bookCount를 지정했을 때, 올바른 수의 Copy가 생성되는 성공 사례")
    public void shouldUpdateBookCountWhenBookCountMoreThanCopy() {
        // given
        // ADMIN으로 사용자로 설정
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("1"); // 원하는 사용자 ID를 설정
        doReturn(List.of(new SimpleGrantedAuthority("ADMIN"))).when(authentication).getAuthorities();
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        Book book = new Book();
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        when(copyRepository.findByBook(book)).thenReturn(Collections.emptyList());

        // when
        bookService.updateBookCount("1", "5");

        // then
        verify(copyRepository, times(5)).save(any());
    }

    @Test
    @DisplayName("updateBookCount 시, 현재 Copy 수보다 더 적은 bookCount를 지정했을 때, 올바른 수의 Copy가 삭제되는 성공 사례")
    public void shouldUpdateBookCountWhenCopyMoreThanBookCount() {
        // given
        // ADMIN으로 사용자로 설정
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("1"); // 원하는 사용자 ID를 설정
        doReturn(List.of(new SimpleGrantedAuthority("ADMIN"))).when(authentication).getAuthorities();
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

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
    @DisplayName("deleteBook 시, Admin이 아니기 때문에 deleteBook 실패 사례")
    void shouldNotDeleteBookIfNotAdmin() {
        // given
        // MEMBER로 사용자로 설정
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("1"); // 원하는 사용자 ID를 설정
        doReturn(List.of(new SimpleGrantedAuthority("MEMBER"))).when(authentication).getAuthorities();
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // when & then
        assertThrows(RuntimeException.class, () -> bookService.deleteBook("1"));
    }

    @Test
    @DisplayName("deleteBook 시, BookId가 Valid하지 않기 때문에 deleteBook 실패 사례")
    void shouldNotDeleteBookIfNotAdminBookIdNotValid() {
        // given
        // ADMIN로 사용자로 설정
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("1"); // 원하는 사용자 ID를 설정
        doReturn(List.of(new SimpleGrantedAuthority("ADMIN"))).when(authentication).getAuthorities();
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // when
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        // then
        assertThrows(RuntimeException.class, () -> bookService.deleteBook("1"));
    }

    @Test
    @DisplayName("deleteBook 시, Book 삭제가 성공 사례")
    public void shouldDeleteBook() {
        // given
        // ADMIN으로 사용자로 설정
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("1"); // 원하는 사용자 ID를 설정
        doReturn(List.of(new SimpleGrantedAuthority("ADMIN"))).when(authentication).getAuthorities();
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // given
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
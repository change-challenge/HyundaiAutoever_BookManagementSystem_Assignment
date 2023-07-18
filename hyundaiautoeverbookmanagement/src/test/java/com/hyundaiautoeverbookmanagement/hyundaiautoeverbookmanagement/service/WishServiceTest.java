package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.service;

import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.BookDTO;
import org.junit.jupiter.api.AfterEach;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.type.WishStatus;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Wish;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.repository.BookRepository;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.repository.CopyRepository;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.repository.WishRepository;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.WishRequestDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.*;

import org.junit.jupiter.api.DisplayName;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("WishService에 대한 테스트")
class WishServiceTest {

    @Mock
    WishRepository wishRepository;
    @Mock
    BookRepository bookRepository;
    @Mock
    CopyRepository copyRepository;
    @InjectMocks
    WishService wishService;

    @AfterEach
    @DisplayName("SecurityContextHolder 컨텍스트 클리어")
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("모든 Wish 가져오는 성공 사례")
    void shouldGetAllWishs() {
        // 예상
        Wish wish = new Wish();
        when(wishRepository.findAll()).thenReturn(Arrays.asList(wish));

        // 실제
        List<WishRequestDTO> result = wishService.getAllWishs();

        // 결과
        assertEquals(1, result.size());
        verify(wishRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("DB에 Wish가 없는 실패 사례")
    void shouldNotGetAllWishs() {
        // 예상
        when(wishRepository.findAll()).thenReturn(Collections.emptyList());

        // 실제
        List<WishRequestDTO> result = wishService.getAllWishs();

        // 결과
        assertEquals(0, result.size());
        verify(wishRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Email 검색으로 다양한 Wish 가져오는 성공 사례")
    void shouldGetWishByEmail() {
        // 예상
        Wish wish1 = new Wish();
        wish1.setMemberEmail("test@email.com");
        Wish wish2 = new Wish();
        wish2.setMemberEmail("test@email.com");

        when(wishRepository.findByMemberEmail("test@email.com")).thenReturn(Arrays.asList(wish1, wish2));

        // 실제
        List<WishRequestDTO> result = wishService.getWishByEmail("test@email.com");

        // 결과
        assertEquals(2, result.size());
        verify(wishRepository, times(1)).findByMemberEmail("test@email.com");
    }

    @Test
    @DisplayName("Email 검색 후 하나 Wish 가져오는 성공 사례")
    void shouldGetOneWishByEmail() {
        // 예상
        Wish wish = new Wish();
        wish.setMemberEmail("test@email.com");

        when(wishRepository.findByMemberEmail("test@email.com")).thenReturn(Arrays.asList(wish));

        // 실제
        List<WishRequestDTO> result = wishService.getWishByEmail("test@email.com");

        // 결과
        assertEquals(1, result.size());
        verify(wishRepository, times(1)).findByMemberEmail("test@email.com");
    }

    @Test
    @DisplayName("해석할 수 없는 Email의 Wish가 없는 실패 사례")
    void shouldNotGetWishByInvalidEmail() {
        // 예상
        when(wishRepository.findByMemberEmail("invalid")).thenReturn(Collections.emptyList());

        // 실제
        List<WishRequestDTO> result = wishService.getWishByEmail("invalid");

        // 결과
        assertEquals(0, result.size());
        verify(wishRepository, times(1)).findByMemberEmail("invalid");
    }

    @Test
    @DisplayName("이메일이 null이여서 꺼낼 Wish가 없는 실패 사례")
    void shouldNotGetWishByNullEmail() {
        // 예상
        when(wishRepository.findByMemberEmail(null)).thenReturn(Collections.emptyList());

        // 실제
        List<WishRequestDTO> result = wishService.getWishByEmail(null);

        // 결과
        assertEquals(0, result.size());
        verify(wishRepository, times(1)).findByMemberEmail(null);
    }

    @Test
    @DisplayName("Book DB에 중복된 ISBN을 가진 책이 없을 때, saveWish 성공 사례")
    void shouldSaveWish() {
        // 예상
        WishRequestDTO form = new WishRequestDTO();
        form.setBook(new BookDTO());
        form.getBook().setIsbn("1234567890");
        when(bookRepository.existsByIsbn("1234567890")).thenReturn(false);
        Wish wish = form.toEntity();
        when(wishRepository.save(any(Wish.class))).thenReturn(wish);

        // 실제
        String result = wishService.saveWish(form);

        // 결과
        assertEquals("Success", result);
        verify(bookRepository, times(1)).existsByIsbn("1234567890");
        verify(wishRepository, times(1)).save(any(Wish.class));
    }

    @Test
    @DisplayName("Book DB에 중복된 ISBN을 가진 책이 있을 때, saveWish 실패 사례")
    void shouldNotSaveWishIfBookExists() {
        // 예상
        WishRequestDTO form = new WishRequestDTO();
        form.setBook(new BookDTO());
        form.getBook().setIsbn("1234567890");
        when(bookRepository.existsByIsbn("1234567890")).thenReturn(true);

        // 실제 및 결과
        assertThrows(RuntimeException.class, () -> wishService.saveWish(form));
        verify(bookRepository, times(1)).existsByIsbn("1234567890");
    }

    @Test
    @DisplayName("Admin이 아니기 때문에 RejectWish 실패 사례")
    void shouldNotRejectWishIfNotAdmin() {
        // 예상
        // MEMBER로 사용자로 설정
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("1"); // 원하는 사용자 ID를 설정
        doReturn(List.of(new SimpleGrantedAuthority("MEMBER"))).when(authentication).getAuthorities();
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // 실제 및 결과
        assertThrows(RuntimeException.class, () -> wishService.rejectedWish("1"));
    }

    @Test
    @DisplayName("Wish가 없어서 RejectWish 실패 사례")
    void shouldNotRejectWishIfWishNotFound() {
        // 예상
        // ADMIN으로 사용자로 설정
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("1"); // 원하는 사용자 ID를 설정
        doReturn(List.of(new SimpleGrantedAuthority("ADMIN"))).when(authentication).getAuthorities();
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(wishRepository.findById(anyLong())).thenReturn(Optional.empty());

        // 실제 및 결과
        assertThrows(RuntimeException.class, () -> wishService.rejectedWish("1"));
        verify(wishRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("RejectWish 성공 사례")
    void shouldRejectWish() {
        // 예상
        // ADMIN으로 사용자로 설정
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("1"); // 원하는 사용자 ID를 설정
        doReturn(List.of(new SimpleGrantedAuthority("ADMIN"))).when(authentication).getAuthorities();
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        Wish wish = new Wish();
        wish.setWishStatus(WishStatus.PENDING);
        when(wishRepository.findById(anyLong())).thenReturn(Optional.of(wish));
        when(wishRepository.save(any(Wish.class))).thenReturn(wish);

        // 실제
        String result = wishService.rejectedWish("1");

        // 결과
        assertEquals("Success", result);
        verify(wishRepository, times(1)).findById(anyLong());
        verify(wishRepository, times(1)).save(any(Wish.class));
    }

    @Test
    @DisplayName("Admin이 아니기 때문에 approveWish 불가 사례")
    void shouldNotApproveWishIfNotAdmin() {
        // 예상
        // MEMBER로 사용자로 설정
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("1"); // 원하는 사용자 ID를 설정
        doReturn(List.of(new SimpleGrantedAuthority("MEMBER"))).when(authentication).getAuthorities();
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // 실제 및 결과
        assertThrows(RuntimeException.class, () -> wishService.approveWish(new WishRequestDTO()));
    }

    @Test
    @DisplayName("Wish가 없어서 approveWish 실패 사례")
    void shouldNotApproveWishIfWishNotFound() {
        // 예상
        // ADMIN으로 사용자로 설정
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("1"); // 원하는 사용자 ID를 설정
        doReturn(List.of(new SimpleGrantedAuthority("ADMIN"))).when(authentication).getAuthorities();
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // 실제
        WishRequestDTO wishDTO = new WishRequestDTO();
        wishDTO.setId(10L);
        wishDTO.setBook(new BookDTO());
        wishDTO.getBook().setIsbn("1234567890");
        when(wishRepository.findById(wishDTO.getId())).thenReturn(Optional.empty());

        // 결과
        assertThrows(RuntimeException.class, () -> wishService.approveWish(wishDTO));
    }

    @Test
    @DisplayName("Wish가 이미 Book에 존재하여 실패 사례")
    void shouldNotApproveWishAlreadyExists() {
        // 예상
        // ADMIN으로 사용자로 설정
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("1"); // 원하는 사용자 ID를 설정
        doReturn(List.of(new SimpleGrantedAuthority("ADMIN"))).when(authentication).getAuthorities();
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        WishRequestDTO wishDTO = new WishRequestDTO();
        wishDTO.setId(10L);
        wishDTO.setBook(new BookDTO());
        wishDTO.getBook().setIsbn("1234567890");

        when(wishRepository.findById(eq(10L))).thenReturn(Optional.of(new Wish()));
        when(bookRepository.existsByIsbn(wishDTO.getBook().getIsbn())).thenReturn(true);

        // 결과
        assertThrows(RuntimeException.class, () -> wishService.approveWish(wishDTO));
        verify(wishRepository, times(1)).findById(anyLong());
    }


    @Test
    @DisplayName("approveWish 성공 사례")
    void shouldApproveWish() {
        // 예상
        // ADMIN으로 사용자로 설정
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("1"); // 원하는 사용자 ID를 설정
        doReturn(List.of(new SimpleGrantedAuthority("ADMIN"))).when(authentication).getAuthorities();
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        WishRequestDTO wishDTO = new WishRequestDTO();
        wishDTO.setId(10L);
        wishDTO.setBook(new BookDTO());
        wishDTO.getBook().setIsbn("1234567890");

        when(wishRepository.findById(eq(10L))).thenReturn(Optional.of(new Wish()));
        when(bookRepository.existsByIsbn(wishDTO.getBook().getIsbn())).thenReturn(false);

        // 실제
        String result = wishService.approveWish(wishDTO);
        verify(wishRepository, times(1)).findById(anyLong());

        // 결과
        assertEquals("Success", result);
    }
}
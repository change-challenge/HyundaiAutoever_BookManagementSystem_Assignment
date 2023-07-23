package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.service;

import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.BookRequestDTO;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Member;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.repository.MemberRepository;
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
    MemberRepository memberRepository;

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
    @DisplayName("getAllwishs 시,모든 Wish 가져오는 성공 사례")
    void shouldGetAllWishs() {
        // given
        Wish wish = new Wish();

        Member mockMember = new Member();
        mockMember.setEmail("test@email.com");
        wish.setMember(mockMember);

        when(wishRepository.findAll()).thenReturn(Arrays.asList(wish));

        // when
        List<WishRequestDTO> result = wishService.getAllWishs();

        // then
        assertEquals(1, result.size());
        verify(wishRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("getAllwishs 시, DB에 Wish가 없는 실패 사례")
    void shouldNotGetAllWishs() {
        // given
        when(wishRepository.findAll()).thenReturn(Collections.emptyList());

        // when
        List<WishRequestDTO> result = wishService.getAllWishs();

        // then
        assertEquals(0, result.size());
        verify(wishRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("getWishByEmail 시, Email 검색으로 다양한 Wish 가져오는 성공 사례")
    void shouldGetWishByEmail() {
        // given
        Wish wish1 = new Wish();
        wish1.setMember(new Member());
        Wish wish2 = new Wish();
        wish2.setMember(new Member());

        when(wishRepository.findByMemberEmail("test@email.com")).thenReturn(Arrays.asList(wish1, wish2));

        // when
        List<WishRequestDTO> result = wishService.getWishByEmail("test@email.com");

        // then
        assertEquals(2, result.size());
        verify(wishRepository, times(1)).findByMemberEmail("test@email.com");
    }

    @Test
    @DisplayName("getWishByEmail 시, Email 검색 후 하나 Wish 가져오는 성공 사례")
    void shouldGetOneWishByEmail() {
        // given
        Wish wish = new Wish();
        wish.setMember(new Member());

        when(wishRepository.findByMemberEmail("test@email.com")).thenReturn(Arrays.asList(wish));

        // when
        List<WishRequestDTO> result = wishService.getWishByEmail("test@email.com");

        // then
        assertEquals(1, result.size());
        verify(wishRepository, times(1)).findByMemberEmail("test@email.com");
    }

    @Test
    @DisplayName("getWishByEmail 시, 유효하지 않는 Email의 Wish가 없는 실패 사례")
    void shouldNotGetWishByInvalidEmail() {
        // given
        when(wishRepository.findByMemberEmail("invalid")).thenReturn(Collections.emptyList());

        // when
        List<WishRequestDTO> result = wishService.getWishByEmail("invalid");

        // then
        assertEquals(0, result.size());
        verify(wishRepository, times(1)).findByMemberEmail("invalid");
    }

    @Test
    @DisplayName("getWishByEmail 시, 이메일이 null이여서 꺼낼 Wish가 없는 실패 사례")
    void shouldNotGetWishByNullEmail() {
        // given
        when(wishRepository.findByMemberEmail(null)).thenReturn(Collections.emptyList());

        // when
        List<WishRequestDTO> result = wishService.getWishByEmail(null);

        // then
        assertEquals(0, result.size());
        verify(wishRepository, times(1)).findByMemberEmail(null);
    }

    @Test
    @DisplayName("saveWish 시, Book DB에 중복된 ISBN을 가진 책이 없을 때, saveWish 성공 사례")
    void shouldSaveWish() {
        // given
        Member member = new Member();
        member.setEmail("test@test.com");  // 이 부분을 추가합니다.
        WishRequestDTO form = new WishRequestDTO();
        form.setEmail(member.getEmail());
        form.setBook(new BookRequestDTO());
        form.getBook().setIsbn("1234567890");
        when(bookRepository.existsByIsbn("1234567890")).thenReturn(false);
        when(memberRepository.findByEmail("test@test.com")).thenReturn(Optional.of(member));
        Wish wish = form.toEntity(member);
        when(wishRepository.save(any(Wish.class))).thenReturn(wish);

        // when
        String result = wishService.saveWish(form);

        // then
        assertEquals("Success", result);
        verify(bookRepository, times(1)).existsByIsbn("1234567890");
        verify(wishRepository, times(1)).save(any(Wish.class));
    }

    @Test
    @DisplayName("saveWish 시, Book DB에 중복된 ISBN을 가진 책이 있을 때, 실패 사례")
    void shouldNotSaveWishIfBookExists() {
        // given
        WishRequestDTO form = new WishRequestDTO();
        form.setBook(new BookRequestDTO());
        form.getBook().setIsbn("1234567890");
        when(bookRepository.existsByIsbn("1234567890")).thenReturn(true);

        // when 및 then
        assertThrows(RuntimeException.class, () -> wishService.saveWish(form));
        verify(bookRepository, times(1)).existsByIsbn("1234567890");
    }

    @Test
    @DisplayName("RejectWish 시, Admin이 아니기 때문에 RejectWish 실패 사례")
    void shouldNotRejectWishIfNotAdmin() {
        // given
        // MEMBER로 사용자로 설정
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("1"); // 원하는 사용자 ID를 설정
        doReturn(List.of(new SimpleGrantedAuthority("MEMBER"))).when(authentication).getAuthorities();
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // when 및 then
        assertThrows(RuntimeException.class, () -> wishService.rejectedWish("1"));
    }

    @Test
    @DisplayName("RejectWish 시, Wish가 없어서 RejectWish 실패 사례")
    void shouldNotRejectWishIfWishNotFound() {
        // given
        // ADMIN으로 사용자로 설정
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("1"); // 원하는 사용자 ID를 설정
        doReturn(List.of(new SimpleGrantedAuthority("ADMIN"))).when(authentication).getAuthorities();
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(wishRepository.findById(anyLong())).thenReturn(Optional.empty());

        // when 및 then
        assertThrows(RuntimeException.class, () -> wishService.rejectedWish("1"));
        verify(wishRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("RejectWish 시, 성공 사례")
    void shouldRejectWish() {
        // given
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

        // when
        String result = wishService.rejectedWish("1");

        // then
        assertEquals("Success", result);
        verify(wishRepository, times(1)).findById(anyLong());
        verify(wishRepository, times(1)).save(any(Wish.class));
    }

    @Test
    @DisplayName("approveWish 시, Admin이 아니기 때문에 approveWish 불가 사례")
    void shouldNotApproveWishIfNotAdmin() {
        // given
        // MEMBER로 사용자로 설정
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("1"); // 원하는 사용자 ID를 설정
        doReturn(List.of(new SimpleGrantedAuthority("MEMBER"))).when(authentication).getAuthorities();
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // when 및 then
        assertThrows(RuntimeException.class, () -> wishService.approveWish(new WishRequestDTO()));
    }

    @Test
    @DisplayName("approveWish 시, Wish가 없어서 approveWish 실패 사례")
    void shouldNotApproveWishIfWishNotFound() {
        // given
        // ADMIN으로 사용자로 설정
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("1"); // 원하는 사용자 ID를 설정
        doReturn(List.of(new SimpleGrantedAuthority("ADMIN"))).when(authentication).getAuthorities();
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // when
        WishRequestDTO wishDTO = new WishRequestDTO();
        wishDTO.setId(10L);
        wishDTO.setBook(new BookRequestDTO());
        wishDTO.getBook().setIsbn("1234567890");
        when(wishRepository.findById(wishDTO.getId())).thenReturn(Optional.empty());

        // then
        assertThrows(RuntimeException.class, () -> wishService.approveWish(wishDTO));
    }

    @Test
    @DisplayName("approveWish 시, Wish가 이미 Book에 존재하여 실패 사례")
    void shouldNotApproveWishAlreadyExists() {
        // given
        // ADMIN으로 사용자로 설정
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("1"); // 원하는 사용자 ID를 설정
        doReturn(List.of(new SimpleGrantedAuthority("ADMIN"))).when(authentication).getAuthorities();
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        WishRequestDTO wishDTO = new WishRequestDTO();
        wishDTO.setId(10L);
        wishDTO.setBook(new BookRequestDTO());
        wishDTO.getBook().setIsbn("1234567890");

        when(wishRepository.findById(eq(10L))).thenReturn(Optional.of(new Wish()));
        when(bookRepository.existsByIsbn(wishDTO.getBook().getIsbn())).thenReturn(true);

        // then
        assertThrows(RuntimeException.class, () -> wishService.approveWish(wishDTO));
        verify(wishRepository, times(1)).findById(anyLong());
    }


    @Test
    @DisplayName("approveWish 시, 성공 사례")
    void shouldApproveWish() {
        // given
        // ADMIN으로 사용자로 설정
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("1"); // 원하는 사용자 ID를 설정
        doReturn(List.of(new SimpleGrantedAuthority("ADMIN"))).when(authentication).getAuthorities();
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        WishRequestDTO wishDTO = new WishRequestDTO();
        wishDTO.setId(10L);
        wishDTO.setBook(new BookRequestDTO());
        wishDTO.getBook().setIsbn("1234567890");

        when(wishRepository.findById(eq(10L))).thenReturn(Optional.of(new Wish()));
        when(bookRepository.existsByIsbn(wishDTO.getBook().getIsbn())).thenReturn(false);

        // when
        String result = wishService.approveWish(wishDTO);
        verify(wishRepository, times(1)).findById(anyLong());

        // then
        assertEquals("Success", result);
    }
}
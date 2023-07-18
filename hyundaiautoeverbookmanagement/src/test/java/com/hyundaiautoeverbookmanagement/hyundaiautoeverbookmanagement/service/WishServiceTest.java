package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.service;

import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.type.CategoryType;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("성공 테스트")
class WishServiceTest {

    @Mock
    WishRepository wishRepository;
    @Mock
    BookRepository bookRepository;
    @Mock
    CopyRepository copyRepository;
    @InjectMocks
    WishService wishService;

    @Test
    @DisplayName("모든 Wish 가져오기")
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
    @DisplayName("DB에 Wish가 없을 때")
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
    @DisplayName("Email로 검색해 다양한 Wish가 가져오기")
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
    @DisplayName("Email로 검색해 하나 Wish가 가져오기")
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
    @DisplayName("해석할 수 없는 Email의 Wish 경우")
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
    @DisplayName("이메일이 null인 경우")
    void shouldNotGetWishByNullEmail() {
        // 예상
        when(wishRepository.findByMemberEmail(null)).thenReturn(Collections.emptyList());

        // 실제
        List<WishRequestDTO> result = wishService.getWishByEmail(null);

        // 결과
        assertEquals(0, result.size());
        verify(wishRepository, times(1)).findByMemberEmail(null);
    }
    
}
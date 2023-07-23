package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.service;

import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.MemberRequestDTO;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.TokenDTO;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Member;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.RefreshToken;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.exception.UserException;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.jwt.TokenProvider;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.repository.MemberRepository;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.repository.RefreshTokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("AuthService에 대한 테스트")
class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private TokenProvider tokenProvider;

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Mock
    private PasswordEncoder passwordEncoder;


    @Mock
    private MemberRequestDTO memberRequestDto;

    @Test
    @DisplayName("signup 시, 이메일이 중복이면 가입 실패 사례")
    public void ShouldNotSignUpIfExistingEmail() {
        MemberRequestDTO memberRequestDto = new MemberRequestDTO();
        memberRequestDto.setEmail("existing@email.com");

        when(memberRepository.existsByEmail(anyString())).thenReturn(true);

        assertThrows(UserException.class, () -> {
            authService.signup(memberRequestDto);
        });
    }

    @Test
    @DisplayName("signup 시, 성공 사례")
    public void ShouldSignUp() {
        MemberRequestDTO memberRequestDto = new MemberRequestDTO();
        memberRequestDto.setEmail("existing@email.com");
        memberRequestDto.setPassword("somePassword");

        Member mockMember = mock(Member.class);
        when(mockMember.getEmail()).thenReturn("test@email.com");
        when(mockMember.getName()).thenReturn("testName");
        when(memberRepository.save(any(Member.class))).thenReturn(mockMember);

        when(memberRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        assertNotNull(authService.signup(memberRequestDto));
    }


    @Test
    @DisplayName("login 시, Authentication가 null인 실패 사례")
    public void ShouldNotLoginIfToAuthenticationReturnsNull() {
        // given
        when(memberRequestDto.toAuthentication()).thenReturn(null);

        // When & Then
        assertThrows(NullPointerException.class, () -> {
            authService.login(memberRequestDto);
        });
    }

    @Test
    @DisplayName("login 시, Authentication가 실패할 경우 실패 사례")
    public void ShouldNotLoginIfToAuthenticationFails() {
        // given
        when(memberRequestDto.toAuthentication()).thenReturn(new UsernamePasswordAuthenticationToken("someUser", "somePassword"));
        when(authenticationManagerBuilder.getObject()).thenReturn(authenticationManager);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        // When & Then
        assertThrows(BadCredentialsException.class, () -> {
            authService.login(memberRequestDto);
        });
    }

    @Test
    @DisplayName("login 시, TokenProvider가 null일 경우 실패 사례")
    public void ShouldNotLoginIfTokenProviderReturnsNull() {
        // given
        when(memberRequestDto.toAuthentication()).thenReturn(new UsernamePasswordAuthenticationToken("someUser", "somePassword"));
        when(authenticationManagerBuilder.getObject()).thenReturn(authenticationManager);
        when(tokenProvider.generateTokenDto(any())).thenReturn(null);

        // When & Then
        assertThrows(NullPointerException.class, () -> {
            authService.login(memberRequestDto);
        });
    }

    @Test
    @DisplayName("login 시, 로그인이 성공적으로 되는 성공 사례")
    public void ShouldLogin() {
        // given
        Authentication mockAuthentication = mock(Authentication.class);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken("someUser", "somePassword");

        when(memberRequestDto.toAuthentication()).thenReturn(token);
        when(authenticationManagerBuilder.getObject()).thenReturn(authenticationManager);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(mockAuthentication);
        when(tokenProvider.generateTokenDto(mockAuthentication)).thenReturn(new TokenDTO()); // 가정: TokenDTO의 기본 생성자를 통해 비어있는 객체 생성

        // When
        TokenDTO result = authService.login(memberRequestDto);

        // Then
        assertNotNull(result); // 반환된 토큰이 null이 아닌지 확인
    }

    @Test
    @DisplayName("logout 시, 로그아웃이 성공 사례")
    public void shouldLogout() {
        // given
        Long memberId = 1L;
        RefreshToken mockRefreshToken = mock(RefreshToken.class);

        when(refreshTokenRepository.findByKey(memberId.toString())).thenReturn(Optional.of(mockRefreshToken));

        // When
        authService.logout(memberId);

        // Then
        verify(refreshTokenRepository).deleteByKey(memberId.toString());
    }

    @Test
    @DisplayName("logout 시, 존재하지 않는 사용자로 로그아웃을 시도하는 실패 사례")
    public void shouldNotLogOutIfNonExistentUser() {
        // given
        Long memberId = 1L;

        when(refreshTokenRepository.findByKey(memberId.toString())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(UserException.class, () -> {
            authService.logout(memberId);
        });
    }
}
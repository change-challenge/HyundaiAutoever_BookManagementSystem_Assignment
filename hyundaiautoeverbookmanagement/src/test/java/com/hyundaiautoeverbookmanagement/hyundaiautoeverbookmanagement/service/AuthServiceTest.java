package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.service;

import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.MemberRequestDTO;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Member;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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


//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//        when(authenticationManagerBuilder.getObject()).thenReturn(authenticationManager);
//    }

    @Test
    @DisplayName("signup 시, 이메일이 중복이면 가입 실패 테스트")
    public void ShouldNotSignUpIfExistingEmail() {
        MemberRequestDTO memberRequestDto = new MemberRequestDTO();
        memberRequestDto.setEmail("existing@email.com");

        when(memberRepository.existsByEmail(anyString())).thenReturn(true);

        assertThrows(RuntimeException.class, () -> {
            authService.signup(memberRequestDto);
        });
    }

    @Test
    @DisplayName("signup 시, 성공 테스트")
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
    @DisplayName("login 시, Authentication가 null인 실패 테스트")
    public void ShouldNotLoginIfToAuthenticationReturnsNull() {
        // Given
        when(memberRequestDto.toAuthentication()).thenReturn(null);

        // When & Then
        assertThrows(NullPointerException.class, () -> {
            authService.login(memberRequestDto);
        });
    }

    @Test
    @DisplayName("login 시, Authentication가 실패할 경우 실패 테스트")
    public void ShouldNotLoginIfToAuthenticationFails() {
        // Given
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
    @DisplayName("login 시, TokenProvider가 null일 경우 실패 테스트")
    public void ShouldNotLoginIfTokenProviderReturnsNull() {
        // Given
        when(memberRequestDto.toAuthentication()).thenReturn(new UsernamePasswordAuthenticationToken("someUser", "somePassword"));
        when(authenticationManagerBuilder.getObject()).thenReturn(authenticationManager);
        when(tokenProvider.generateTokenDto(any())).thenReturn(null);

        // When & Then
        assertThrows(NullPointerException.class, () -> {
            authService.login(memberRequestDto);
        });
    }
}
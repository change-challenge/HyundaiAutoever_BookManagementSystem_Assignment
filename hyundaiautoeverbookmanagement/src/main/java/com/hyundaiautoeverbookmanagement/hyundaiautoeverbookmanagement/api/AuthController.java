package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.api;


import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.TokenDto;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.TokenRequestDto;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.MemberRequestDto;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.MemberResponseDto;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<MemberResponseDto> signup(@RequestBody MemberRequestDto memberRequestDto) {
        return ResponseEntity.ok(authService.signup(memberRequestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody MemberRequestDto memberRequestDto) {
        log.info("hey!!!!!!!!!!!" + memberRequestDto.toString());
        return ResponseEntity.ok(authService.login(memberRequestDto));
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        return ResponseEntity.ok(authService.reissue(tokenRequestDto));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody TokenRequestDto tokenRequestDto) {
//        // 세션 무효화
//        HttpSession session = request.getSession(false);
//        if (session != null) {
//            session.invalidate();
//        }
//
//        // 쿠키 삭제
//        Cookie[] cookies = request.getCookies();
//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//                cookie.setMaxAge(0);
//                cookie.setValue(null);
//                cookie.setPath("/");
//                response.addCookie(cookie);
//            }
//        }

        return ResponseEntity.ok("로그아웃 성공");
    }

}

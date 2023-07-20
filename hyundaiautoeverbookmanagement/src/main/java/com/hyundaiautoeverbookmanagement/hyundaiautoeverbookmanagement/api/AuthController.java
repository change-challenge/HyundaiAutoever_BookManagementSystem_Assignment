package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.api;


import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.TokenDTO;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.TokenRequestDTO;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.MemberRequestDTO;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.MemberResponseDTO;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.util.SecurityUtil.getCurrentMemberId;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<MemberResponseDTO> signup(@RequestBody MemberRequestDTO memberRequestDto) {
        return ResponseEntity.ok(authService.signup(memberRequestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody MemberRequestDTO memberRequestDto) {
        return ResponseEntity.ok(authService.login(memberRequestDto));
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenDTO> reissue(@RequestBody TokenRequestDTO tokenRequestDto) {
        return ResponseEntity.ok(authService.reissue(tokenRequestDto));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        authService.logout(getCurrentMemberId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/exist")
    public ResponseEntity<?> existEmail(@RequestParam String email) {
        return (authService.existEmail(email) ? ResponseEntity.badRequest().build() : ResponseEntity.ok().build());
    }
}

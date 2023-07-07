package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.api;

import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.MemberDTO;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.MemberResponseDto;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Member;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.repository.MemberRepository;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.service.UserService;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@Slf4j
public class UserApiController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<MemberResponseDto> findUserInfoById() {
        return ResponseEntity.ok(userService.findUserInfoById(SecurityUtil.getCurrentUserId()));
    }

//
    @Autowired // 스프링 부트가 미리 생성해놓은 객체를 가져다가 자동 연결!
    private MemberRepository memberRepository;

//    private final UserRepository userRepository;
//
//    @Autowired
//    public UserApiController(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }

    @PostMapping("/api/user/create")
    public String createUser(@RequestBody MemberDTO form) {
        log.info(form.toString());
        // 1. Dto를 변환! Entity!
        Member user = form.toEntity();

        // 2. Repository에게 Entity를 DB안에 저장하게 함!
        Member saved = memberRepository.save(user);
        log.info(saved.toString());
        return "success";
    }

}

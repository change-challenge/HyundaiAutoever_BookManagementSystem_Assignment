package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.api;

import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.MemberResponseDTO;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.service.MemberService;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
@Slf4j
public class MemberApiController {

    private final MemberService memberService;

    @GetMapping("/me")
    public ResponseEntity<MemberResponseDTO> findMemberInfoById() {
        return ResponseEntity.ok(memberService.findMemberInfoById(SecurityUtil.getCurrentMemberId()));
    }
}

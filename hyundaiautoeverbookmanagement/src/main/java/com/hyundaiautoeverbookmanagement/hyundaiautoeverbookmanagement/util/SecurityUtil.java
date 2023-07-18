package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.util;

import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.MemberType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Slf4j
public class SecurityUtil {

    private SecurityUtil() { }

    // SecurityContext 에 유저 정보가 저장되는 시점
    // Request 가 들어올 때 JwtFilter 의 doFilter 에서 저장
    public static Long getCurrentMemberId() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getName() == null) {
            throw  new RuntimeException("Security Context 에 인증 정보가 없습니다.");
        }
        return Long.parseLong(authentication.getName());
    }

    public static MemberType getCurrentMemberType() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getName() == null) {
            throw new RuntimeException("Security Context 에 인증 정보가 없습니다.");
        }
        String authority = authentication.getAuthorities().iterator().next().getAuthority();
        if (authority.equals("ROLE_ANONYMOUS")) {
            throw new RuntimeException("인증되지 않은 사용자입니다.");
        }
        return MemberType.valueOf(authority);
    }

    public static void checkAdminAuthority() {
        if (getCurrentMemberType() != MemberType.ADMIN) {
            throw new RuntimeException("당신은 Admin이 아닙니다.");
        }
    }

}

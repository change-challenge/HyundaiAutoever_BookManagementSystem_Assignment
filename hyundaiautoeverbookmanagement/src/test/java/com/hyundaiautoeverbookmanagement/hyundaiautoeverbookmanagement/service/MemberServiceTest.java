package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.service;

import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.AdminMemberDTO;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.type.MemberType;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Member;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.repository.*;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.MemberResponseDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;


@ExtendWith(MockitoExtension.class)
@DisplayName("MemberhService에 대한 테스트")
class MemberServiceTest {

    @Mock
    WishRepository wishRepository;
    @Mock
    BookRepository bookRepository;
    @Mock
    RentRepository rentRepository;
    @Mock
    MemberRepository memberRepository;

    @InjectMocks
    MemberService memberService;

    @AfterEach
    @DisplayName("SecurityContextHolder 컨텍스트 클리어")
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("모든 회원 정보를 가져올 ")
    public void shouldGetAllMembers() {
        // 예상
        List<Member> members = new ArrayList<>();

        for (long i = 1; i <= 6; i++) {
            Member member = new Member();
            member.setId(i);
            members.add(member);
        }
        when(memberRepository.findAll()).thenReturn(members);
        when(rentRepository.countByMemberIdAndReturnedDateIsNull(anyLong())).thenReturn(0);

        // 실제
        List<AdminMemberDTO> result = memberService.getAllMembers();

        // 결과
        assertEquals(members.size(), result.size());
        verify(memberRepository, times(1)).findAll();
        verify(rentRepository, times(members.size())).countByMemberIdAndReturnedDateIsNull(anyLong());
    }

    @Test
    @DisplayName("한 명의 회원 정보를 가져오는 테스트")
    public void shouldGetOneMember() {
        // 예상
        Member member1 = new Member();
        member1.setId(1L);
        List<Member> members = Arrays.asList(member1);
        when(memberRepository.findAll()).thenReturn(members);
        when(rentRepository.countByMemberIdAndReturnedDateIsNull(anyLong())).thenReturn(0);

        // 실제
        List<AdminMemberDTO> result = memberService.getAllMembers();

        // 결과
        assertEquals(members.size(), result.size());
        verify(memberRepository, times(1)).findAll();
        verify(rentRepository, times(members.size())).countByMemberIdAndReturnedDateIsNull(anyLong());
    }

    @Test
    @DisplayName("회원 정보가 하나도 없을 때")
    public void shouldNotGetAllMembers() {
        // 예상
        when(memberRepository.findAll()).thenReturn(Collections.emptyList());

        // 실제
        List<AdminMemberDTO> result = memberService.getAllMembers();

        // 결과
        assertEquals(0, result.size());
    }

    @Test
    @DisplayName("존재하는 회원 ID로 정보를 가져오는 테스트")
    public void shouldFindMemberInfoByIdExists() {
        // 예상
        Member member = new Member();
        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(member));
        // 실제
        MemberResponseDTO result = memberService.findMemberInfoById(1L);
        // 결과
        assertNotNull(result);
        verify(memberRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("존재하지 않는 회원 ID로 정보를 가져오는 테스트")
    public void testFindMemberInfoByIdNotExists() {
        // 예상
        when(memberRepository.findById(anyLong())).thenReturn(Optional.empty());
        // 실제 & 결과
        assertThrows(RuntimeException.class, () -> memberService.findMemberInfoById(1L));
        verify(memberRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("신청자가 ADMIN이 아닌 경우")
    public void ShouldNotChangeMemberTypeIfNotAdmin() {
        // 예상
        // MEMBER로 사용자로 설정
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("1"); // 원하는 사용자 ID를 설정
        doReturn(List.of(new SimpleGrantedAuthority("MEMBER"))).when(authentication).getAuthorities();
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // 실제 및 결과
        assertThrows(RuntimeException.class, () -> memberService.changeMemberType("a@a.com", "ab@a.com"));
    }

    @Test
    @DisplayName("신청자가 본인의 회원 유형을 바꾸려 하는 경우")
    public void ShouldNotChangeMemberTypeIfItsMine() {
        // ADMIN로 사용자로 설정
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("1"); // 원하는 사용자 ID를 설정
        doReturn(List.of(new SimpleGrantedAuthority("ADMIN"))).when(authentication).getAuthorities();
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        String userEmail = "user1@email.com";

        assertThrows(RuntimeException.class, () -> memberService.changeMemberType(userEmail, userEmail), "본인 스스로를 바꿀 수 없습니다.");
    }

    @Test
    @DisplayName("제공된 이메일로 회원 정보를 찾을 수 없는 경우")
    public void ShouldNotChangeMemberTypeIfNotFound() {
        // ADMIN 사용자로 설정
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("1"); // 원하는 사용자 ID를 설정
        doReturn(List.of(new SimpleGrantedAuthority("ADMIN"))).when(authentication).getAuthorities();
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        String userEmail = "nonexistent@email.com";
        when(memberRepository.findByEmail(userEmail)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> memberService.changeMemberType(userEmail, "admin@email.com"), "유저 정보가 없습니다.");
    }

    @Test
    @DisplayName("정상적인 회원 유형 변경")
    @Transactional
    public void ShouldChangeMemberType() {
        // ADMIN 사용자로 설정
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("1"); // 원하는 사용자 ID를 설정
        doReturn(List.of(new SimpleGrantedAuthority("ADMIN"))).when(authentication).getAuthorities();
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        String userEmail = "user1@email.com";
        String adminEmail = "admin@email.com";

        Member member = new Member();
        member.setEmail(userEmail);
        member.setMemberType(MemberType.MEMBER);

        when(memberRepository.findByEmail(userEmail)).thenReturn(Optional.of(member));
        String result = memberService.changeMemberType(userEmail, adminEmail);

        assertEquals("Success", result);
        assertEquals(MemberType.ADMIN, member.getMemberType());
    }
}




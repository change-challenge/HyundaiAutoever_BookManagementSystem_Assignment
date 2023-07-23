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
    @DisplayName("getAllMembers 시, 모든 회원 정보를 가져오는 성공 사례")
    public void shouldGetAllMembers() {
        // given
        List<Member> members = new ArrayList<>();

        for (long i = 1; i <= 6; i++) {
            Member member = new Member();
            member.setId(i);
            members.add(member);
        }
        when(memberRepository.findAll()).thenReturn(members);
        when(rentRepository.countByMemberIdAndReturnedDateIsNull(anyLong())).thenReturn(0);

        // when
        List<AdminMemberDTO> result = memberService.getAllMembers();

        // then
        assertEquals(members.size(), result.size());
        verify(memberRepository, times(1)).findAll();
        verify(rentRepository, times(members.size())).countByMemberIdAndReturnedDateIsNull(anyLong());
    }

    @Test
    @DisplayName("getAllMembers 시, 한 명의 회원 정보를 가져오는 성공 사례")
    public void shouldGetOneMember() {
        // given
        Member member1 = new Member();
        member1.setId(1L);
        List<Member> members = Arrays.asList(member1);
        when(memberRepository.findAll()).thenReturn(members);
        when(rentRepository.countByMemberIdAndReturnedDateIsNull(anyLong())).thenReturn(0);

        // when
        List<AdminMemberDTO> result = memberService.getAllMembers();

        // then
        assertEquals(members.size(), result.size());
        verify(memberRepository, times(1)).findAll();
        verify(rentRepository, times(members.size())).countByMemberIdAndReturnedDateIsNull(anyLong());
    }

    @Test
    @DisplayName("getAllMembers 시, 회원 정보가 하나도 없는 실패 사례")
    public void shouldNotGetAllMembers() {
        // given
        when(memberRepository.findAll()).thenReturn(Collections.emptyList());

        // when
        List<AdminMemberDTO> result = memberService.getAllMembers();

        // then
        assertEquals(0, result.size());
    }

    @Test
    @DisplayName("FindMemberInfoById 시, 존재하는 회원 ID로 정보를 가져오는 성공 사례")
    public void shouldFindMemberInfoByIdExists() {
        // given
        Member member = new Member();
        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(member));
        // when
        MemberResponseDTO result = memberService.findMemberInfoById(1L);
        // then
        assertNotNull(result);
        verify(memberRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("FindMemberInfoById 시, 존재하지 않는 회원 ID로 정보를 가져오는 실패 사례")
    public void shouldNotFindMemberInfoByIdNotExists() {
        // given
        when(memberRepository.findById(anyLong())).thenReturn(Optional.empty());
        // when & then
        assertThrows(RuntimeException.class, () -> memberService.findMemberInfoById(1L));
        verify(memberRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("changeMemberType 시, ADMIN이 아닌 실패 사례")
    public void ShouldNotChangeMemberTypeIfNotAdmin() {
        // given
        // MEMBER로 사용자로 설정
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("1"); // 원하는 사용자 ID를 설정
        doReturn(List.of(new SimpleGrantedAuthority("MEMBER"))).when(authentication).getAuthorities();
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // when 및 then
        assertThrows(RuntimeException.class, () -> memberService.changeMemberType("a@a.com", "ab@a.com"));
    }

    @Test
    @DisplayName("changeMemberType 시, 본인의 회원 권한 변경 실패 사례")
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
    @DisplayName("changeMemberType 시, 이메일로 회원 정보를 찾을 수 없는 실패 사례")
    public void ShouldNotChangeMemberTypeIfNotFound() {
        // ADMIN 사용자로 설정
        // given
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("1"); // 원하는 사용자 ID를 설정
        doReturn(List.of(new SimpleGrantedAuthority("ADMIN"))).when(authentication).getAuthorities();
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // when
        String userEmail = "nonexistent@email.com";
        when(memberRepository.findByEmail(userEmail)).thenReturn(Optional.empty());

        // then
        assertThrows(RuntimeException.class, () -> memberService.changeMemberType(userEmail, "admin@email.com"), "유저 정보가 없습니다.");
    }

    @Test
    @DisplayName("changeMemberType 시, 회원 권한 변경 성공 사례")
    @Transactional
    public void ShouldChangeMemberType() {
        // ADMIN 사용자로 설정
        // given
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

        // when
        when(memberRepository.findByEmail(userEmail)).thenReturn(Optional.of(member));
        String result = memberService.changeMemberType(userEmail, adminEmail);

        // then
        assertEquals("Success", result);
        assertEquals(MemberType.ADMIN, member.getMemberType());
    }
}




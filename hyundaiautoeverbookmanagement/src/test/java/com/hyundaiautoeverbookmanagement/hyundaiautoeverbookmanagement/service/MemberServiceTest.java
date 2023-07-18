package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.service;

import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.AdminMemberDTO;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.WishRequestDTO;
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
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
    @DisplayName("모든 회원 정보를 가져오는 테스트")
    public void shouldGetAllMembers() {
        // 예상
        Member member1 = new Member();
        member1.setId(1L);
        Member member2 = new Member();
        member2.setId(2L);
        List<Member> members = Arrays.asList(member1, member2);
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
    

}
package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.service;


import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.MemberDTO;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.MemberResponseDTO;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Member;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;

    public List<MemberDTO> getAllMembers() {
        List<Member> members = memberRepository.findAll();
        List<MemberDTO> memberDTOS = new ArrayList<>();

        for (Member member: members) {
            MemberDTO memberDTO = new MemberDTO();

            memberDTO.setId(member.getId());
            memberDTO.setEmail(member.getEmail());
            memberDTO.setName(member.getName());
            memberDTO.setUserType(member.getUsertype());
            memberDTO.setRegistDate(member.getRegist_date());
            memberDTOS.add(memberDTO);
        }
        return memberDTOS;
    }

    public MemberResponseDTO findMemberInfoById(Long userId) {
        return memberRepository.findById(userId)
                .map(MemberResponseDTO::of)
                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다."));
    }

    public MemberResponseDTO findMemberInfoByEmail(String email) {
        return memberRepository.findByEmail(email)
                .map(MemberResponseDTO::of)
                .orElseThrow(() -> new RuntimeException("유저 정보가 없습니다."));
    }
}

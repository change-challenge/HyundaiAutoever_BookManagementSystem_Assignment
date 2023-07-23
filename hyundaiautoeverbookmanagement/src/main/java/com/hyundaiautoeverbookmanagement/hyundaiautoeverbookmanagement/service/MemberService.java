package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.service;


import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.AdminMemberDTO;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.MemberResponseDTO;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.type.MemberType;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Member;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Rent;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.exception.UserException;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.repository.MemberRepository;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.repository.RentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.util.SecurityUtil.checkAdminAuthority;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final RentRepository rentRepository;

    // ID로 MEMBER DATA 찾기
    public MemberResponseDTO findMemberInfoById(Long memberId) {
        return memberRepository.findById(memberId)
                .map(MemberResponseDTO::of)
                .orElseThrow(() -> new UserException("findMemberInfoById : 로그인 유저 정보가 없습니다."));
    }

    // -----------------------------
    // |        Admin 권한관련        |
    // -----------------------------
    public List<AdminMemberDTO> getAllMembers() {
        List<Member> members = memberRepository.findAll();
        List<AdminMemberDTO> adminMemberDTOS = new ArrayList<>();

        for (Member member: members) {
            AdminMemberDTO adminMemberDTO = new AdminMemberDTO();
            adminMemberDTO.setId(member.getId());
            adminMemberDTO.setEmail(member.getEmail());
            adminMemberDTO.setName(member.getName());
            adminMemberDTO.setMemberType(member.getMemberType());
            adminMemberDTO.setRegistDate(member.getRegistDate());
            adminMemberDTO.setRentCount(rentRepository.countByMemberIdAndReturnedDateIsNull(member.getId()));
            adminMemberDTO.setLateDay(calculateLateDays(member.getId()));
            adminMemberDTOS.add(adminMemberDTO);
        }
        return adminMemberDTOS;
    }

    // Member 권한 바꾸기
    @Transactional
    public String changeMemberType(String email, String myEmail) {
        // 1. 신청자가 admin 인 지 확인
        checkAdminAuthority();

        // 2. 본인을 바꾸려고 하는 지 확인
        if (email.equals(myEmail)) {
            throw new UserException("changeMemberType : 본인 스스로를 바꿀 수 없습니다.");
        }

        // 3. email 로 member Data 가져오기
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UserException("changeMemberType : 해당 유저 정보가 없습니다."));

        // 4. Meber면 Admin으로, Admin이면 Member로 변경
        member.setMemberType(member.getMemberType() == MemberType.MEMBER ? MemberType.ADMIN : MemberType.MEMBER);
        memberRepository.save(member);
        return "Success";
    }

    // -----------------------------
    // |        내장 함수 관련         |
    // -----------------------------

    // 연체중인 기간 정하는 함수
    private int calculateLateDays(Long memberId) {
        // 1. 해당 유저의 모든 RENT 정보 가져오기
        List<Rent> rents = rentRepository.findByMemberIdAndReturnedDateIsNull(memberId);

        // 2. 오늘 날짜 가져오기
        int totalDays = 0;
        LocalDate currentDate = LocalDate.now();

        // 3. 모든 RENT에서 대출 시작날과 오늘을 뺀 후, 7일 넘으면 연체날에 추가
        for(Rent rent : rents) {
            long diff = ChronoUnit.DAYS.between(rent.getStartDate(), currentDate);
            // 3-1. 연장 했을 시, 14일로 계산
            if (rent.getIsExtendable() && diff > 14) {
                totalDays += (diff - 14);
            }
            else if(diff > 7) {
                totalDays += (diff - 7);
            }
        }
        return totalDays;
    }
}

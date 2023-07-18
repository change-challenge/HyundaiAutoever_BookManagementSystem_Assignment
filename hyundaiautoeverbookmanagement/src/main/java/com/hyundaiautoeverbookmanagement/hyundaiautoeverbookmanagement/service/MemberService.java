package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.service;


import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.MemberAdminResponseDTO;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.MemberResponseDTO;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.MemberType;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Member;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Rent;
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
import static com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.util.SecurityUtil.getCurrentMemberType;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final RentRepository rentRepository;

    public List<MemberAdminResponseDTO> getAllMembers() {
        List<Member> members = memberRepository.findAll();
        List<MemberAdminResponseDTO> memberAdminResponseDTOS = new ArrayList<>();

        for (Member member: members) {
            MemberAdminResponseDTO memberAdminResponseDTO = new MemberAdminResponseDTO();
            memberAdminResponseDTO.setId(member.getId());
            memberAdminResponseDTO.setEmail(member.getEmail());
            memberAdminResponseDTO.setName(member.getName());
            memberAdminResponseDTO.setMemberType(member.getMemberType());
            memberAdminResponseDTO.setRegistDate(member.getRegistDate());
            memberAdminResponseDTO.setRentCount(rentRepository.countByMemberIdAndReturnedDateIsNull(member.getId()));
            memberAdminResponseDTO.setLateDay(calculateLateDays(member.getId()));
            memberAdminResponseDTOS.add(memberAdminResponseDTO);
        }
        return memberAdminResponseDTOS;
    }

    public MemberResponseDTO findMemberInfoById(Long memberId) {
        return memberRepository.findById(memberId)
                .map(MemberResponseDTO::of)
                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다."));
    }

    @Transactional
    public String changeMemberType(String email, String myEmail) {
        // 1. 신청자가 admin인 지 확인
        if (getCurrentMemberType() != MemberType.ADMIN) {
            throw new RuntimeException("당신은 Admin이 아닙니다.");
        }

        // 2. 본인을 바꾸려고 하는 지 확인
        if (email.equals(myEmail)) {
            throw new RuntimeException("본인 스스로를 바꿀 수 없습니다.");
        }

        // 3. email로 member Data 가져오기
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("유저 정보가 없습니다."));

        member.setMemberType(member.getMemberType() == MemberType.MEMBER ? MemberType.ADMIN : MemberType.MEMBER);
        memberRepository.save(member);
        return "Success";
    }

    private int calculateLateDays(Long memberId) {
        // 해당 유저의 모든 대출 정보 가져오기
        List<Rent> rents = rentRepository.findByMemberIdAndReturnedDateIsNull(memberId);

        int totalDays = 0;
        LocalDate currentDate = LocalDate.now();

        for(Rent rent : rents) {
            long diff = ChronoUnit.DAYS.between(rent.getStartDate(), currentDate);
            if(diff > 7) {
                totalDays += (diff - 7);
            }
        }
        return totalDays;
    }
}

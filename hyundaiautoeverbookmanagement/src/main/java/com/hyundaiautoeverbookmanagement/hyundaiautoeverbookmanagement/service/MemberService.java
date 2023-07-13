package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.service;


import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.MemberAdminResponseDTO;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto.MemberResponseDTO;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Member;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Rent;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.repository.MemberRepository;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.repository.RentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final RentRepository rentRepository;

    private int calculateLateDays(Long userId) {
        // 해당 유저의 모든 대출 정보 가져오기
        List<Rent> rents = rentRepository.findByUserIdAndRentReturnedDateIsNull(userId);

        int totalDays = 0;
        LocalDate currentDate = LocalDate.now();

        for(Rent rent : rents) {
            long diff = ChronoUnit.DAYS.between(rent.getRentStartDate(), currentDate);

            if(diff > 7) {
                totalDays += (diff - 7);
            }
        }

        return totalDays;
    }


    public List<MemberAdminResponseDTO> getAllMembers() {
        List<Member> members = memberRepository.findAll();
        List<MemberAdminResponseDTO> memberAdminResponseDTOS = new ArrayList<>();

        for (Member member: members) {
            MemberAdminResponseDTO memberAdminResponseDTO = new MemberAdminResponseDTO();

            memberAdminResponseDTO.setId(member.getId());
            memberAdminResponseDTO.setEmail(member.getEmail());
            memberAdminResponseDTO.setName(member.getName());
            memberAdminResponseDTO.setUserType(member.getUsertype());
            memberAdminResponseDTO.setRegistDate(member.getRegist_date());
            memberAdminResponseDTO.setRentCount(rentRepository.countByUserIdAndRentReturnedDateIsNull(member.getId()));
            memberAdminResponseDTO.setLateDay(calculateLateDays(member.getId()));
            memberAdminResponseDTOS.add(memberAdminResponseDTO);
        }
        return memberAdminResponseDTOS;
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

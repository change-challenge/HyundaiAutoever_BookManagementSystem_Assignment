package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.repository;

import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Rent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RentRepository extends JpaRepository<Rent, Long> {

    List<Rent> findAll();

    @Query("SELECT COUNT(r) FROM Rent r WHERE r.member.id = :memberId AND r.returnedDate IS NULL")
    int countByMemberIdAndReturnedDateIsNull(@Param("memberId") Long memberId);
    // MyPage 대출이력 가져오기

    List<Rent> findByMemberId(Long memberId);
    // MyPage 대출현황 가져오기

    List<Rent> findByMemberIdAndReturnedDateIsNull(Long memberId);
    // 현재 대출중인 특정한 책 가져오기

    Optional<Rent> findByMemberIdAndCopyIdAndReturnedDateIsNull(Long memberId, Long copyId);

    List<Rent> findByCopyId(Long copyId);

    // 대출중인 책 중복처리 막기 위해
    // 먼저 현재 빌리고 있는 책 제목 리스트 가져오기
    @Query("SELECT r.copy.book.title FROM Rent r WHERE r.member.id = :memberId AND r.returnedDate IS NULL")
    List<String> findRentedBookTitlesByMemberId(@Param("memberId") Long memberId);

    // 반납을 한 책을 또 빌리려고 할 때
    @Query("SELECT r FROM Rent r WHERE r.copy.id = :copyId AND r.returnedDate IS NULL ORDER BY r.endDate DESC")
    Rent findFirstByCopyIdAndReturnedDateIsNullOrderByEndDateDesc(@Param("copyId") Long copyId);
}

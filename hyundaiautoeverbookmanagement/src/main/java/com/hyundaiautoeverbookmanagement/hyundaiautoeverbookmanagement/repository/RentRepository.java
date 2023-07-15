package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.repository;

import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Copy;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Rent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RentRepository extends JpaRepository<Rent, Long> {

    @Query("SELECT COUNT(r) FROM Rent r WHERE r.user.id = :userId AND r.rentReturnedDate IS NULL")
    int countByUserIdAndRentReturnedDateIsNull(@Param("userId") Long userId);

    // MyPage 대출이력 가져오기
    List<Rent> findByUserId(Long userId);

    // MyPage 대출현황 가져오기
    List<Rent> findByUserIdAndRentReturnedDateIsNull(Long userId);

    // 현재 대출중인 특정한 책 가져오
    Optional<Rent> findByUserIdAndCopyIdAndRentReturnedDateIsNull(Long userId, Long copyId);

    // 대출중인 책 중복처리 막기 위해
    Optional<Rent> findByUserIdAndCopy_Book_TitleAndRentReturnedDateIsNull(Long userId, String bookTitle);



    List<Rent> findAll();

    @Query("SELECT r FROM Rent r WHERE r.copy = :copy ORDER BY r.rentEndDate DESC")
    Rent findTopByCopyOrderByRentEndDateDesc(@Param("copy") Copy copy);


    @Query("SELECT r FROM Rent r WHERE r.copy.id = :copyId ORDER BY r.rentEndDate DESC")
    Rent findFirstByCopyIdOrderByRentEndDateDesc(@Param("copyId") Long copyId);



}

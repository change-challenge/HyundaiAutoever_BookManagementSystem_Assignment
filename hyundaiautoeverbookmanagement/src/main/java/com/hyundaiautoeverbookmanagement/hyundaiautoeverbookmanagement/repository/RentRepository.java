package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.repository;

import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Rent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentRepository extends JpaRepository<Rent, Long> {

    @Query("SELECT COUNT(r) FROM Rent r WHERE r.user.id = :userId AND r.rentReturnedDate IS NULL")
    int countByUserIdAndRentReturnedDateIsNull(@Param("userId") Long userId);

    List<Rent> findByUserIdAndRentReturnedDateIsNull(Long userId);

    List<Rent> findAll();
}

package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.repository;

import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Book;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Copy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface CopyRepository extends JpaRepository<Copy, Long> {
    List<Copy> findByBook(Book book);
    List<Copy> findByBookId(Long bookId);
    Optional<Copy> findById(Long id);
    int countByBook(Book book);
    @Query("SELECT c.book FROM Copy c WHERE c.id = :copyId")
    Book findBookByCopyId(@Param("copyId") Long copyId);
}

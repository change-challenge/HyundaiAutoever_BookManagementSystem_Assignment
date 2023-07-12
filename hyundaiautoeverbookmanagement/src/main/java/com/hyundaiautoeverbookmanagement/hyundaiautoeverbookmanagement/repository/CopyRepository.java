package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.repository;

import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Book;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Copy;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface CopyRepository extends JpaRepository<Copy, Long> {
    List<Copy> findByBook(Book book);
    Optional<Copy> findById(Long id);
}

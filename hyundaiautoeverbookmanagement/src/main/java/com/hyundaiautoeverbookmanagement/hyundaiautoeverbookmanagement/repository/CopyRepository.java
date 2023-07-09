package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.repository;

import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Book;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Copy;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface CopyRepository extends JpaRepository<Copy, Long> {
    List<Copy> findByBook(Book book);
}

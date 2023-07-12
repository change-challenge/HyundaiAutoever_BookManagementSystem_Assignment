package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.repository;

import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Book;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Wish;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findById(Long bookId);
    List<Book> findByTitleContaining(String title);
    List<Book> findAll();

}

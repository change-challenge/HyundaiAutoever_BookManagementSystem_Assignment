package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.repository;

import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findById(Long bookId);
    @Query("SELECT b.title FROM Book b WHERE b.id = :id")
    String findTitleById(@Param("id") Long id);

    List<Book> findByTitleContaining(String title);

    List<Book> findAll();

    boolean existsByIsbn(String isbn);
}
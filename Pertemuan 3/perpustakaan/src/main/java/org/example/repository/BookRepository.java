package org.example.repository;

import org.example.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<BookEntity, Long> {
    @Query("SELECT b FROM BookEntity b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR LOWER(b.author) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<BookEntity> searchBooks(@Param("searchTerm") String searchTerm);

    @Query("SELECT b FROM BookEntity b WHERE LOWER(b.author) LIKE LOWER(CONCAT('%', :author, '%')) AND LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    List<BookEntity> searchBooksByAuthorAndTitle(@Param("author") String author, @Param("title") String title);
}
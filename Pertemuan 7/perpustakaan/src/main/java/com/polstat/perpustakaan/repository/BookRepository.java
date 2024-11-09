package com.polstat.perpustakaan.repository;

import com.polstat.perpustakaan.entity.Book;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@SecurityRequirement(name = "bearerAuth")
public interface BookRepository extends JpaRepository<Book, Long> {

    @Operation(summary = "Search books by title or author")
    @ApiResponse(responseCode = "200", description = "Found the books",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Book.class)))
    @RestResource(path = "search-books")
    @Query("SELECT b FROM Book b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR LOWER(b.author) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Book> searchBooks(@Parameter(description = "Term to search in title or author") @Param("searchTerm") String searchTerm);

    @Operation(summary = "Search books by author and title")
    @ApiResponse(responseCode = "200", description = "Found the books",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Book.class)))
    @RestResource(path = "search-by-author-and-title")
    @Query("SELECT b FROM Book b WHERE LOWER(b.author) LIKE LOWER(CONCAT('%', :author, '%')) AND LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    List<Book> searchBooksByAuthorAndTitle(
            @Parameter(description = "Author name to search") @Param("author") String author,
            @Parameter(description = "Book title to search") @Param("title") String title
    );
}
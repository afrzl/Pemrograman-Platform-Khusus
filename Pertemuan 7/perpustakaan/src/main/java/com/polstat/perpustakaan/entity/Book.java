package com.polstat.perpustakaan.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Schema(description = "Book entity representing a book in the library")
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the book")
    private Long id;

    @Column(nullable = false)
    @Schema(description = "Title of the book", example = "Belajar Web Service")
    private String title;

    @Column(nullable = false)
    @Schema(description = "Author of the book", example = "Budi")
    private String author;

    @Column(nullable = true)
    @Schema(description = "Description of the book", example = "Ini adalah buku web service terbaik")
    private String description;
}

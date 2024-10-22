package com.polstat.perpustakaan.service;

import com.polstat.perpustakaan.dto.BookDto;
import java.util.List;

public interface BookService {

    public BookDto createBook(BookDto bookDto);

    public BookDto updateBook(BookDto bookDto);

    public void deleteBook(BookDto bookDto);

    public List<BookDto> getBooks();

    public BookDto getBook(Long id);

    public List<BookDto> searchBooks(String searchTerm);

    public List<BookDto> searchBooks(String title, String author);
}

package com.polstat.perpustakaan.service;

import com.polstat.perpustakaan.dto.BookDto;
import java.util.List;

public interface BookService {

    void createBook(BookDto bookDto);

    public List<BookDto> getBooks();

    public List<BookDto> searchBooks(String searchTerm);

    public List<BookDto> searchBooks(String title, String author);
}

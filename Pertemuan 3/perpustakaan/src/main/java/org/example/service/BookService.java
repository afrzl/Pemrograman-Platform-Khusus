package org.example.service;

import com.example.soap.gen.Book;

import java.util.List;

public interface BookService {

    void createBook(Book book);

    public List<Book> getBooks();

    public List<Book> searchBooks(String searchTerm);

    public List<Book> searchBooks(String title, String author);
}

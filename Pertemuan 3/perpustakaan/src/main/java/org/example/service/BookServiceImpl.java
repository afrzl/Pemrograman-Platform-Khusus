package org.example.service;

import com.example.soap.gen.Book;
import org.example.entity.BookEntity;
import org.example.mapper.BookMapper;
import org.example.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;

    @Override
    public void createBook(Book book) {
        BookEntity entity = BookMapper.mapToEntity(book);
        bookRepository.save(entity);
    }

    @Override
    public List<Book> getBooks() {
        List<BookEntity> entities = bookRepository.findAll();
        return entities.stream()
                .map(BookMapper::mapToBook)
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> searchBooks(String searchTerm) {
        return bookRepository.searchBooks(searchTerm).stream()
                .map(BookMapper::mapToBook)
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> searchBooks(String author, String title) {
        return bookRepository.searchBooksByAuthorAndTitle(author, title).stream()
                .map(BookMapper::mapToBook)
                .collect(Collectors.toList());
    }
}
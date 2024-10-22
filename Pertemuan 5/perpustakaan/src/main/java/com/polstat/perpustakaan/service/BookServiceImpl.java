package com.polstat.perpustakaan.service;

import com.polstat.perpustakaan.dto.BookDto;
import com.polstat.perpustakaan.entity.Book;
import com.polstat.perpustakaan.mapper.BookMapper;
import com.polstat.perpustakaan.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements  BookService {
    @Autowired
    private BookRepository bookRepository;

    @Override
    public BookDto createBook(BookDto bookDto) {
        Book book = bookRepository.save(BookMapper.mapToBook(bookDto));
        return BookMapper.mapToBookDto(book);
    }

    @Override
    public List<BookDto> getBooks() {
        List<Book> books = bookRepository.findAll();
        List<BookDto> bookDtos = books.stream()
                .map((product) -> (BookMapper.mapToBookDto(product)))
                .collect(Collectors.toList());
        return bookDtos;
    }

    @Override
    public BookDto getBook(Long id) {
        Book book = bookRepository.getReferenceById(id);
        return BookMapper.mapToBookDto(book);
    }

    @Override
    public BookDto updateBook(BookDto bookDto) {
        Book book = bookRepository.save(BookMapper.mapToBook(bookDto));
        return BookMapper.mapToBookDto(book);
    }

    @Override
    public void deleteBook(BookDto bookDto) {
        bookRepository.delete(BookMapper.mapToBook(bookDto));
    }

    public List<BookDto> searchBooks(String searchTerm) {
        return bookRepository.searchBooks(searchTerm).stream()
                .map(BookMapper::mapToBookDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookDto> searchBooks(String author, String title) {
        return bookRepository.searchBooksByAuthorAndTitle(author, title).stream()
                .map(BookMapper::mapToBookDto)
                .collect(Collectors.toList());
    }
}

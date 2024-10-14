package org.example.mapper;

import org.example.entity.BookEntity;
import com.example.soap.gen.Book;

public class BookMapper {
    public static BookEntity mapToEntity(Book book) {
        BookEntity entity = new BookEntity();
        entity.setId(book.getId());
        entity.setTitle(book.getTitle());
        entity.setAuthor(book.getAuthor());
        entity.setDescription(book.getDescription());
        return entity;
    }

    public static Book mapToBook(BookEntity entity) {
        Book book = new Book();
        book.setId(entity.getId());
        book.setTitle(entity.getTitle());
        book.setAuthor(entity.getAuthor());
        book.setDescription(entity.getDescription());
        return book;
    }
}
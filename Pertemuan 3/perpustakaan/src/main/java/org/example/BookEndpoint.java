package org.example;

import com.example.soap.gen.*;
import org.example.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

@Endpoint
public class BookEndpoint {
    private static final String NAMESPACE_URI = "http://www.example.com/soap/gen";

    @Autowired
    private BookService bookService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createBookRequest")
    @ResponsePayload
    public CreateBookResponse createBook(@RequestPayload CreateBookRequest request) {
        bookService.createBook(request.getBook());

        CreateBookResponse response = new CreateBookResponse();
        response.setResult("created");
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getBooksRequest")
    @ResponsePayload
    public GetBooksResponse getBooks(@RequestPayload GetBooksRequest request) {
        List<Book> books = bookService.getBooks();
        GetBooksResponse response = new GetBooksResponse();
        response.getBooks().addAll(books);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "searchBooksRequest")
    @ResponsePayload
    public SearchBooksResponse searchBooks(@RequestPayload SearchBooksRequest request) {
        List<Book> searchResults;
        if (request.getSearch() != null) {
            searchResults = bookService.searchBooks(request.getSearch());
        } else {
            searchResults = bookService.searchBooks(request.getAuthor(), request.getTitle());
        }
        SearchBooksResponse response = new SearchBooksResponse();
        response.getBooks().addAll(searchResults);
        return response;
    }
}
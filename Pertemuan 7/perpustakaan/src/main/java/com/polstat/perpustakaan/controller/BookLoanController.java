package com.polstat.perpustakaan.controller;

import com.polstat.perpustakaan.entity.Book;
import com.polstat.perpustakaan.entity.BookLoan;
import com.polstat.perpustakaan.entity.Member;
import com.polstat.perpustakaan.repository.BookLoanRepository;
import com.polstat.perpustakaan.repository.BookRepository;
import com.polstat.perpustakaan.repository.MemberRepository;
import com.polstat.perpustakaan.service.BookLoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/loans")
@Tag(name = "Book Loan", description = "Book Loan management APIs")
@SecurityRequirement(name = "bearerAuth")
public class BookLoanController {

    private static final Logger logger = LoggerFactory.getLogger(BookLoanController.class);

    @Autowired
    private BookLoanRepository bookLoanRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private BookLoanService bookLoanService;

    @Operation(summary = "Create a new book loan")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Loan created successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookLoan.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Book or Member not found",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<?> createLoan(@RequestBody BookLoanRequest request) {
        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new RuntimeException("Member not found"));

        BookLoan loan = new BookLoan();
        loan.setBook(book);
        loan.setMember(member);
        loan.setBorrowDate(request.getBorrowDate());
        loan.setStatus(BookLoan.LoanStatus.BORROWED);

        BookLoan savedLoan = bookLoanRepository.save(loan);
        return ResponseEntity.ok(savedLoan);
    }

    @Operation(summary = "Return a borrowed book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book returned successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookReturnResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid loan ID or book already returned",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})
    })
    @PostMapping("/return")
    public ResponseEntity<?> returnBook(@RequestBody BookReturnRequest request) {
        try {
            BookLoan returnedLoan = bookLoanService.returnBook(request.getLoanId());
            return ResponseEntity.ok(new BookReturnResponse(returnedLoan.getId(), "Book returned successfully"));
        } catch (IllegalStateException e) {
            logger.warn("Attempt to return an already returned book: {}", e.getMessage());
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            logger.error("Error returning book", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("An unexpected error occurred while returning the book"));
        }
    }

    @Schema(description = "Request object for creating a book loan")
    @Setter
    @Getter
    private static class BookLoanRequest {
        private Long bookId;
        private Long memberId;
        private LocalDate borrowDate;
    }

    @Schema(description = "Request object for returning a book")
    @Setter
    @Getter
    private static class BookReturnRequest {
        private Long loanId;
    }

    @Schema(description = "Response object for book return operation")
    @Getter
    @AllArgsConstructor
    private static class BookReturnResponse {
        private Long loanId;
        private String message;
    }

    @Schema(description = "Error response object")
    @Getter
    @AllArgsConstructor
    private static class ErrorResponse {
        private String error;
    }

}
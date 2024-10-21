package com.polstat.perpustakaan.service;

import com.polstat.perpustakaan.entity.Book;
import com.polstat.perpustakaan.entity.BookLoan;
import com.polstat.perpustakaan.entity.Member;
import com.polstat.perpustakaan.repository.BookLoanRepository;
import com.polstat.perpustakaan.repository.BookRepository;
import com.polstat.perpustakaan.repository.MemberRepository;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class BookLoanService {

    private static final Logger logger = LoggerFactory.getLogger(BookLoanService.class);

    @Autowired
    private BookLoanRepository bookLoanRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MemberRepository memberRepository;

    public BookLoan createLoan(Long memberId, Long bookId, LocalDate borrowDate) {
        return bookLoanRepository.createLoan(memberId, bookId, borrowDate, memberRepository, bookRepository);
    }

    public BookLoan borrowBook(Long memberId, Long bookId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        BookLoan bookLoan = BookLoan.builder()
                .member(member)
                .book(book)
                .borrowDate(LocalDate.now())
                .status(BookLoan.LoanStatus.BORROWED)
                .build();

        return bookLoanRepository.save(bookLoan);
    }

    @Transactional
    public BookLoan returnBook(Long loanId) {
        BookLoan bookLoan = bookLoanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found with ID: " + loanId));

        Hibernate.initialize(bookLoan.getMember());
        Hibernate.initialize(bookLoan.getBook());

        if (bookLoan.getStatus() == BookLoan.LoanStatus.RETURNED) {
            throw new IllegalStateException("Book already returned for loan ID: " + loanId);
        }

        LocalDate returnDate = LocalDate.now();
        bookLoan.setReturnDate(returnDate);
        bookLoan.setStatus(BookLoan.LoanStatus.RETURNED);

        long daysOverdue = ChronoUnit.DAYS.between(bookLoan.getBorrowDate(), returnDate) - 7;
        bookLoan.setDaysOverdue(daysOverdue > 0 ? (int) daysOverdue : 0);

        return bookLoanRepository.save(bookLoan);
    }

    public List<BookLoan> getLoansByMember(Long memberId) {
        return bookLoanRepository.findByMemberId(memberId);
    }

    public List<BookLoan> getLoansByBook(Long bookId) {
        return bookLoanRepository.findByBookId(bookId);
    }

    public List<BookLoan> getCurrentLoans() {
        return bookLoanRepository.findByStatus(BookLoan.LoanStatus.BORROWED);
    }
}
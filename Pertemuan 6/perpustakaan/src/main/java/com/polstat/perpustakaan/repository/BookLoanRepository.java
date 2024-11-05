package com.polstat.perpustakaan.repository;

import com.polstat.perpustakaan.entity.Book;
import com.polstat.perpustakaan.entity.BookLoan;
import com.polstat.perpustakaan.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.time.LocalDate;
import java.util.List;

@RepositoryRestResource(collectionResourceRel = "loans", path = "loans")
public interface BookLoanRepository extends JpaRepository<BookLoan, Long> {

    @RestResource(path = "byMember")
    List<BookLoan> findByMemberId(@Param("memberId") Long memberId);

    @RestResource(path = "byBook")
    List<BookLoan> findByBookId(@Param("bookId") Long bookId);

    @RestResource(path = "byStatus")
    List<BookLoan> findByStatus(@Param("status") BookLoan.LoanStatus status);

    @RestResource(path = "currentLoans")
    List<BookLoan> findByStatusAndReturnDateIsNull(@Param("status") BookLoan.LoanStatus status);

    @RestResource(path = "overdue")
    List<BookLoan> findByStatusAndReturnDateIsNullAndBorrowDateBefore(
            @Param("status") BookLoan.LoanStatus status,
            @Param("date") LocalDate date);

    @RestResource(exported = false)
    default BookLoan createLoan(@Param("memberId") Long memberId,
                                @Param("bookId") Long bookId,
                                @Param("borrowDate") LocalDate borrowDate,
                                MemberRepository memberRepository,
                                BookRepository bookRepository) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        BookLoan bookLoan = BookLoan.builder()
                .member(member)
                .book(book)
                .borrowDate(borrowDate)
                .status(BookLoan.LoanStatus.BORROWED)
                .build();

        return save(bookLoan);
    }
}
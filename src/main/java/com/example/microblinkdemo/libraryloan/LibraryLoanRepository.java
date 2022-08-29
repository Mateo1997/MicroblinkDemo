package com.example.microblinkdemo.libraryloan;

import com.example.microblinkdemo.libraryloan.domain.LibraryLoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibraryLoanRepository extends JpaRepository<LibraryLoan, Long> {

    @Query(value = "SELECT * FROM library_loan ll" +
            " INNER JOIN library_loan_record llr on llr.library_loan_id = ll.id" +
            " WHERE llr.book_copy_id = :bookCopyId" +
            " ORDER BY ll.borrow_date" +
            " DESC", nativeQuery = true)
    List<LibraryLoan> getHistoryByBookCopyId(@Param("bookCopyId") Integer bookCopyId);

    @Query(value = "SELECT TRUE FROM library_loan ll" +
            " INNER JOIN library_loan_record llr on llr.library_loan_id = ll.id" +
            " WHERE llr.book_copy_id = :bookCopyId" +
            " AND ll.return_date IS NULL", nativeQuery = true)
    Boolean isBookCopyBorrowed(@Param("bookCopyId") Integer bookCopyId);
}

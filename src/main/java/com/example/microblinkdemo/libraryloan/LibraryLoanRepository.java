package com.example.microblinkdemo.libraryloan;

import com.example.microblinkdemo.libraryloan.domain.LibraryLoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibraryLoanRepository extends JpaRepository<LibraryLoan, Long> {

    List<LibraryLoan> findByBookCopy_IdOrderByBorrowDateDesc(Integer bookCopyId);

    boolean existsByBookCopy_IdAndReturnDateIsNull(Integer bookCopyId);

    @Query(value = "SELECT * FROM LibraryLoan ubr" +
            " INNER JOIN UserBook u on u.id = ubr.userid" +
            " WHERE ubr.returndate IS NULL AND" +
            " EXTRACT(DAY FROM now() - ubr.duedate) > 0", nativeQuery = true)
    List<LibraryLoan> findMostOverdueUser();
}

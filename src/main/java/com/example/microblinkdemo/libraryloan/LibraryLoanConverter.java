package com.example.microblinkdemo.libraryloan;

import com.example.microblinkdemo.bookcopy.BookCopyConverter;
import com.example.microblinkdemo.bookcopy.domain.BookCopy;
import com.example.microblinkdemo.libraryloan.domain.LibraryLoan;
import com.example.microblinkdemo.libraryloan.domain.LibraryLoanHistory;
import com.example.microblinkdemo.libraryloan.domain.LibraryLoanRequest;
import com.example.microblinkdemo.libraryloanrecords.LibraryLoanRecord;
import com.example.microblinkdemo.user.UserConverter;
import com.example.microblinkdemo.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class LibraryLoanConverter {

    private final BookCopyConverter bookCopyConverter;
    private final UserConverter userConverter;

    @Value("${library.check.out.book.days}")
    private Integer checkOutBookInDays;

    public LibraryLoan requestToEntity(LibraryLoanRequest request) {
        return LibraryLoan.builder()
                .borrowDate(request.getBorrowDate())
                .number(UUID.randomUUID().toString())
                .dueDate(request.getBorrowDate().plusDays(checkOutBookInDays))
                .user(new User(request.getUserId()))
                .build();
    }

    public LibraryLoanRecord getLibraryLoanRecord(LibraryLoan libraryLoan, Integer bookCopyId) {
        return LibraryLoanRecord.builder()
                .libraryLoan(libraryLoan)
                .bookCopy(new BookCopy(bookCopyId))
                .build();
    }

    public LibraryLoanHistory entityToHistory(LibraryLoan libraryLoan) {
        final BookCopy bookCopy = getBookCopy(libraryLoan);
        return LibraryLoanHistory.builder()
                .bookCopy(bookCopyConverter.entityToDomain(bookCopy))
                .user(userConverter.entityToDomain(libraryLoan.getUser()))
                .number(libraryLoan.getNumber())
                .borrowDate(libraryLoan.getBorrowDate())
                .dueDate(libraryLoan.getDueDate())
                .returnDate(libraryLoan.getReturnDate())
                .build();
    }

    private BookCopy getBookCopy(LibraryLoan libraryLoan) {
        return libraryLoan.getLibraryLoanRecords()
                .stream()
                .findFirst()
                .orElseGet(LibraryLoanRecord::new)
                .getBookCopy();
    }
}

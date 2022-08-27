package com.example.microblinkdemo.libraryloan;

import com.example.microblinkdemo.bookcopy.BookCopyConverter;
import com.example.microblinkdemo.bookcopy.domain.BookCopy;
import com.example.microblinkdemo.libraryloan.domain.LibraryLoan;
import com.example.microblinkdemo.libraryloan.domain.LibraryLoanHistory;
import com.example.microblinkdemo.libraryloan.domain.LibraryLoanRequest;
import com.example.microblinkdemo.user.UserConverter;
import com.example.microblinkdemo.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LibraryLoanConverter {

    private final BookCopyConverter bookCopyConverter;
    private final UserConverter userConverter;

    @Value("${library.check.out.book.days}")
    private Integer checkOutBookInDays;

    public LibraryLoan requestToEntity(LibraryLoanRequest request, Integer bookCopyId) {
        return LibraryLoan.builder()
                .bookCopy(new BookCopy(bookCopyId))
                .user(new User(request.getUserId()))
                .borrowDate(request.getBorrowDate())
                .dueDate(request.getBorrowDate().plusDays(checkOutBookInDays))
                .build();
    }

    public LibraryLoanHistory entityToHistory(LibraryLoan libraryLoan) {
        return LibraryLoanHistory.builder()
                .bookCopy(bookCopyConverter.entityToDomain(libraryLoan.getBookCopy()))
                .user(userConverter.entityToDomain(libraryLoan.getUser()))
                .borrowDate(libraryLoan.getBorrowDate())
                .dueDate(libraryLoan.getDueDate())
                .returnDate(libraryLoan.getReturnDate())
                .build();
    }
}

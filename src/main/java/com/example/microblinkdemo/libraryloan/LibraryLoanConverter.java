package com.example.microblinkdemo.libraryloan;

import com.example.microblinkdemo.libraryloan.domain.LibraryLoan;
import com.example.microblinkdemo.libraryloan.domain.LibraryLoanHistory;
import com.example.microblinkdemo.libraryloan.domain.LibraryLoanRequest;
import com.example.microblinkdemo.user.UserConverter;
import com.example.microblinkdemo.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class LibraryLoanConverter {

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

    public LibraryLoanHistory entityToHistory(LibraryLoan libraryLoan) {
        return LibraryLoanHistory.builder()
                .user(userConverter.entityToDomain(libraryLoan.getUser()))
                .number(libraryLoan.getNumber())
                .borrowDate(libraryLoan.getBorrowDate())
                .dueDate(libraryLoan.getDueDate())
                .returnDate(libraryLoan.getReturnDate())
                .build();
    }
}

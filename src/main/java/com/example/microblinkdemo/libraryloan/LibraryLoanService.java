package com.example.microblinkdemo.libraryloan;

import com.example.microblinkdemo.bookcopy.BookCopyService;
import com.example.microblinkdemo.exception.MethodNotAllowedException;
import com.example.microblinkdemo.libraryloan.domain.BookDueDate;
import com.example.microblinkdemo.libraryloan.domain.LibraryLoan;
import com.example.microblinkdemo.libraryloan.domain.LibraryLoanHistory;
import com.example.microblinkdemo.libraryloan.domain.LibraryLoanRequest;
import com.example.microblinkdemo.user.UserService;
import com.example.microblinkdemo.util.ResponseConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LibraryLoanService {

    private final LibraryLoanRepository libraryLoanRepository;
    private final UserService userService;
    private final BookCopyService bookCopyService;
    private final LibraryLoanConverter libraryLoanConverter;

    @Value("${library.check.out.book.days}")
    private Integer checkOutBookInDays;

    public List<LibraryLoanHistory> history(Integer bookCopyId) {
        final List<LibraryLoan> libraryLoanEntities = libraryLoanRepository.findByBookCopy_IdOrderByBorrowDateDesc(bookCopyId);
        return libraryLoanEntities.stream()
                .map(libraryLoanConverter::entityToHistory)
                .toList();
    }

    public BookDueDate borrowBook(LibraryLoanRequest request) {
        List<LibraryLoan> libraryLoanEntities = new ArrayList<>();
        userService.throwExceptionIfNotExists(request.getUserId());

        for (Integer bookCopyId : request.getBookCopyIds()) {
            bookCopyService.throwExceptionIfNotExists(bookCopyId);
            throwExceptionIfBookIsLend(bookCopyId);
            libraryLoanEntities.add(libraryLoanConverter.requestToEntity(request, bookCopyId));
        }
        libraryLoanRepository.saveAll(libraryLoanEntities);
        final LocalDate dueDate = getBookDueDate(request.getBorrowDate());
        return new BookDueDate(dueDate);
    }

    private void throwExceptionIfBookIsLend(Integer bookCopyId) {
        final boolean isBookLend = libraryLoanRepository.existsByBookCopy_IdAndReturnDateIsNull(bookCopyId);
        if (isBookLend)
            throw new MethodNotAllowedException(ResponseConstants.ERROR_BOOK_ALREADY_LEND);
    }

    private LocalDate getBookDueDate(LocalDate borrowDate) {
        return borrowDate.plusDays(checkOutBookInDays);
    }
}

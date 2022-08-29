package com.example.microblinkdemo.libraryloan;

import com.example.microblinkdemo.bookcopy.BookCopyService;
import com.example.microblinkdemo.bookcopy.domain.BookCopy;
import com.example.microblinkdemo.exception.MethodNotAllowedException;
import com.example.microblinkdemo.libraryloan.domain.LibraryLoan;
import com.example.microblinkdemo.libraryloan.domain.LibraryLoanHistory;
import com.example.microblinkdemo.libraryloan.domain.LibraryLoanRequest;
import com.example.microblinkdemo.libraryloan.domain.LibraryLoanResponse;
import com.example.microblinkdemo.user.UserService;
import com.example.microblinkdemo.util.ResponseConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LibraryLoanService {

    private final LibraryLoanRepository libraryLoanRepository;
    private final UserService userService;
    private final BookCopyService bookCopyService;
    private final LibraryLoanConverter libraryLoanConverter;

    public List<LibraryLoanHistory> history(Integer bookCopyId) {
        final List<LibraryLoan> libraryLoanEntities = libraryLoanRepository.getHistoryByBookCopyId(bookCopyId);
        return libraryLoanEntities.stream()
                .map(libraryLoanConverter::entityToHistory)
                .toList();
    }

    public LibraryLoanResponse borrowBook(LibraryLoanRequest request) {
        List<BookCopy> bookCopies = new ArrayList<>();
        userService.throwExceptionIfNotExists(request.getUserId());

        LibraryLoan libraryLoan = libraryLoanConverter.requestToEntity(request);
        for (Integer bookCopyId : request.getBookCopyIds()) {
            bookCopyService.throwExceptionIfNotExists(bookCopyId);
            throwExceptionIfBookIsBorrowed(bookCopyId);
            bookCopies.add(new BookCopy(bookCopyId));
        }
        libraryLoan.setBookCopy(bookCopies);

        final LibraryLoan libraryLoanDTO = libraryLoanRepository.save(libraryLoan);
        return new LibraryLoanResponse(libraryLoanDTO.getNumber(), libraryLoanDTO.getDueDate());
    }

    private void throwExceptionIfBookIsBorrowed(Integer bookCopyId) {
        final Boolean isBorrowed = libraryLoanRepository.isBookCopyBorrowed(bookCopyId);
        if (Boolean.TRUE.equals(isBorrowed))
            throw new MethodNotAllowedException(ResponseConstants.ERROR_BOOK_ALREADY_BORROWED);
    }
}

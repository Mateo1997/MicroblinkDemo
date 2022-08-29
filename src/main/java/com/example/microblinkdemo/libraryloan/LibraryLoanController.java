package com.example.microblinkdemo.libraryloan;

import com.example.microblinkdemo.libraryloan.domain.LibraryLoanResponse;
import com.example.microblinkdemo.libraryloan.domain.LibraryLoanHistory;
import com.example.microblinkdemo.libraryloan.domain.LibraryLoanRequest;
import com.example.microblinkdemo.util.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("library-loan")
@RestController
@RequiredArgsConstructor
public class LibraryLoanController {

    private final LibraryLoanService libraryLoanService;

    @GetMapping(value = "/history")
    public List<LibraryLoanHistory> history(@RequestParam Integer bookCopyId) {
        return libraryLoanService.history(bookCopyId);
    }

    @PostMapping
    public LibraryLoanResponse borrowBook(@RequestBody @Valid LibraryLoanRequest request, BindingResult result) {
        Validator.requestAndDuplicateBooks(result, request.getBookCopyIds());
        return libraryLoanService.borrowBook(request);
    }
}
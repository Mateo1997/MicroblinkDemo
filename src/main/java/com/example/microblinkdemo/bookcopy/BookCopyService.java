package com.example.microblinkdemo.bookcopy;

import com.example.microblinkdemo.exception.NotFoundException;
import com.example.microblinkdemo.util.ResponseConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookCopyService {

    private final BookCopyRepository bookCopyRepository;

    public void throwExceptionIfNotExists(Integer bookCopyId) {
        final boolean bookCopyExists = doesBookCopyExists(bookCopyId);
        if (!bookCopyExists) {
            throw new NotFoundException(ResponseConstants.ERROR_BOOK_NOT_FOUND);
        }
    }

    private boolean doesBookCopyExists(Integer id) {
        return bookCopyRepository.existsById(id);
    }
}

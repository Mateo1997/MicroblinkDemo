package com.example.microblinkdemo.bookcopy;

import com.example.microblinkdemo.exception.NotFoundException;
import com.example.microblinkdemo.util.ResponseConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookCopyService {

    private final BookCopyRepository bookCopyRepository;

    public void throwExceptionIfNotExists(Integer id) {
        final boolean bookCopyExists = bookCopyRepository.existsById(id);
        if (!bookCopyExists) {
            throw new NotFoundException(ResponseConstants.ERROR_BOOK_COPY_NOT_FOUND);
        }
    }
}

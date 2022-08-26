package com.example.microblinkdemo.book;

import com.example.microblinkdemo.exception.NotFoundException;
import com.example.microblinkdemo.util.ResponseConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookRecordService {

    private final BookRecordRepository bookRecordRepository;

    public void throwExceptionIfNotExists(Integer bookRecordId) {
        final boolean bookRecordExists = doesBookRecordExists(bookRecordId);
        if (!bookRecordExists) {
            throw new NotFoundException(ResponseConstants.ERROR_BOOK_NOT_FOUND);
        }
    }

    private boolean doesBookRecordExists(Integer id) {
        return bookRecordRepository.existsById(id);
    }
}

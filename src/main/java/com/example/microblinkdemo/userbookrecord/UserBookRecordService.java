package com.example.microblinkdemo.userbookrecord;

import com.example.microblinkdemo.book.BookRecord;
import com.example.microblinkdemo.book.BookRecordService;
import com.example.microblinkdemo.exception.MethodNotAllowedException;
import com.example.microblinkdemo.user.UserService;
import com.example.microblinkdemo.user.domain.User;
import com.example.microblinkdemo.util.ResponseConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserBookRecordService {

    private final UserBookRecordRepository userBookRecordRepository;
    private final UserService userService;
    private final BookRecordService bookRecordService;

    public void borrowBook(UserBookRecordRequest request) {
        List<UserBookRecord> userBookRecords = new ArrayList<>();
        userService.throwExceptionIfNotExists(request.getUserId());

        for (Integer bookRecordId : request.getBookRecordIds()) {
            bookRecordService.throwExceptionIfNotExists(bookRecordId);
            throwExceptionIfBookIsLend(bookRecordId);
            userBookRecords.add(mapUserBookRecord(request, bookRecordId));
        }
        userBookRecordRepository.saveAll(userBookRecords);
    }

    private void throwExceptionIfBookIsLend(Integer bookRecordId) {
        final boolean isBookLend = userBookRecordRepository.existsByBookRecord_IdAndReturnTimeIsNull(bookRecordId);
        if (isBookLend)
            throw new MethodNotAllowedException(ResponseConstants.ERROR_BOOK_ALREADY_LEND);
    }

    private UserBookRecord mapUserBookRecord(UserBookRecordRequest request, Integer bookRecordId) {
        return UserBookRecord.builder()
                .bookRecord(new BookRecord(bookRecordId))
                .user(new User(request.getUserId()))
                .borrowTime(request.getBorrowTime())
                .build();
    }
}

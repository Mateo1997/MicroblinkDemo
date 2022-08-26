package com.example.microblinkdemo.userbookrecord;

import com.example.microblinkdemo.book.BookRecordService;
import com.example.microblinkdemo.book.domain.Book;
import com.example.microblinkdemo.book.domain.BookDomain;
import com.example.microblinkdemo.book.domain.BookRecord;
import com.example.microblinkdemo.exception.MethodNotAllowedException;
import com.example.microblinkdemo.user.UserService;
import com.example.microblinkdemo.user.domain.User;
import com.example.microblinkdemo.user.domain.UserDomain;
import com.example.microblinkdemo.userbookrecord.domain.UserBookRecord;
import com.example.microblinkdemo.userbookrecord.domain.UserBookRecordHistory;
import com.example.microblinkdemo.userbookrecord.domain.UserBookRecordRequest;
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

    public List<UserBookRecordHistory> history(Integer bookId) {
        final List<UserBookRecord> userBookRecordEntities = userBookRecordRepository.findByBookRecord_BookOrderByBorrowTimeDesc(new Book(bookId));
        return userBookRecordEntities.stream()
                .map(this::mapUserBookRecordHistory)
                .toList();
    }

    public void borrowBook(UserBookRecordRequest request) {
        List<UserBookRecord> userBookRecordEntities = new ArrayList<>();
        userService.throwExceptionIfNotExists(request.getUserId());

        for (Integer bookRecordId : request.getBookRecordIds()) {
            bookRecordService.throwExceptionIfNotExists(bookRecordId);
            throwExceptionIfBookIsLend(bookRecordId);
            userBookRecordEntities.add(mapUserBookRecord(request, bookRecordId));
        }
        userBookRecordRepository.saveAll(userBookRecordEntities);
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

    private UserBookRecordHistory mapUserBookRecordHistory(UserBookRecord userBookRecord) {
        return UserBookRecordHistory.builder()
                .book(mapBookDomain(userBookRecord.getBookRecord()))
                .user(mapUserDomain(userBookRecord.getUser()))
                .borrowTime(userBookRecord.getBorrowTime())
                .returnTime(userBookRecord.getReturnTime())
                .build();
    }

    private BookDomain mapBookDomain(BookRecord bookRecord) {
        Book book = bookRecord.getBook();
        return BookDomain.builder()
                .bookRecordId(bookRecord.getId())
                .serialNumber(bookRecord.getSerialNumber())
                .bookId(book.getId())
                .author(book.getAuthor())
                .publisher(book.getPublisher())
                .title(book.getTitle())
                .year(book.getYear())
                .build();
    }

    private UserDomain mapUserDomain(User user) {
        return UserDomain.builder()
                .id(user.getId())
                .serialNumber(user.getSerialNumber())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .dateOfBirth(user.getDateOfBirth())
                .build();
    }
}

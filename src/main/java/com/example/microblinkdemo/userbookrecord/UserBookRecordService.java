package com.example.microblinkdemo.userbookrecord;

import com.example.microblinkdemo.book.BookRecordService;
import com.example.microblinkdemo.book.domain.Book;
import com.example.microblinkdemo.book.domain.BookDomain;
import com.example.microblinkdemo.book.domain.BookRecord;
import com.example.microblinkdemo.exception.MethodNotAllowedException;
import com.example.microblinkdemo.user.UserService;
import com.example.microblinkdemo.user.domain.User;
import com.example.microblinkdemo.user.domain.UserDomain;
import com.example.microblinkdemo.userbookrecord.domain.*;
import com.example.microblinkdemo.util.ResponseConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserBookRecordService {

    private final UserBookRecordRepository userBookRecordRepository;
    private final UserService userService;
    private final BookRecordService bookRecordService;

    @Value("${library.days.of.keeping.book}")
    private Integer dayOfKeepingBook;

//    public UserOverdue mostOverdue() {
//        UserOverdue user = null;
//        final List<UserBookRecord> mostOverdueUsers = userBookRecordRepository.findMostOverdueUser();
//
//        Optional<Map.Entry<User, Long>> mostOverdueUser = mostOverdueUsers.stream()
//                .collect(Collectors.groupingBy(UserBookRecord::getUser,
//                        Collectors.summingLong(value -> ChronoUnit.DAYS.between(value.getDueDate(), LocalDate.now()))))
//                .entrySet().stream().max(Map.Entry.comparingByValue());
//
//        if (mostOverdueUser.isPresent()) {
//            UserDomain userDomain  = mapUserDomain(mostOverdueUser.get().getKey());
//
//        }
//
//        return user;
//    }

    public List<UserBookRecordHistory> history(Integer bookRecordId) {
        final List<UserBookRecord> userBookRecordEntities = userBookRecordRepository.findByBookRecord_IdOrderByBorrowDateDesc(bookRecordId);
        return userBookRecordEntities.stream()
                .map(this::mapUserBookRecordHistory)
                .toList();
    }

    public BookDueDate borrowBook(UserBookRecordRequest request) {
        List<UserBookRecord> userBookRecordEntities = new ArrayList<>();
        userService.throwExceptionIfNotExists(request.getUserId());

        for (Integer bookRecordId : request.getBookRecordIds()) {
            bookRecordService.throwExceptionIfNotExists(bookRecordId);
            throwExceptionIfBookIsLend(bookRecordId);
            userBookRecordEntities.add(mapUserBookRecord(request, bookRecordId));
        }
        userBookRecordRepository.saveAll(userBookRecordEntities);
        final LocalDate dueDate = getBookDueDate(request.getBorrowDate());
        return new BookDueDate(dueDate);
    }


    private void throwExceptionIfBookIsLend(Integer bookRecordId) {
        final boolean isBookLend = userBookRecordRepository.existsByBookRecord_IdAndReturnDateIsNull(bookRecordId);
        if (isBookLend)
            throw new MethodNotAllowedException(ResponseConstants.ERROR_BOOK_ALREADY_LEND);
    }

    private UserBookRecord mapUserBookRecord(UserBookRecordRequest request, Integer bookRecordId) {
        return UserBookRecord.builder()
                .bookRecord(new BookRecord(bookRecordId))
                .user(new User(request.getUserId()))
                .borrowDate(request.getBorrowDate())
                .dueDate(getBookDueDate(request.getBorrowDate()))
                .build();
    }

    private LocalDate getBookDueDate(LocalDate borrowDate) {
        return borrowDate.plusDays(dayOfKeepingBook);
    }

    private UserBookRecordHistory mapUserBookRecordHistory(UserBookRecord userBookRecord) {
        return UserBookRecordHistory.builder()
                .book(mapBookDomain(userBookRecord.getBookRecord()))
                .user(mapUserDomain(userBookRecord.getUser()))
                .borrowDate(userBookRecord.getBorrowDate())
                .dueDate(userBookRecord.getDueDate())
                .returnDate(userBookRecord.getReturnDate())
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

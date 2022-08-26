package com.example.microblinkdemo.userbookrecord;

import com.example.microblinkdemo.book.domain.Book;
import com.example.microblinkdemo.userbookrecord.domain.UserBookRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserBookRecordRepository extends JpaRepository<UserBookRecord, Long> {

    List<UserBookRecord> findByBookRecord_BookOrderByBorrowTimeDesc(Book book);

    boolean existsByBookRecord_IdAndReturnTimeIsNull(Integer bookRecordId);
}

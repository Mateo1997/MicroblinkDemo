package com.example.microblinkdemo.book;

import com.example.microblinkdemo.book.domain.BookRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRecordRepository extends JpaRepository<BookRecord, Integer> {
}

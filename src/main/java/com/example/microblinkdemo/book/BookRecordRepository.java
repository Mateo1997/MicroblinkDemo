package com.example.microblinkdemo.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRecordRepository extends JpaRepository<BookRecord, Integer> {
}

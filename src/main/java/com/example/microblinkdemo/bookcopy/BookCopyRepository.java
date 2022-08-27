package com.example.microblinkdemo.bookcopy;

import com.example.microblinkdemo.bookcopy.domain.BookCopy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookCopyRepository extends JpaRepository<BookCopy, Integer> {
}

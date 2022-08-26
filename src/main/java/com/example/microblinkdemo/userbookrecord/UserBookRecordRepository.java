package com.example.microblinkdemo.userbookrecord;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserBookRecordRepository extends JpaRepository<UserBookRecord, Long> {

    boolean existsByBookRecord_IdAndReturnTimeIsNull(Integer bookRecordId);
}

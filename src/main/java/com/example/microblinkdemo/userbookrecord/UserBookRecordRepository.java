package com.example.microblinkdemo.userbookrecord;

import com.example.microblinkdemo.userbookrecord.domain.UserBookRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserBookRecordRepository extends JpaRepository<UserBookRecord, Long> {

    List<UserBookRecord> findByBookRecord_IdOrderByBorrowDateDesc(Integer bookRecordId);

    boolean existsByBookRecord_IdAndReturnDateIsNull(Integer bookRecordId);

    @Query(value = "SELECT * FROM UserBookRecord ubr" +
            " INNER JOIN UserBook u on u.id = ubr.userid" +
            " WHERE ubr.returndate IS NULL AND" +
            " EXTRACT(DAY FROM now() - ubr.duedate) > 0", nativeQuery = true)
    List<UserBookRecord> findMostOverdueUser();
}

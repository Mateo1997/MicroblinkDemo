package com.example.microblinkdemo.user;

import com.example.microblinkdemo.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "SELECT TOP (1) u.*" +
            " FROM library_loan ll" +
            " INNER JOIN [user] u ON u.id = ll.user_id" +
            " WHERE ll.return_date IS NULL AND" +
            " DATEDIFF(DAY, ll.due_date, GETDATE()) > 0" +
            " GROUP BY" +
            " u.id," +
            " u.card_number," +
            " u.first_name," +
            " u.last_name," +
            " u.date_of_birth," +
            " u.creation_time," +
            " u.modification_time" +
            " ORDER BY SUM(DATEDIFF(DAY, ll.due_date, GETDATE()))" +
            " DESC", nativeQuery = true)
    User findMostOverdue();
}

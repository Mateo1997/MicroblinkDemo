package com.example.microblinkdemo.user;

import com.example.microblinkdemo.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "SELECT u.* FROM library_loan ll" +
            " INNER JOIN \"user\" u ON u.id = ll.user_id" +
            " WHERE ll.return_date IS NULL AND" +
            " EXTRACT(DAY FROM now() - ll.due_date) > 0" +
            " GROUP BY u.id" +
            " ORDER BY SUM(EXTRACT(DAY FROM now() - ll.due_date))" +
            " DESC LIMIT 1", nativeQuery = true)
    User findMostOverdue();
}

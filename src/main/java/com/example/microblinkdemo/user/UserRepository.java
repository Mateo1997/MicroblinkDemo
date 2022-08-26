package com.example.microblinkdemo.user;

import com.example.microblinkdemo.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "SELECT u.* FROM userbookrecord ubr" +
            " INNER JOIN userbook u on ubr.userid = u.id" +
            " WHERE ubr.returndate is null AND EXTRACT(DAY FROM now() - duedate) > 0" +
            " GROUP BY u.id" +
            " ORDER BY SUM(EXTRACT(DAY FROM now() - duedate)) DESC" +
            " LIMIT 1", nativeQuery = true)
    User findMostOverdue();
}

package com.example.microblinkdemo.mrtd;

import com.example.microblinkdemo.mrtd.domain.PersonIdentity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MrtdRepository extends JpaRepository<PersonIdentity, Long> {
}

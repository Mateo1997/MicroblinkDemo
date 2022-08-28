package com.example.microblinkdemo.mrz;

import com.example.microblinkdemo.mrz.domain.Mrz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MrzRepository extends JpaRepository<Mrz, Long> {
}

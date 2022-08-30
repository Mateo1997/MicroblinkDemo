package com.example.microblinkdemo.user.domain;

import com.example.microblinkdemo.libraryloan.domain.LibraryLoan;
import com.example.microblinkdemo.util.TimestampEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Table(name = "\"user\"")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends TimestampEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, updatable = false)
    private String cardNumber;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @OneToMany(mappedBy = "user")
    private List<LibraryLoan> libraryLoans;

    public User(Integer id) {
        this.id = id;
    }
}
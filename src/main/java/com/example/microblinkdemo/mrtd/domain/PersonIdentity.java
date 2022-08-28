package com.example.microblinkdemo.mrtd.domain;

import com.example.microblinkdemo.util.TimestampEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonIdentity extends TimestampEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String documentCode;
    private String issuer;
    private String documentNumber;
    private String dateOfBirth;
    private String gender;
    private String dateOfExpiry;
    private String nationality;
    private String primaryId;
    private String secondaryId;
    private String optionalData1;
    private String optionalData2;
    private Boolean valid;

}

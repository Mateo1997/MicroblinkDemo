package com.example.microblinkdemo.util;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
public abstract class TimestampEntity {

    @Column(updatable = false, nullable = false)
    @CreationTimestamp
    private LocalDateTime creationTime;

    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime modificationTime;
}

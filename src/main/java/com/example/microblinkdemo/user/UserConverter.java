package com.example.microblinkdemo.user;

import com.example.microblinkdemo.user.domain.User;
import com.example.microblinkdemo.user.domain.UserDomain;
import com.example.microblinkdemo.user.domain.UserRequestCreate;
import com.example.microblinkdemo.user.domain.UserRequestUpdate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserConverter {

    public User createRequestToEntity(UserRequestCreate request) {
        return User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .dateOfBirth(request.getDateOfBirth())
                .cardNumber(UUID.randomUUID().toString())
                .build();
    }

    public User updateRequestToEntity(UserRequestUpdate request) {
        return User.builder()
                .id(request.getId())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .dateOfBirth(request.getDateOfBirth())
                .build();
    }

    public UserDomain entityToDomain(User user) {
        return UserDomain.builder()
                .id(user.getId())
                .cardNumber(user.getCardNumber())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .dateOfBirth(user.getDateOfBirth())
                .build();
    }
}

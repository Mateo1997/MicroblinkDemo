package com.example.microblinkdemo.user.domain;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class UserRequestUpdate extends UserRequest {

    @NotNull
    private Integer id;
}

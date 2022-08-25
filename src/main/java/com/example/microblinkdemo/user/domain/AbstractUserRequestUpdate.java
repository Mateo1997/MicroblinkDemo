package com.example.microblinkdemo.user.domain;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class AbstractUserRequestUpdate extends AbstractUser {

    @NotNull
    private Integer id;
}

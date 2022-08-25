package com.example.microblinkdemo.user;

import com.example.microblinkdemo.user.domain.User;
import com.example.microblinkdemo.user.domain.AbstractUserRequestCreate;
import com.example.microblinkdemo.user.domain.AbstractUserRequestUpdate;
import com.example.microblinkdemo.util.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RequestMapping("/user")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("{id}")
    public User findById(@PathVariable Integer id) {
        return userService.findById(id);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public User save(@RequestBody @Valid AbstractUserRequestCreate request, BindingResult result) {
        Validator.request(result);
        return userService.save(request);
    }

    @PutMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@RequestBody @Valid AbstractUserRequestUpdate request) {
        userService.update(request);
    }
}
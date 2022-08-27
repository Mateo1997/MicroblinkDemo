package com.example.microblinkdemo.user;

import com.example.microblinkdemo.user.domain.UserDomain;
import com.example.microblinkdemo.user.domain.UserRequestCreate;
import com.example.microblinkdemo.user.domain.UserRequestUpdate;
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

    @GetMapping(value = "{id}")
    public UserDomain findById(@PathVariable Integer id) {
        return userService.findById(id);
    }

    @GetMapping(value = "/overdue/top")
    public UserDomain mostOverdue() {
        return userService.findMostOverdue();
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public UserDomain save(@RequestBody @Valid UserRequestCreate request, BindingResult result) {
        Validator.request(result);
        return userService.save(request);
    }

    @PutMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@RequestBody @Valid UserRequestUpdate request, BindingResult result) {
        Validator.request(result);
        userService.update(request);
    }
}

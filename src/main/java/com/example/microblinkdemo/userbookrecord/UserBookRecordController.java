package com.example.microblinkdemo.userbookrecord;

import com.example.microblinkdemo.util.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("user-book-record")
@RestController
@RequiredArgsConstructor
public class UserBookRecordController {

    private final UserBookRecordService userBookRecordService;

    @PostMapping(value = "/borrow")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void borrowBook(@RequestBody @Valid UserBookRecordRequest request, BindingResult result) {
        Validator.request(result);
        userBookRecordService.borrowBook(request);
    }
}

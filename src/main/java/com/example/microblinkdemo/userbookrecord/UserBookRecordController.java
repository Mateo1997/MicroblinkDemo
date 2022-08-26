package com.example.microblinkdemo.userbookrecord;

import com.example.microblinkdemo.userbookrecord.domain.UserBookRecordHistory;
import com.example.microblinkdemo.userbookrecord.domain.UserBookRecordRequest;
import com.example.microblinkdemo.util.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("user-book-record")
@RestController
@RequiredArgsConstructor
public class UserBookRecordController {

    private final UserBookRecordService userBookRecordService;

    @GetMapping(value = "/history")
    public List<UserBookRecordHistory> history(@RequestParam Integer bookId) {
        return userBookRecordService.history(bookId);
    }

    @PostMapping(value = "/borrow")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void borrowBook(@RequestBody @Valid UserBookRecordRequest request, BindingResult result) {
        Validator.request(result);
        userBookRecordService.borrowBook(request);
    }
}

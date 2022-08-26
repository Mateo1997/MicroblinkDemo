package com.example.microblinkdemo.userbookrecord;

import com.example.microblinkdemo.userbookrecord.domain.BookDueDate;
import com.example.microblinkdemo.userbookrecord.domain.UserBookRecordHistory;
import com.example.microblinkdemo.userbookrecord.domain.UserBookRecordRequest;
import com.example.microblinkdemo.util.Validator;
import lombok.RequiredArgsConstructor;
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
    public List<UserBookRecordHistory> history(@RequestParam Integer bookRecordId) {
        return userBookRecordService.history(bookRecordId);
    }

    @PostMapping(value = "/borrow")
    public BookDueDate borrowBook(@RequestBody @Valid UserBookRecordRequest request, BindingResult result) {
        Validator.request(result);
        return userBookRecordService.borrowBook(request);
    }
}
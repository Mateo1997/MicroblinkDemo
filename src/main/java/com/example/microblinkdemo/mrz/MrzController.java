package com.example.microblinkdemo.mrz;

import com.example.microblinkdemo.mrz.domain.MrtdRequest;
import com.example.microblinkdemo.util.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("/mrz")
@RestController
@RequiredArgsConstructor
public class MrzController {

    private final MrzService mrzService;

    @PostMapping
    public void parseMrzData(@RequestBody @Valid MrtdRequest request, BindingResult result) {
        Validator.request(result);
        mrzService.parseMrzData(request);
    }
}

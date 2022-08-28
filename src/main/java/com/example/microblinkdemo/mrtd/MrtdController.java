package com.example.microblinkdemo.mrtd;

import com.example.microblinkdemo.mrtd.domain.MRTDRequest;
import com.example.microblinkdemo.util.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/mrtd")
@RestController
@RequiredArgsConstructor
public class MrtdController {

    private final MrtdService mrtdService;

    @PostMapping
    public void process(@RequestBody @Valid MRTDRequest request, BindingResult result) {
        Validator.request(result);
        mrtdService.process(request);
    }
}

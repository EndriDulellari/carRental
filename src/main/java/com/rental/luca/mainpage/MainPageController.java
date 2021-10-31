package com.rental.luca.mainpage;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class MainPageController {


    @GetMapping("")
    public ResponseEntity<String> mainPageContent() {
        return new ResponseEntity<>("Welcome!", HttpStatus.OK);
    }
}

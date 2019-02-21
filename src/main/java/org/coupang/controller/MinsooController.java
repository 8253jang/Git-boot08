package org.coupang.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/minsoo")
public class MinsooController {

    @GetMapping
    public String hello() {
        return "hello minsoo";
    }
}

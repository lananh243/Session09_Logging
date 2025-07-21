package com.ra.ss9.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Bai6 {
    @GetMapping("/test")
    public String test() {
        String str = null;
        return str.toString();
    }

}

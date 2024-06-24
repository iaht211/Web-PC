package com.vn.laptopshop;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/")
    public String index() {
        return "Hello world update";
    }

    @GetMapping("/user")
    public String user() {
        return "Only user can use this link";
    }

    @GetMapping("/admin")
    public String admin() {
        return "Only admin can use this link";
    }
}

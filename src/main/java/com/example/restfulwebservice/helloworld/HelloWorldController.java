package com.example.restfulwebservice.helloworld;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
    // GET
    // /hello-world
    @GetMapping("/hello-world")
    public HelloWorldBean helloWorld() {
        return new HelloWorldBean("Hello World");
    }
}

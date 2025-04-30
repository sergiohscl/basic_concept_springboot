package com.sergiohscl.basic_concept_springboot;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api")
public class helloController {
    
    @GetMapping("/hello")
    public String hello() {
        return "Hello World!!! Welcome to Spring Boot.";
    }   
}

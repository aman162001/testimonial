package com.io.testimonial.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.io.testimonial.model.User;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
@RequestMapping
public class UserContoller {
    
    @GetMapping("/test")
    public ResponseEntity<Map<String,String>> test() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "OK");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<Map<String,String>> signup(@RequestBody User user) {
        //TODO: process POST request
        System.out.println(user);
        return new ResponseEntity<>( HttpStatus.OK);
    }
    

}

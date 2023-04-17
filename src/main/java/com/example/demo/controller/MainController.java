package com.example.demo.controller;

import com.example.demo.entity.Task;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

// what is mapping:mapping-> In context of spring boot, mapping refers to the process of defining how HTTP Requests should be handled
//REST->Representational state transfer, uses standard HTTP Methods to deliver data to clients over Web
//REST endpoints allow client to preform CRUD action (Create, Read, Update, Delete)
// Controller is class that handles HTTP request and returns HTTP response to client
@Controller
public class MainController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/common")
    public String common() {
        return "common";
        }



    @GetMapping("/user")
    public String user() {
        return "user";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }
}
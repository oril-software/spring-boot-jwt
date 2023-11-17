package com.jwt.example.controller;

import com.jwt.example.model.User;
import com.jwt.example.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public String registerUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @GetMapping("/get")
    public User getUser(@RequestAttribute(value = "userId") ObjectId userId) {
        return userService.getUser(userId);
    }



}

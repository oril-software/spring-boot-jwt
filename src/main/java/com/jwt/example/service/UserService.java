package com.jwt.example.service;

import com.jwt.example.model.User;
import com.jwt.example.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final TokenService tokenService;

    @Autowired
    UserService(UserRepository userRepository, TokenService tokenService) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    public User getUser(ObjectId userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public String saveUser(User user) {
        User savedUser = userRepository.save(user);
        return tokenService.createToken(savedUser.getId());
    }
}

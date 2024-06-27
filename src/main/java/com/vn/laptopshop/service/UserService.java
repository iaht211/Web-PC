package com.vn.laptopshop.service;

import org.springframework.stereotype.Service;

import com.vn.laptopshop.domain.User;
import com.vn.laptopshop.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String handleHello() {
        return "hello form serveice";
    }

    public User createUser(User user) {
        User mavis = this.userRepository.save(user);
        System.out.println(mavis);
        return mavis;
    }
}

package com.ajay.service;

import com.ajay.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserService {

    @Autowired
    private UserRepository userRepository;



    @Test
    void findUserByIDTest(Long userId){


    }
}

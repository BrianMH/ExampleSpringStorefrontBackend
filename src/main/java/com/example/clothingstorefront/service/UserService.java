package com.example.clothingstorefront.service;

import com.example.clothingstorefront.model.User;
import com.example.clothingstorefront.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepo;

    public User getByUUID(UUID uuid) {
        return userRepo.getUserByUserId(uuid);
    }
}

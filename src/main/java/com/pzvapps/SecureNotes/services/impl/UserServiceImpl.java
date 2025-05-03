package com.pzvapps.SecureNotes.services.impl;

import com.pzvapps.SecureNotes.model.User;
import com.pzvapps.SecureNotes.repository.UserRepository;
import com.pzvapps.SecureNotes.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User findByUsername(String username) {
        Optional<User> user = userRepository.findByUserName(username);
        return user.orElseThrow(() -> new RuntimeException("User not found with username: " + username));
    }
}

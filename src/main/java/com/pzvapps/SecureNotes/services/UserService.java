package com.pzvapps.SecureNotes.services;

import com.pzvapps.SecureNotes.model.User;

public interface UserService {
    public User findByUsername(String username);

    }

package com.jardev.auth.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.jardev.auth.domain.user.User;
import com.jardev.auth.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserRepository repository;

    @Transactional
    public User create(User data) {
        if (this.repository.findByLogin(data.getLogin()) != null) {
            return null;
        }

        String encryptedPassword = this.bCryptPasswordEncoder.encode(data.getPassword());
        User user = new User(data.getLogin(), encryptedPassword, data.getRole());

        this.repository.save(user);

        return user;
    }
}

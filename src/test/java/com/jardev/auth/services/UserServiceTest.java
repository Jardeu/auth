package com.jardev.auth.services;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import com.jardev.auth.domain.user.User;
import com.jardev.auth.domain.user.UserRole;
import com.jardev.auth.repositories.UserRepository;

@ActiveProfiles("test")
public class UserServiceTest {

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    UserRepository repository;

    @InjectMocks
    UserService userService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should create user successfully when everything is ok.")
    void whenCreateUser_thenReturnUser() {
        User user = new User("jardeu", "1234", UserRole.USER);

        userService.create(user);

        verify(repository, times(1)).save(any());
    }

    @Test
    @DisplayName("Should not create user when it already exists.")
    void whenCreateUser_thenReturnNull() {
        User user = new User("jardeu", "1234", UserRole.USER);

        when(repository.findByLogin(user.getLogin())).thenReturn(user);

        User registeredUser = userService.create(user);

        assertNull(registeredUser);
    }
}

package com.jardev.auth.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

import com.jardev.auth.domain.user.RegisterDTO;
import com.jardev.auth.domain.user.User;
import com.jardev.auth.domain.user.UserRole;

import jakarta.persistence.EntityManager;

@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Should get user successfully from DB")
    void whenFindByLogin_thenReturnUser() {
        String login = "jardeu";
        RegisterDTO data = new RegisterDTO(login, "1234", UserRole.USER);
        this.createUser(data);

        UserDetails result = this.userRepository.findByLogin(login);

        assertThat(result.getUsername()).isEqualTo(login);
    }

    @Test
    @DisplayName("Should not get user from DB when user not exists")
    void whenNotFindByLogin_thenReturnNull() {
        String login = "jardeu";

        UserDetails result = this.userRepository.findByLogin(login);

        assertNull(result);
    }

    private User createUser(RegisterDTO data) {
        User newUser = new User(data.login(), data.password(), data.role());
        this.entityManager.persist(newUser);
        this.entityManager.flush();

        return newUser;
    }
}

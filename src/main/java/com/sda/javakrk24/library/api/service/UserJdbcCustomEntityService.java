package com.sda.javakrk24.library.api.service;

import com.sda.javakrk24.library.api.config.JdbcCustomEntityAuthorizationConfig;
import com.sda.javakrk24.library.api.dao.UserEntity;
import com.sda.javakrk24.library.api.repository.UsersRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnBean(JdbcCustomEntityAuthorizationConfig.class)
public class UserJdbcCustomEntityService implements UserService {

    private final UsersRepository repository;
    private final PasswordEncoder encoder;

    public UserJdbcCustomEntityService(UsersRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Override
    public void addUser(String email, String password) {
        UserEntity entity = new UserEntity();
        entity.setEmail(email);
        entity.setPassword(encoder.encode(password));
        repository.save(entity);
    }
}

package com.sda.javakrk24.library.api.config;

import com.sda.javakrk24.library.api.dao.UserEntity;
import com.sda.javakrk24.library.api.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JdbcCustomUserDetailsProvider implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Autowired
    public JdbcCustomUserDetailsProvider(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> firstByLoginOrEmail = usersRepository.findFirstByLoginOrEmail(username, username);
        return firstByLoginOrEmail.map(x ->
                User.builder()
                        .username(x.getUserId().toString())
                        .password(x.getPassword())
                        .roles()
                .build()
        ).orElse(null);
    }

}

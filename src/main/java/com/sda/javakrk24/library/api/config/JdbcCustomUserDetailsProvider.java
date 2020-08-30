package com.sda.javakrk24.library.api.config;

import com.sda.javakrk24.library.api.dao.UserEntity;
import com.sda.javakrk24.library.api.dao.UserRoleEntity;
import com.sda.javakrk24.library.api.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@ConditionalOnBean(JdbcCustomEntityAuthorizationConfig.class)
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
                        .roles(getRoles(x))
                        .build()
        ).orElse(null);
    }

    private String[] getRoles(UserEntity x) {
        List<UserRoleEntity> userRoles = usersRepository.findUserRoles(x.getUserId());
        return Optional.ofNullable(userRoles)
                .map(list -> list.stream().map(roleEntity -> roleEntity.getRole()).map(str -> str.substring(5)).peek(role -> System.out.println(role)).collect(Collectors.toList()))
                .map(list -> list.toArray(new String[0]))
                .orElse(new String[0]);
    }

}

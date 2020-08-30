package com.sda.javakrk24.library.api.config;

import com.sda.javakrk24.library.api.dao.UserEntity;
import com.sda.javakrk24.library.api.dao.UserRoleEntity;
import com.sda.javakrk24.library.api.repository.UsersRepository;
import com.sda.javakrk24.library.api.repository.UsersRolesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

@Slf4j
@Configuration
@Profile("authorization-jdbc-custom-with-entity")
public class JdbcCustomEntityAuthorizationConfig extends AbstractSecurityConfig {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private UsersRolesRepository usersRolesRepository;

    @Autowired
    private JdbcCustomUserDetailsProvider provider;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //stworzyc usera i wrzucic do bazy przy pomocy repozytoriow
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin("pmarcisz");
        userEntity.setEmail("patryk@marcisz.pl");
        userEntity.setPassword(encoder.encode("123"));
        userEntity.setActive(true);
        usersRepository.save(userEntity);

        UserRoleEntity rolesEntity = new UserRoleEntity();
        rolesEntity.setUser(userEntity);
        rolesEntity.setRole("ROLE_API_USER");
        usersRolesRepository.save(rolesEntity);

        auth.userDetailsService(provider);
    }
}

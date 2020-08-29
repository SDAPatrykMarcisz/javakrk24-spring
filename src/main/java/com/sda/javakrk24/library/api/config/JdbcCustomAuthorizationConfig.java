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

import javax.sql.DataSource;

@Slf4j
@Configuration
@Profile("authorization-jdbc-custom")
public class JdbcCustomAuthorizationConfig extends AbstractSecurityConfig {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private UsersRolesRepository usersRolesRepository;

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

        //a potem wypelnic query
        auth.jdbcAuthentication()
                .usersByUsernameQuery("select email, password, active from app_users where email=?")
                .authoritiesByUsernameQuery("select users.email, roles.role from app_users_roles as roles " +
                        "left join app_users as users on roles.user_user_id = users.user_id where email=?")
                .dataSource(dataSource);
                //.withUser(getAppUser()); pojdzie exception
    }
}

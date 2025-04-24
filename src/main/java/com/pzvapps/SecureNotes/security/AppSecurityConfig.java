package com.pzvapps.SecureNotes.security;

import com.pzvapps.SecureNotes.model.AppRole;
import com.pzvapps.SecureNotes.model.Role;
import com.pzvapps.SecureNotes.model.User;
import com.pzvapps.SecureNotes.repository.RoleRepository;
import com.pzvapps.SecureNotes.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.time.LocalDate;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig {


    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(
                request -> request
                        .requestMatchers("/contact").permitAll()
                        .anyRequest().authenticated()
        );
        http.sessionManagement(session  ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                ) ;
        http.csrf(AbstractHttpConfigurer::disable);
        http.httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder (){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CommandLineRunner initData(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            Role userRole = roleRepository.findByRoleName(AppRole.ROLE_USER)
                    .orElseGet(() -> roleRepository.save(new Role(AppRole.ROLE_USER)));

            Role adminRole = roleRepository.findByRoleName(AppRole.ROLE_ADMIN)
                    .orElseGet(() -> roleRepository.save(new Role(AppRole.ROLE_ADMIN)));

            if (!userRepository.existsByUserName("user1")) {
                User user1 = new User("user1", "user1@example.com",
                        passwordEncoder.encode("password1"));
                user1.setAccountNonLocked(false);
                user1.setAccountNonExpired(true);
                user1.setCredentialsNonExpired(true);
                user1.setEnabled(true);
                user1.setCredentialsExpiryDate(LocalDate.now().plusYears(1));
                user1.setAccountExpiryDate(LocalDate.now().plusYears(1));
                user1.setTwoFactorEnabled(false);
                user1.setSignUpMethod("email");
                user1.setRole(userRole);
                userRepository.save(user1);
            }

            if (!userRepository.existsByUserName("admin")) {
                User admin = new User("admin", "admin@example.com",
                        passwordEncoder.encode("adminPass"));
                admin.setAccountNonLocked(true);
                admin.setAccountNonExpired(true);
                admin.setCredentialsNonExpired(true);
                admin.setEnabled(true);
                admin.setCredentialsExpiryDate(LocalDate.now().plusYears(1));
                admin.setAccountExpiryDate(LocalDate.now().plusYears(1));
                admin.setTwoFactorEnabled(false);
                admin.setSignUpMethod("email");
                admin.setRole(adminRole);
                userRepository.save(admin);
            }
        };
    }

//    @Bean
//    public UserDetailsService getUserDetailsService(DataSource dataSource){
//
//        //InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//
//        JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);
//
//        if(!manager.userExists("user1")) {
//            manager.createUser(
//                    User.withUsername("user1")
//                            .password("{noop}password1")
//                            .roles("USER")
//                            .build()
//            );
//        }
//        if(!manager.userExists("admin")) {
//            manager.createUser(
//                    User.withUsername("admin")
//                            .password("{noop}password")
//                            .roles("USER")
//                            .build()
//            );
//        }
//
//        return manager;
//    }
}

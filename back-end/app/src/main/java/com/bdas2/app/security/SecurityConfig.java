package com.bdas2.app.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    final UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

//    @Bean
//    PasswordEncoder passwordEncoder(){
//        return new CustomPasswordEncoder();
//    }

    @Bean
    AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        return provider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http.csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeRequests(auth ->{
                    auth.antMatchers("/login").permitAll();
                    auth.antMatchers("/linkyDetail").permitAll();
                    auth.antMatchers("/linka/read").permitAll();
                    auth.regexMatchers("^/\\D+").hasRole("1");
                    auth.regexMatchers("^/spojDetail").hasAnyRole("1", "2");
                    auth.regexMatchers("^/spoj/read").hasAnyRole("1", "2");
                    auth.regexMatchers("^/uzivatel_view/update").hasAnyRole("1", "2");
                })
                .httpBasic(Customizer.withDefaults())
                .build();

    }
}

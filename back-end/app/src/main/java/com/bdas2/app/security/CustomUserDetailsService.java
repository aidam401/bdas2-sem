package com.bdas2.app.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    final SecurityRepository repository;

    public CustomUserDetailsService(SecurityRepository repository) {
        this.repository = repository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var detail = repository.fetchCustomeUserDetails(username);
        if (detail == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return detail;
    }
}

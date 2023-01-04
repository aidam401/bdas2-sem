package com.bdas2.app.security;

import com.bdas2.app.dao.Dao;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    Dao dao;

    public CustomUserDetailsService(Dao dao) {
        this.dao = dao;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var detail = dao.getUserDetail(username);
        if (detail == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return detail;
    }
}

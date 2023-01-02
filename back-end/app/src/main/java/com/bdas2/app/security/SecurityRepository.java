package com.bdas2.app.security;

import com.bdas2.app.dao.Dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Types;

@Repository
public class SecurityRepository {
    final Dao dao;

    public SecurityRepository(Dao dao) {
        this.dao = dao;
    }

    public CustomeUserDetails fetchCustomeUserDetails(String name){
        try {
            return dao.login(name);
        }catch (DataAccessException e){
            return null;
        }
    }

}

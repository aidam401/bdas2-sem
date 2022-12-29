package com.bdas2.app.repository;

import com.bdas2.app.dao.Dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Types;

@Repository
public class LoginRepository {

    final Dao dao;

    public LoginRepository(Dao dao) {
        this.dao = dao;
    }

    public boolean userExist(String name, String password){
        return dao.fetchObject("SELECT COUNT(*) FROM UZIVATEL WHERE login=? AND heslo = ?", new Object[]{name, password}, new int[]{Types.VARCHAR, Types.VARCHAR}, Boolean.class);
    }


}

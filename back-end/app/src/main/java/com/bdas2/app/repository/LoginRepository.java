package com.bdas2.app.repository;

import com.bdas2.app.dao.Dao;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.Objects;

@Repository
public class LoginRepository {

    final Dao dao;

    public LoginRepository(Dao dao) {
        this.dao = dao;
    }

    public JSONArray userExist(String name, String password, String anotherUser){
        var exist =  dao.fetchObject("SELECT COUNT(*) FROM UZIVATEL WHERE login=? AND heslo = ?", new Object[]{name, password}, new int[]{Types.VARCHAR, Types.VARCHAR}, Boolean.class);
        var ret = new JSONArray(new Object[]{exist});
        if(Boolean.FALSE.equals(exist))
            return ret;

        if(Objects.equals(dao.fetchObject("SELECT r.nazev " +
                        "FROM UZIVATEL u " +
                        "join role r on u.id_role = r.id_role " +
                        "WHERE login='Igor' AND heslo = 'Igorovic' ",
                new Object[]{name, password},
                new int[]{Types.VARCHAR, Types.VARCHAR}, String.class), "admin") && anotherUser != null){
            ret.putAll(dao.fetchJsonArray(
                    "select u.id_uzivatele, r.id_role, u.login, u.heslo, r.nazev nazev_role from uzivatel u join role r on u.id_role = r.id_role WHERE login=?",
                    new Object[]{anotherUser}, new int[]{Types.VARCHAR}));
        }else{
            ret.putAll(dao.fetchJsonArray(
                    "select u.id_uzivatele, r.id_role, u.login, u.heslo, r.nazev nazev_role from uzivatel u join role r on u.id_role = r.id_role WHERE login=? AND heslo = ?",
                    new Object[]{name, password}, new int[]{Types.VARCHAR, Types.VARCHAR}));

        }






        return ret;

    }


}

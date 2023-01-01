package com.bdas2.app.repository;

import com.bdas2.app.dao.Dao;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Types;

@Repository
public class LoginRepository {

    final Dao dao;

    public LoginRepository(Dao dao) {
        this.dao = dao;
    }

    public JSONArray userExist(String name, String password){
        var exist =  dao.fetchObject("SELECT COUNT(*) FROM UZIVATEL WHERE login=? AND heslo = ?", new Object[]{name, password}, new int[]{Types.VARCHAR, Types.VARCHAR}, Boolean.class);
        var ret = new JSONArray(new Object[]{exist});
        if(Boolean.FALSE.equals(exist))
            return ret;
        var add_data =dao.fetchJsonArray(
                "select u.id_uzivatele, r.id_role, u.login, u.heslo, r.nazev nazev_role from uzivatel u join role r on u.id_role = r.id_role WHERE login=? AND heslo = ?",
                new Object[]{name, password}, new int[]{Types.VARCHAR, Types.VARCHAR});
        ret.putAll(add_data);
        return ret;

    }


}

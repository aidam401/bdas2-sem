package com.bdas2.app.repository;

import com.bdas2.app.dao.Dao;
import org.json.JSONArray;
import org.springframework.stereotype.Repository;

import java.sql.Types;

@Repository
public class ViewRepository {
    final Dao dao;

    public ViewRepository(Dao dao) {
        this.dao = dao;
    }


    public JSONArray fetchAllUsers(String limit, Integer offset){
        return dao.fetchJsonArray("select * from UZIVATELE_ADMIN_VIEW OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;", new Object[]{offset, limit}, new int[]{Types.INTEGER, Types.INTEGER} );
    }




}

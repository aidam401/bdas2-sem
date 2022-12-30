package com.bdas2.app.repository;

import com.bdas2.app.dao.Dao;
import org.json.JSONArray;
import org.springframework.stereotype.Repository;

import java.sql.Types;

@Repository
public class CrudRepository {

    public final Dao dao;

    public CrudRepository(Dao dao) {
        this.dao = dao;
    }


    public JSONArray fetchAll(String tableName){
        return dao.fetchJsonArray("SELECT * FROM " + tableName);
    }

    public JSONArray fetchAll(String tableName, Integer limit, Integer offset){
        return dao.fetchJsonArray("SELECT * FROM "+tableName+" order by id_uzivatele OFFSET ? ROWS FETCH NEXT ? ROWS ONLY", new Object[]{offset, limit}, new int[]{Types.INTEGER, Types.INTEGER} );
    }
    public Integer fetchCount(String tableName){
        return dao.fetchObject("SELECT COUNT(*) FROM "+tableName, Integer.class);
    }
    public JSONArray fetchDetail(String tableName, Integer idVal){
        var sql = "SELECT cols.column_name FROM all_constraints cons, all_cons_columns cols " +
                "WHERE cols.table_name = ? " +
                "AND cons.constraint_type = 'P' " +
                "AND cons.constraint_name = cols.constraint_name " +
                "AND cons.owner = cols.owner ORDER BY cols.table_name, cols.position";
        var primary_key = dao.fetchObject(sql, new Object[]{tableName.toUpperCase()}, new int[]{Types.VARCHAR}, String.class);
        return dao.fetchJsonArray("SELECT * FROM "+tableName+" where "+primary_key+" = ?", new Object[]{idVal}, new int[]{Types.INTEGER});
    }
}

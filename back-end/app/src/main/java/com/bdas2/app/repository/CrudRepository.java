package com.bdas2.app.repository;

import com.bdas2.app.dao.Dao;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.sql.Types;
import java.util.*;

@Repository
public class CrudRepository {

    public final Dao dao;

    public CrudRepository(Dao dao) {
        this.dao = dao;
    }


    public JSONArray fetchAll(String tableName) {
        return dao.fetchJsonArray("SELECT * FROM " + tableName);
    }

    public JSONArray fetchAll(String tableName, Integer limit, Integer offset, String query) {

        var sql = "SELECT * FROM " + tableName;

        List<Object> args = new ArrayList<>();
        List<Integer> types = new ArrayList<>();

        if (limit != -1 && offset != -1) {
            sql += " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
            args.add(offset);
            types.add(Types.INTEGER);
            args.add(limit);
            types.add(Types.INTEGER);

        }

        if (query != "") {
            var col_name = dao.fetchObject("SELECT column_name FROM user_tab_columns WHERE table_name = '"+tableName.toUpperCase()+"' AND REGEXP_LIKE(column_name, '^nazev_', 'i') and ROWNUM = 1",
                    String.class
            );
            sql += " where LOWER(" + col_name + ") like ?";
            args.add("%"+query.toLowerCase()+"%");
            types.add(Types.VARCHAR);
        }
        int[] prim_types = new int[types.size()];
        for (int i = 0; i < types.size(); i++) {
            prim_types[i] = types.get(i).intValue();
        }
        return dao.fetchJsonArray(sql, args.toArray(), prim_types);
    }

    public Integer fetchCount(String tableName) {
        return dao.fetchObject("SELECT COUNT(*) FROM " + tableName, Integer.class);
    }

    public JSONArray fetchDetail(String tableName, Integer idVal) {

        return dao.fetchJsonArray("SELECT * FROM " + tableName + " where " + getPrimaryKeyName(tableName) + " = ?", new Object[]{idVal}, new int[]{Types.INTEGER});
    }

    public Boolean delete(String tableName, Integer id) {
        var sql = "DELETE FROM " + tableName + " WHERE " + getPrimaryKeyName(tableName) + " = ?";
        return dao.update(sql, new Object[]{id});
    }


    public boolean create(String tableName, JSONObject body) {
        Iterator<String> keys = body.keys();
        var colString = "(";
        var valString = "(";

        var args = new ArrayList<>();
        while (keys.hasNext()) {
            var key = keys.next();
            var val = body.get(key);
            args.add(val);

            colString += key;
            valString += "?";

            if (keys.hasNext()) {
                colString += ", ";
                valString += ", ";
            }
        }
        colString += ")";
        valString += ")";

        var sql = "INSERT INTO " + tableName + colString + " VALUES " + valString;
        return dao.update(sql, args.toArray());


    }

    public Boolean update(String tableName, Integer id, JSONObject body, @Nullable String idCol) {
        Iterator<String> keys = body.keys();
        var setString = "";
        var args = new ArrayList<>();


        while (keys.hasNext()) {
            var key = keys.next();
            var val = body.get(key);
            args.add(val);

            setString += key + " = ?";
            if (keys.hasNext()) {
                setString += ", ";
            }

        }
        args.add(id.intValue());

        var sql = "UPDATE " + tableName + " SET " + setString + " WHERE " + ((idCol== null) ?  getPrimaryKeyName(tableName): idCol) + " = ?";
        return dao.update(sql, args.toArray());
    }

    private String getPrimaryKeyName(String tableName) {
        var sql = "SELECT cols.column_name FROM all_constraints cons, all_cons_columns cols " +
                "WHERE cols.table_name = ? " +
                "AND cons.constraint_type = 'P' " +
                "AND cons.constraint_name = cols.constraint_name " +
                "AND cons.owner = cols.owner ORDER BY cols.table_name, cols.position";
        return dao.fetchObject(sql, new Object[]{tableName.toUpperCase()}, new int[]{Types.VARCHAR}, String.class);
    }

    public static boolean isNumeric(String str) {
        return str != null && str.matches("[-+]?\\d*\\.?\\d+");
    }


}

package com.bdas2.app.repository;

import com.bdas2.app.dao.Dao;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

@Repository
public class CrudRepository {

    public final Dao dao;

    public CrudRepository(Dao dao) {
        this.dao = dao;
    }


    public JSONArray fetchAll(String tableName) {
        return dao.fetchJsonArray("SELECT * FROM " + tableName);
    }

    public JSONArray fetchAll(String tableName, Integer limit, Integer offset) {
        return dao.fetchJsonArray("SELECT * FROM " + tableName + " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY", new Object[]{offset, limit}, new int[]{Types.INTEGER, Types.INTEGER});
    }

    public Integer fetchCount(String tableName) {
        return dao.fetchObject("SELECT COUNT(*) FROM " + tableName, Integer.class);
    }

    public JSONArray fetchDetail(String tableName, Integer idVal) {

        return dao.fetchJsonArray("SELECT * FROM " + tableName + " where " + getPrimaryKeyName(tableName) + " = ?", new Object[]{idVal}, new int[]{Types.INTEGER});
    }

    public Boolean delete(String tableName, Integer id) {
        var sql = "DELETE FROM " + tableName + " WHERE " + getPrimaryKeyName(tableName) + " = ?";
        return dao.update(sql, new Object[]{id}, new int[]{Types.INTEGER});
    }

    public boolean create(String tableName, JSONObject body) {
        Iterator<String> keys = body.keys();
        var colString = "(";
        var valString = "(";

        var args = new ArrayList<>();
        var argsTypes = new int[body.toMap().size()];
        int counter = 0;
        while (keys.hasNext()) {
            var key = keys.next();
            var val = body.get(key);
            args.add(val);
            argsTypes[counter] = (isNumeric((String) val)) ? Types.INTEGER : Types.VARCHAR;
            colString += key;
            valString += "?";

            if (keys.hasNext()) {
                colString += ", ";
                valString += ", ";
            }
            counter++;
        }
        colString += ")";
        valString += ")";

        var sql = "INSERT INTO " + tableName + colString + " VALUES " + valString;
        return dao.update(sql, args.toArray(), argsTypes);


    }

    public Boolean update(String tableName, Integer id, JSONObject body) {
        Iterator<String> keys = body.keys();
        var setString = "";
        var args = new ArrayList<>();
        var argsTypes = new int[body.toMap().size()+1];
        int counter = 0;
        while (keys.hasNext()) {
            var key = keys.next();
            var val = body.get(key);
            args.add(val);
            argsTypes[counter]=(isNumeric((String) val)) ? Types.INTEGER : Types.VARCHAR;
            setString += key + " = ?";
            if (keys.hasNext()) {
                setString += ", ";
            }
            counter++;
        }
        args.add(id.intValue());
        argsTypes[counter]=Types.INTEGER;
        var sql = "UPDATE " + tableName + " SET " + setString + " WHERE " + getPrimaryKeyName(tableName) + " = ?";
        return dao.update(sql, args.toArray(), argsTypes);
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

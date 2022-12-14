package com.bdas2.app.repository;

import com.bdas2.app.dao.Dao;
import com.google.common.collect.Iterators;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;

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

        if (query != "") {
            var col_name = dao.fetchObject("SELECT column_name FROM user_tab_columns WHERE table_name = '" + tableName.toUpperCase() + "' AND REGEXP_LIKE(column_name, '^nazev_', 'i') and ROWNUM = 1",
                    String.class
            );
            sql += " where LOWER(" + col_name + ") like ?";
            args.add("%" + query.toLowerCase() + "%");
            types.add(Types.VARCHAR);
        }
        if (limit != -1 && offset != -1) {
            sql += " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
            args.add(offset);
            types.add(Types.INTEGER);
            args.add(limit);
            types.add(Types.INTEGER);
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


    public Integer create(String tableName, JSONObject body) {
        Iterator<String> keys = body.keys();
        var colString = "(";
        var valString = "(";

        var args = new ArrayList<>();
        while (keys.hasNext()) {
            var key = keys.next();
            var val = body.get(key);

            if(Pattern.matches("\\d{4}-\\d{2}-\\d{2}", String.valueOf(val))){
                try {
                    var datek = new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(val));
                    val = new java.sql.Date(datek.getTime());
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }else if(Pattern.matches("\\d{2}:\\d{2}", String.valueOf(val))){

                LocalTime time = LocalTime.parse((String)val);
                LocalDate localDate = LocalDate.now();
                LocalDateTime localDateTime = LocalDateTime.of(localDate, time);
                val = java.sql.Timestamp.valueOf(localDateTime);


            }


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
        if (!dao.update(sql, args.toArray())) {
            throw new RuntimeException("Nastala chyba");
        }
        var prim_key = dao.fetchObject("SELECT cols.column_name FROM all_constraints cons, all_cons_columns cols WHERE cols.table_name = ? AND cons.constraint_type = 'P' AND cons.constraint_name = cols.constraint_name AND cons.owner = cols.owner ORDER BY cols.table_name, cols.position", new Object[]{tableName.toUpperCase()}, new int[]{Types.VARCHAR}, String.class);
        return dao.fetchObject("select max(" + prim_key + ") from " + tableName, Integer.class);

    }

    public Integer createUser(JSONObject jsonObject) {
        return dao.createUser((String) jsonObject.get("LOGIN"), (String) jsonObject.get("HESLO"), (Integer) jsonObject.get("ID_ROLE"), (Integer) jsonObject.get("ID_SOUBOR"));
    }

    public Boolean update(String tableName, Integer id, JSONObject body, @Nullable String idCol) {


        var argStr = "";
        Iterator<String> keys = body.keys();
        var args = new ArrayList<>();

        while (keys.hasNext()) {
            var key = keys.next();
            var val =  body.get(key);
            if(Pattern.matches("\\d{4}-\\d{2}-\\d{2}", String.valueOf(val))){
                Date datek = null;
                try {
                    datek = new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(val));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                val = new java.sql.Date(datek.getTime());
            }else if(Pattern.matches("\\d{2}:\\d{2}", String.valueOf(val))){

                LocalTime time = LocalTime.parse(String.valueOf(val));
                LocalDate localDate = LocalDate.now();
                LocalDateTime localDateTime = LocalDateTime.of(localDate, time);
                val = java.sql.Timestamp.valueOf(localDateTime);
            }

            argStr += key + " = ?";
            args.add(val);

            if(keys.hasNext())
                argStr += ", ";
            else
                argStr += " ";
        }
        args.add(id);

        var sql = "UPDATE "+tableName+" SET "+argStr+ " WHERE "+((idCol == null) ? getPrimaryKeyName(tableName) : idCol) + "= ?";
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

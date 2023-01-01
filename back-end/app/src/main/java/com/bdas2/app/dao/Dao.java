package com.bdas2.app.dao;

import com.bdas2.app.exception.CrudDaoException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
@Slf4j
public class Dao {
    final DataSource datasource;
    final JdbcTemplate template;


    public Dao(DataSource datasource, JdbcTemplate template) {
        this.datasource = datasource;
        this.template = template;

    }

    public <T> T fetchObject(@NonNull String sql, @NonNull Object[] args, @NonNull int[] argsTypes, @NonNull Class<T> type) {
        try {
            return template.queryForObject(sql, args, argsTypes, type);
        } catch (DataAccessException e) {
            throw new CrudDaoException(e.getMessage(), e);
        }
    }

    public <T> T fetchObject(@NonNull String sql, @NonNull Object[] args, @NonNull int[] argsTypes, @NonNull RowMapper<T> mapper) {
        try {
            return template.queryForObject(sql, args, argsTypes, mapper);
        } catch (DataAccessException e) {
            throw new CrudDaoException(e.getMessage(), e);
        }
    }
    public <T> T fetchObject(@NonNull String sql, @NonNull Class<T> type) {
        try {
            return template.queryForObject(sql, type);
        } catch (DataAccessException e) {
            throw new CrudDaoException(e.getMessage(), e);
        }
    }
    //TODO Integer[] na int[]
    public boolean update(@NonNull String sql, @NonNull Object[] args, @NonNull int[] argsTypes){
        return template.update(sql, args, argsTypes) == 1;
    }

    public JSONArray fetchJsonArray(@NonNull String sql) {
        try {
            JSONArray out = new JSONArray();

            template.query(sql, (rs, rowNum) -> {
                JSONObject json = new JSONObject();

                var metadata = rs.getMetaData();
                for (int i = 1; i <= metadata.getColumnCount(); i++) {
                    json.put(metadata.getColumnName(i), rs.getObject(i));
                }
                return json;
            }).forEach(out::put);
            return out;

        } catch (DataAccessException e) {
            throw new CrudDaoException(e.getMessage(), e);
        }
    }

    public JSONArray fetchJsonArray(@NonNull String sql, @NonNull Object[] args, @NonNull int[] argsTypes) {
        try {
            JSONArray out = new JSONArray();

            template.query(sql, args, argsTypes, (rs, rowNum) -> {
                JSONObject json = new JSONObject();

                var metadata = rs.getMetaData();
                for (int i = 1; i <= metadata.getColumnCount(); i++) {
                    json.put(metadata.getColumnName(i), rs.getObject(i));
                }
                return json;
            }).forEach(out::put);
            return out;

        } catch (DataAccessException e) {
            throw new CrudDaoException(e.getMessage(), e);
        }
    }

}

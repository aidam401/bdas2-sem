package com.bdas2.app.dao;

import com.bdas2.app.exception.CrudDaoException;
import com.bdas2.app.security.CustomeUserDetails;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import oracle.jdbc.internal.OracleTypes;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;

@Repository
@Slf4j
public class Dao {
    final DataSource datasource;
    final JdbcTemplate template;
    final Connection connection;

    public Dao(DataSource datasource, JdbcTemplate template, Connection connection) {
        this.datasource = datasource;
        this.template = template;
        this.connection = connection;
    }

    public <T> T fetchObject(@NonNull String sql, @NonNull Object[] args, @NonNull int[] argsTypes, @NonNull Class<T> type) {
        try {
            return template.queryForObject(sql, args, argsTypes, type);
        } catch (DataAccessException e) {
            throw new CrudDaoException(e.getMessage(), e);
        }
    }

    public CustomeUserDetails login(String login) {
        try {
            CallableStatement stmt = connection.prepareCall("{ ? = call get_login_info(?) }");

            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.setString(2, login);
            stmt.execute();
            ResultSet rs = (ResultSet) stmt.getObject(1);
            rs.next();
            var ret = new CustomeUserDetails(rs.getString(1), rs.getString(2), rs.getString(3));
            rs.close();
            stmt.close();
            return ret;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }




    public <T> T fetchObject(@NonNull String sql, @NonNull Class<T> type) {
        try {
            return template.queryForObject(sql, type);
        } catch (DataAccessException e) {
            throw new CrudDaoException(e.getMessage(), e);
        }
    }

    public boolean update(@NonNull String sql, @NonNull Object[] args){
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                log.info(args[i].toString());
                stmt.setObject(i+1, args[i]);
            }
            var ex = !stmt.execute();
            stmt.close();
            return ex;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }


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

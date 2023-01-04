package com.bdas2.app.dao;

import com.bdas2.app.exception.DaoException;


import com.bdas2.app.security.CustomeUserDetails;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.lang.Nullable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
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

    public <T> List<T> readAsList(@NonNull String sql, @NonNull Object[] args, RowMapper<T> mapper) {
        try {
            return template.query(sql, args, mapper);
        } catch (Exception ex) {
            throw new DaoException("Chyba na dao vrstvě (readAsList): " + ex.getMessage(), ex.getCause());
        }
    }

    public <T> T readAsObject(@NonNull String sql, @NonNull Object[] args, RowMapper<T> mapper) {
        try {
            var res = template.query(sql, args, mapper);
            if (res.isEmpty()) {
                return null;
            }
            return res.get(0);
        } catch (Exception ex) {
            throw new DaoException("Chyba na dao vrstvě (readAsList): " + ex.getMessage(), ex.getCause());
        }
    }

    public boolean update(@NonNull String sql, @NonNull Object[] args) {
        try {
            return template.update(sql, args) != 0;
        } catch (Exception ex) {
            throw new DaoException("Chyba na dao vrstvě (update): " + ex.getMessage(), ex.getCause());
        }
    }

    public boolean delete(@NonNull String sql, @NonNull Object[] args) {
        try {
            return template.update(sql, args) != 0;
        } catch (Exception ex) {
            throw new DaoException("Chyba na dao vrstvě (update): " + ex.getMessage(), ex.getCause());
        }

    }

    public Integer insertProcedure(@NonNull String insertSql,
                                   @NonNull String selectSql) {
        try {
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("insert_sql", insertSql);
            parameters.put("select_sql", selectSql);

            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(template)
                    .withProcedureName("insert_into_table")
                    .declareParameters(
                            new SqlParameter("insert_sql", Types.VARCHAR),
                            new SqlParameter("select_sql", Types.VARCHAR),
                            new SqlOutParameter("last_primary_key", Types.NUMERIC));

            Map<String, Object> result = jdbcCall.execute(parameters);
            return ((BigDecimal) result.get("last_primary_key")).intValue();
        } catch (Exception ex) {
            throw new DaoException("Chyba na dao vrstvě (update): " + ex.getMessage(), ex.getCause());
        }
    }

    public UserDetails getUserDetail(@NonNull String login) {
        try {
            String heslo;
            Integer idRole;

            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(template)
                    .withFunctionName("get_user_detail")
                    .declareParameters(
                            new SqlOutParameter("h_heslo", Types.VARCHAR),
                            new SqlOutParameter("h_id_role", Types.NUMERIC),
                            new SqlOutParameter("return", Types.NUMERIC)
                    ).returningResultSet("result", new RowMapper<Object>() {
                        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                            return rs;
                        }
                    });
            SqlParameterSource in = new MapSqlParameterSource()
                    .addValue("h_login", login)
                    .addValue("h_heslo", Types.VARCHAR)
                    .addValue("h_id_role", Types.NUMERIC);


            Map<String, Object> result = jdbcCall.execute(in);
            heslo = (String) result.get("h_heslo");
            idRole = ((BigDecimal) result.get("h_id_role")).intValue();
            return new CustomeUserDetails(login, heslo, idRole);
        } catch (Exception ex) {
            throw new DaoException("Chyba na dao vrstvě (update): " + ex.getMessage(), ex.getCause());
        }
    }

    public String getLogin(@NonNull String userName, @NonNull String userPassword, @Nullable String anotherUser) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("user_name", userName);
            params.put("user_password", userPassword);
            params.put("another_user", anotherUser);
            SimpleJdbcCall call = new SimpleJdbcCall(template)
                    .withProcedureName("user_exist")
                    .declareParameters(
                            new SqlParameter("user_name", Types.VARCHAR),
                            new SqlParameter("user_password", Types.VARCHAR),
                            new SqlParameter("another_user", Types.VARCHAR),
                            new SqlOutParameter("ret", Types.VARCHAR)
                    );
            Map<String, Object> result = call.execute(params);
            return (String) result.get("ret");
        } catch (Exception ex) {
            throw new DaoException("Chyba na dao vrstvě (update): " + ex.getMessage(), ex.getCause());
        }
    }


}

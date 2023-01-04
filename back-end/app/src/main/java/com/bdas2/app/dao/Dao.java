package com.bdas2.app.dao;

import com.bdas2.app.exception.DaoException;


import com.bdas2.app.security.CustomeUserDetails;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
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

//    public void insert(@NonNull String sql, @NonNull Object[] args, @NonNull int[] argTypes) {
//        try {
//            template.update(sql, args, argTypes);
//        } catch (Exception ex) {
//            throw new DaoException("Chyba na dao vrstvě (insert): " + ex.getMessage(), ex.getCause());
//        }
//    }

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

    public UserDetails getUserDetail(String login) {

        String heslo;
        Integer idRole;
        Integer status;

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
    }


//    public <T> T fetchObject(@NonNull String sql, @NonNull Object[] args, @NonNull int[] argsTypes, @NonNull Class<T> type) {
//        try {
//            return template.queryForObject(sql, args, argsTypes, type);
//        } catch (DataAccessException e) {
//            throw new CrudDaoException(e.getMessage(), e);
//        }
//    }
//
//    public CustomeUserDetails login(String login) {
//        try {
//            CallableStatement stmt = connection.prepareCall("{ ? = call get_login_info(?) }");
//
//            stmt.registerOutParameter(1, OracleTypes.CURSOR);
//            stmt.setString(2, login);
//            stmt.execute();
//            ResultSet rs = (ResultSet) stmt.getObject(1);
//            rs.next();
//            var ret = new CustomeUserDetails(rs.getString(1), rs.getString(2), rs.getInt(3));
//            rs.close();
//            stmt.close();
//
//            return ret;
//        }catch (SQLException e){
//            throw new RuntimeException(e);
//        }
//    }


//    public Integer createUser(String login, String password, Integer file_id, Integer role_id) {
//
//        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(template)
//                .withProcedureName("add_user")
//                .declareParameters(
//                        new SqlParameter("login", Types.VARCHAR),
//                        new SqlParameter("password", Types.VARCHAR),
//                        new SqlParameter("file_id", Types.NUMERIC),
//                        new SqlParameter("role_id", Types.NUMERIC),
//                        new SqlOutParameter("new_id", Types.NUMERIC));
//        Map<String, Object> parameters = new HashMap<>();
//        parameters.put("login", login);
//        parameters.put("password", password);
//        parameters.put("file_id", file_id);
//        parameters.put("role_id", role_id);
//        Map<String, Object> result = jdbcCall.execute(parameters);
//        return (Integer) result.get("new_id");
//    }
//
//    public <T> T fetchObject(@NonNull String sql, @NonNull Class<T> type) {
//        try {
//            return template.queryForObject(sql, type);
//        } catch (DataAccessException e) {
//            throw new CrudDaoException(e.getMessage(), e);
//        }
//    }
//
//    public boolean update(@NonNull String sql, @NonNull Object[] args){
//        try {
//            PreparedStatement stmt = connection.prepareStatement(sql);
//            for (int i = 0; i < args.length; i++) {
//                log.info(args[i].toString());
//                stmt.setObject(i+1, args[i]);
//            }
//            var ex = !stmt.execute();
//            stmt.close();
//            return ex;
//        }catch (SQLException e){
//            throw new RuntimeException(e);
//        }
//    }
//
//    public boolean call(@NonNull String sql,@NonNull Object[] args){
//        try {
//            PreparedStatement stmt = connection.prepareCall(sql);
//            for (int i = 0; i < args.length; i++)
//                stmt.setObject(i+1, args[i]);
//            var ex = stmt.executeUpdate() > 0;
//            stmt.close();
//            return ex;
//        }catch (SQLException e){
//            throw new RuntimeException(e);
//        }
//    }
//
//    public JSONArray fetchJsonArray(@NonNull String sql) {
//        try {
//            JSONArray out = new JSONArray();
//
//            template.query(sql, (rs, rowNum) -> {
//                JSONObject json = new JSONObject();
//
//                var metadata = rs.getMetaData();
//                for (int i = 1; i <= metadata.getColumnCount(); i++) {
//                    json.put(metadata.getColumnName(i), rs.getObject(i));
//                }
//                return json;
//            }).forEach(out::put);
//            return out;
//
//        } catch (DataAccessException e) {
//            throw new CrudDaoException(e.getMessage(), e);
//        }
//    }
//
//    public JSONArray fetchJsonArray(@NonNull String sql, @NonNull Object[] args, @NonNull int[] argsTypes) {
//        try {
//            JSONArray out = new JSONArray();
//
//            template.query(sql, args, argsTypes, (rs, rowNum) -> {
//                JSONObject json = new JSONObject();
//
//                var metadata = rs.getMetaData();
//                for (int i = 1; i <= metadata.getColumnCount(); i++) {
//                    json.put(metadata.getColumnName(i), rs.getObject(i));
//                }
//                return json;
//            }).forEach(out::put);
//            return out;
//
//        } catch (DataAccessException e) {
//            throw new CrudDaoException(e.getMessage(), e);
//        }
//    }


}

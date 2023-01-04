package com.bdas2.app.repository.modelRepositories;

import com.bdas2.app.dao.Dao;
import com.bdas2.app.exception.RepositoryException;
import org.springframework.core.GenericTypeResolver;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

@Repository
public abstract class BaseCrudRepository<T> implements CrudRepository<T>{
    public final Class<T> genericType;
    public final String tableName;
    public final String primaryKeyName;
    final Dao dao;


    public BaseCrudRepository(Dao dao) {
        this.genericType = (Class<T>) GenericTypeResolver.resolveTypeArgument(getClass(), BaseCrudRepository.class);
        this.tableName = genericType.getSimpleName().toUpperCase();
        this.primaryKeyName = "ID_" + genericType.getSimpleName().toUpperCase();
        this.dao = dao;
    }


    public Integer create(T model) {
        try {
            Field fields[] = genericType.getDeclaredFields();
            String sql = "INSERT INTO " + tableName + "(";
            String valSql = "";
            for (int i = 0; i < fields.length; i++) {
                var fieldName = fields[i].getName().toUpperCase();
                if (!fieldName.equals(primaryKeyName)) {
                    sql += fieldName;

                    if (fields[i].getType() == String.class)
                        valSql += "'" + fields[i].get(model).toString() + "'";
                    else if (fields[i].getType() == Integer.class)
                        valSql += fields[i].get(model).toString();
                    else if (fields[i].getType() == Date.class)
                        valSql += "TO_DATE('" + fields[i].get(model).toString() + "', 'YYYY-MM-DD')";

                    if (i + 1 != fields.length) {
                        sql += ", ";
                        valSql += ", ";
                    }
                }
            }
            sql += ") VALUES ("+valSql+")";

            return dao.insertProcedure(sql,"SELECT MAX("+primaryKeyName+") FROM "+tableName);
        } catch (Exception ex) {
            throw new RepositoryException("Chyba na repository vrstvě", ex);
        }
    }


    public boolean update(Integer id, T model) {
        try {
            Field fields[] = genericType.getDeclaredFields();
            Object values[] = new Object[fields.length];

            String sql = "UPDATE " + tableName + " set ";
            int counter = 0;
            for (int i = 0; i < fields.length; i++) {
                var fieldName = fields[i].getName().toUpperCase();


                if (!fieldName.equals(primaryKeyName)) {
                    //ADD TO SQL
                    sql += fields[i].getName().toUpperCase() + " = ?";
                    //ADD VALUE
                    values[counter] = fields[i].get(model);
                    counter++;
                    if (i + 1 != fields.length)
                        sql += ", ";
                }
            }
            values[fields.length - 1] = id;
            sql += " WHERE " + primaryKeyName + " = ?";


            return dao.update(sql,
                    values
            );
        } catch (Exception ex) {
            throw new RepositoryException("Chyba na repository vrstvě", ex);
        }
    }


    public boolean delete(Integer id) {
        try {
            return dao.delete(
                    "DELETE FROM " + tableName + " WHERE " + primaryKeyName + " = ?",
                    new Object[]{id}
            );
        } catch (Exception ex) {
            throw new RepositoryException("Chyba na repository vrstvě", ex);
        }
    }


    public T read(Integer id) {
        try {
            return dao.readAsObject("SELECT * FROM " + tableName + " WHERE " + primaryKeyName + " = ?",
                    new Object[]{id},
                    new BeanPropertyRowMapper<>(genericType));
        } catch (Exception ex) {
            throw new RepositoryException("Chyba na repository vrstvě", ex);
        }
    }


    public List<T> readList(Integer offset, Integer limit) {
        try {
            return dao.readAsList(
                    "SELECT * FROM " + tableName + " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY",
                    new Object[]{offset, limit},
                    new BeanPropertyRowMapper<>(genericType)
            );
        } catch (Exception ex) {
            throw new RepositoryException("Chyba na repository vrstvě", ex);
        }
    }


    public List<T> readList(Integer offset, Integer limit, String query) {

        try {
            var col_name = dao.readAsObject(
                    "SELECT column_name FROM user_tab_columns WHERE table_name = ? AND REGEXP_LIKE(column_name, '^nazev_', 'i') and ROWNUM = 1",
                    new Object[]{tableName},
                    new BeanPropertyRowMapper<>(String.class));

            return dao.readAsList(
                    "SELECT * FROM " + tableName + " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY  WHERE LOWER(" + col_name + ") like ?",
                    new Object[]{offset, limit, query},
                    new BeanPropertyRowMapper<>(genericType)
            );
        } catch (Exception ex) {
            throw new RepositoryException("Chyba na repository vrstvě", ex);
        }
    }

    public Integer count(){
        try {
            return dao.readAsObject(
                    "SELECT COUNT(*) FROM " + tableName,
                    new Object[]{},
                    new BeanPropertyRowMapper<>(Integer.class)
            );
        } catch (Exception ex) {
            throw new RepositoryException("Chyba na repository vrstvě", ex);
        }
    }
}

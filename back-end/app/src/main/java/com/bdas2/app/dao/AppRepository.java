package com.bdas2.app.dao;

import com.bdas2.app.dto.interfaces.IDatabased;
import com.bdas2.app.exception.AppRepositoryException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Repository
@Slf4j
public class AppRepository {
    final DataSource datasource;
    final JdbcTemplate template;

    public AppRepository(DataSource datasource) {
        this.datasource = datasource;
        this.template = new JdbcTemplate(datasource);
    }

    public <T extends IDatabased<T>> List<T> fetchList(String sql, Class<T> targetClass) {
        try {
            return template.query(sql, (rs, rowNum) -> {
                T output = null;
                try {
                    output = targetClass.getDeclaredConstructor().newInstance().fetchMe(rs);
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                         NoSuchMethodException e) {
                    log.error("Non possible exception ", e);
                }catch (Exception e){
                    log.error("Exception in row set ", e);
                }
                return output;
            });
        } catch (Exception e) {
            log.error("Exception in dao layer", e);
            throw new AppRepositoryException("Exception in dao layer", e);
        }
    }
}

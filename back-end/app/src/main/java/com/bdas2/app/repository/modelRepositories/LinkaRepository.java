package com.bdas2.app.repository.modelRepositories;

import com.bdas2.app.dao.Dao;
import com.bdas2.app.exception.RepositoryException;
import com.bdas2.app.model.Linka;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;
@Repository
public class LinkaRepository extends BaseCrudRepository<Linka>{

    @Autowired
    protected LinkaRepository(Dao dao) {
        super(dao);
    }
}

package com.bdas2.app.repository.modelRepositories;

import com.bdas2.app.dao.Dao;
import com.bdas2.app.model.Linka;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class LinkaRepository extends BaseCrudRepository<Linka>{

    @Autowired
    public LinkaRepository(Dao dao) {
        super(dao);
    }
}
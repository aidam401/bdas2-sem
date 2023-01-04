package com.bdas2.app.repository.modelRepositories;

import com.bdas2.app.dao.Dao;
import com.bdas2.app.model.Autobus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AutobusRepository extends BaseCrudRepository<Autobus> {
    @Autowired
    public AutobusRepository(Dao dao) {
        super(dao);
    }
}

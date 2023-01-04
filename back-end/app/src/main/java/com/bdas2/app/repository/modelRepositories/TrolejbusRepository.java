package com.bdas2.app.repository.modelRepositories;

import com.bdas2.app.dao.Dao;
import com.bdas2.app.model.Trolejbus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TrolejbusRepository extends BaseCrudRepository<Trolejbus> {

    @Autowired
    public TrolejbusRepository(Dao dao) {
        super(dao);
    }
}

package com.bdas2.app.repository.modelRepositories;

import com.bdas2.app.dao.Dao;
import com.bdas2.app.model.Spoj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SpojRepository extends BaseCrudRepository<Spoj>{

    @Autowired
    protected SpojRepository(Dao dao) {
        super(dao);
    }
}

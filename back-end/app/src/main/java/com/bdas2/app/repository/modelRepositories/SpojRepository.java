package com.bdas2.app.repository.modelRepositories;

import com.bdas2.app.dao.Dao;
import com.bdas2.app.model.Spoj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SpojRepository extends BaseCrudRepository<Spoj>{

    @Autowired
    public SpojRepository(Dao dao) {
        super(dao);
    }
}

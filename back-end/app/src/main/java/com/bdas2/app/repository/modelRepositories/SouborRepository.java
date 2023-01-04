package com.bdas2.app.repository.modelRepositories;

import com.bdas2.app.dao.Dao;
import com.bdas2.app.model.Soubor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SouborRepository extends BaseCrudRepository<Soubor>{

    @Autowired
    public SouborRepository(Dao dao) {
        super(dao);
    }
}

package com.bdas2.app.repository.modelRepositories;

import com.bdas2.app.dao.Dao;
import com.bdas2.app.model.Aktualni_Jizda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AktualniJizdaRepository extends BaseCrudRepository<Aktualni_Jizda>{

    @Autowired
    protected AktualniJizdaRepository(Dao dao) {
        super(dao);
    }
}

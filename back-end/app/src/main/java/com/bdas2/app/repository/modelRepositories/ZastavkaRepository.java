package com.bdas2.app.repository.modelRepositories;

import com.bdas2.app.dao.Dao;
import com.bdas2.app.model.Zastavka;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ZastavkaRepository extends BaseCrudRepository<Zastavka>{

    @Autowired
    protected ZastavkaRepository(Dao dao) {
        super(dao);
    }
}

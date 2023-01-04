package com.bdas2.app.repository.modelRepositories;

import com.bdas2.app.dao.Dao;
import com.bdas2.app.model.Zastavka_Linka;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ZastavkaLinkaRepository extends BaseCrudRepository<Zastavka_Linka>{

    @Autowired
    protected ZastavkaLinkaRepository(Dao dao) {
        super(dao);
    }
}

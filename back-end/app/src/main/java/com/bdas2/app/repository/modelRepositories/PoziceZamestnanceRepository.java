package com.bdas2.app.repository.modelRepositories;

import com.bdas2.app.dao.Dao;
import com.bdas2.app.model.Pozice_Zamestnance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PoziceZamestnanceRepository extends BaseCrudRepository<Pozice_Zamestnance> {

    @Autowired
    public PoziceZamestnanceRepository(Dao dao) {
        super(dao);
    }
}

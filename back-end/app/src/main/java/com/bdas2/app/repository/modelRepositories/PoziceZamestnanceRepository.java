package com.bdas2.app.repository.modelRepositories;

import com.bdas2.app.dao.Dao;
import com.bdas2.app.model.Pozice_Zamestnance;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PoziceZamestnanceRepository extends BaseCrudRepository<Pozice_Zamestnance> {

    @Autowired
    protected PoziceZamestnanceRepository(Dao dao) {
        super(dao);
    }
}

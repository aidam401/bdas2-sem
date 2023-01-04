package com.bdas2.app.repository.modelRepositories;

import com.bdas2.app.dao.Dao;
import com.bdas2.app.model.Uzivatel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UzivatelRepository extends BaseCrudRepository<Uzivatel> {

    @Autowired
    public UzivatelRepository(Dao dao) {
        super(dao);
    }
}

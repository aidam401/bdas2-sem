package com.bdas2.app.repository.modelRepositories;

import com.bdas2.app.dao.Dao;
import com.bdas2.app.model.Uzivatel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UzivatelRepository extends BaseCrudRepository<Uzivatel> {

    @Autowired
    protected UzivatelRepository(Dao dao) {
        super(dao);
    }
}

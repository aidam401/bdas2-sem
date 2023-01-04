package com.bdas2.app.repository.modelRepositories;

import com.bdas2.app.dao.Dao;
import com.bdas2.app.model.Vozidlo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VozidloRepository extends BaseCrudRepository<Vozidlo> {

    @Autowired
    protected VozidloRepository(Dao dao) {
        super(dao);
    }
}

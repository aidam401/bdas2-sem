package com.bdas2.app.repository.modelRepositories;

import com.bdas2.app.dao.Dao;

import com.bdas2.app.model.Jizdni_Rad;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Repository;

@Repository
public class JizdniRadRepository extends BaseCrudRepository<Jizdni_Rad> {
    @Autowired
    protected JizdniRadRepository(Dao dao) {
        super(dao);
    }
}
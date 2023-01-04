package com.bdas2.app.repository.modelRepositories;

import com.bdas2.app.dao.Dao;
import com.bdas2.app.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoleRepository extends BaseCrudRepository<Role>{

    @Autowired
    protected RoleRepository(Dao dao) {
        super(dao);
    }
}

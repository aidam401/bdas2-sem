package com.bdas2.app.repository.modelRepositories;

import com.bdas2.app.dao.Dao;
import com.bdas2.app.model.Changelog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ChangelogRepository extends BaseCrudRepository<Changelog> {
    @Autowired
    public ChangelogRepository(Dao dao) {
        super(dao);
    }
}

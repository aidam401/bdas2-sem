package com.bdas2.app.repository.modelRepositories;

import com.bdas2.app.dao.Dao;
import com.bdas2.app.model.Zamestnanec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ZamestnanecRepository extends BaseCrudRepository<Zamestnanec>{
    @Autowired
    public ZamestnanecRepository(Dao dao) {
        super(dao);
    }

}

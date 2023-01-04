package com.bdas2.app.repository.modelRepositories;

import com.bdas2.app.dao.Dao;
import com.bdas2.app.model.Zamestnanec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ZamestnanecRepository extends BaseCrudRepository<Zamestnanec>{
    @Autowired
    protected ZamestnanecRepository(Dao dao) {
        super(dao);
    }

}

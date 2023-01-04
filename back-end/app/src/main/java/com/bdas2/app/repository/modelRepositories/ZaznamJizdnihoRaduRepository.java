package com.bdas2.app.repository.modelRepositories;

import com.bdas2.app.dao.Dao;
import com.bdas2.app.model.Zaznam_Jizdniho_Radu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ZaznamJizdnihoRaduRepository extends BaseCrudRepository<Zaznam_Jizdniho_Radu>{

    @Autowired
    public ZaznamJizdnihoRaduRepository(Dao dao) {
        super(dao);
    }
}

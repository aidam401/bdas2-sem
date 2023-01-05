package com.bdas2.app.repository;

import com.bdas2.app.dao.Dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

@Repository
public class DateRepository {
    final Dao dao;

    public DateRepository(Dao dao) {
        this.dao = dao;
    }



}

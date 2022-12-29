package com.bdas2.app.config;

import com.bdas2.app.dao.Dao;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Runner implements CommandLineRunner{
    final Dao dao;

    public Runner(Dao dao) {
        this.dao = dao;
    }

    @Override
    public void run(String... args) throws Exception {
        dao.fetchJsonArray("SELECT * FROM ADRESA");
    }
}

package com.bdas2.app.repository.modelRepositories;

import com.bdas2.app.model.Autobus;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface CrudRepository<T> {
    Integer create(T model);



    boolean update(Integer id, T model);
    T read(Integer id);
    List<T> readList(Integer offset, Integer limit);
    List<T> readList(Integer offset, Integer limit, String query);


}

package com.bdas2.app.repository.modelRepositories;

import com.bdas2.app.dao.Dao;
import com.bdas2.app.exception.RepositoryException;
import lombok.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public class LoginRepository {
    final Dao dao;

    public LoginRepository(Dao dao) {
        this.dao = dao;
    }

    public String userExist(@NonNull String name, @NonNull String password, String anotherUser) {
        try {
            return dao.getLogin(name, password, anotherUser);
        } catch (Exception ex) {
            throw new RepositoryException("Chyba na repository vrstvě " + ex.getMessage(), ex);
        }

    }
}

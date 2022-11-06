package com.bdas2.app.service;

import com.bdas2.app.dao.AppRepository;
import com.bdas2.app.dto.User;
import com.bdas2.app.exception.AppRepositoryException;
import com.bdas2.app.exception.UserServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.bdas2.app.dao.queries.queries.SELECT_ALL_USERS;

@Service
@Slf4j
public class UserService {
    final AppRepository appRepository;

    public UserService(AppRepository appRepository) {
        this.appRepository = appRepository;
    }

    public List<User> getAllUsers(){
        try {
            return appRepository.fetchList(SELECT_ALL_USERS, User.class);
        }catch (AppRepositoryException e){
            throw new UserServiceException(e);
        }catch (Exception e){
            log.error("Chyba v user service vrstvě", e);
            throw new UserServiceException(e);
        }
    }

}

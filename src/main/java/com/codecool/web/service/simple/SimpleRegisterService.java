package com.codecool.web.service.simple;

import com.codecool.web.dao.UserDao;
import com.codecool.web.service.RegisterService;
import com.codecool.web.service.exception.ServiceException;

import java.sql.SQLException;

public class SimpleRegisterService implements RegisterService {

    private final UserDao userDao;

    public SimpleRegisterService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void registerUser(String email, String name) throws SQLException, ServiceException {
        try {
            userDao.addUser(email, name);
        } catch (IllegalArgumentException ex) {
            throw new ServiceException(ex.getMessage());
        }
    }
}

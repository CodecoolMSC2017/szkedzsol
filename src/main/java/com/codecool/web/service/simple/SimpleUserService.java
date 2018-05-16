package com.codecool.web.service.simple;

import com.codecool.web.dao.UserDao;
import com.codecool.web.model.Role;
import com.codecool.web.model.User;
import com.codecool.web.service.UserService;
import com.codecool.web.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

public class SimpleUserService implements UserService {

    private final UserDao userDao;

    public SimpleUserService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<User> getAllUsers() throws SQLException, ServiceException {
        try {
            return userDao.findAll();
        } catch (IllegalArgumentException ex) {
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public boolean isAdmin(User tmpUser) {
        if(tmpUser.getRole().equals(Role.ADMIN)){
            return true;
        }else {
            return false;
        }
    }
}

package com.codecool.web.service.simple;

import com.codecool.web.dao.UserDao;
import com.codecool.web.model.Role;
import com.codecool.web.model.User;
import com.codecool.web.service.UserService;
import com.codecool.web.service.exception.ServiceException;
import com.codecool.web.service.exception.UserException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

public class SimpleUserService implements UserService {

    private final UserDao userDao;

    final Logger logger = Logger.getLogger(SimpleUserService.class);

    public SimpleUserService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<User> getAllUsers() throws SQLException {
        try {
            return userDao.findAll();
        } catch (SQLException e) {
            logger.error("SQL ERROR"+e.getMessage());
        }
        return null;
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

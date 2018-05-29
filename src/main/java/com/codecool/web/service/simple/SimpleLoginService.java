package com.codecool.web.service.simple;

import com.codecool.web.dao.UserDao;
import com.codecool.web.model.User;
import com.codecool.web.service.LoginService;
import com.codecool.web.service.exception.LogingException;
import org.apache.log4j.Logger;

import java.sql.SQLException;

public final class SimpleLoginService implements LoginService {

    private final UserDao userDao;
    final Logger logger = Logger.getLogger(SimpleLoginService.class);

    public SimpleLoginService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User loginUser(String email, String name) throws SQLException, LogingException {
            logger.info("loginUSer METHOD CALLED");
        try {
            User user = userDao.findByEmail(email);
            if (user == null || !user.getEmail().equals(email) || !name.equals(user.getName())) {
                logger.warn("Error while login");
                throw new LogingException("User name or email fields empty");

            }
            logger.debug(user.getName()+"Logged in");
            return user;
        } catch (IllegalArgumentException ex) {
            logger.error("Argument missmatch "+ex.getMessage());
        }
        return null;
    }
}

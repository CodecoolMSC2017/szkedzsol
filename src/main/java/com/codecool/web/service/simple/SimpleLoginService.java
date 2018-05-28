package com.codecool.web.service.simple;

import com.codecool.web.dao.UserDao;
import com.codecool.web.model.User;
import com.codecool.web.service.LoginService;
import com.codecool.web.service.exception.LogingException;
import com.codecool.web.service.exception.ServiceException;
import javafx.fxml.LoadException;
import org.apache.log4j.Logger;

import javax.sql.rowset.serial.SerialException;
import java.sql.SQLException;

public final class SimpleLoginService implements LoginService {

    private final UserDao userDao;
    final Logger logger = Logger.getLogger(SimpleLoginService.class);

    public SimpleLoginService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User loginUser(String email, String name) throws SQLException, LogingException {
        try {
            User user = userDao.findByEmail(email);
            if (user == null || !user.getEmail().equals(email) || !name.equals(user.getName())) {
                logger.error("Error while login");
                throw new LogingException("User name or email fields empty");

            }
            logger.info(user.getName()+"Logged in");
            return user;
        } catch (IllegalArgumentException ex) {
            logger.error("Error while login in"+ex.getMessage());

        }
        return null;
    }
}

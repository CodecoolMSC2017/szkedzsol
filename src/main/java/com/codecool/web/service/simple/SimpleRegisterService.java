package com.codecool.web.service.simple;

import com.codecool.web.dao.UserDao;
import com.codecool.web.service.RegisterService;
import com.codecool.web.service.exception.RegisterException;
import com.codecool.web.service.exception.ServiceException;
import org.apache.log4j.Logger;

import java.sql.SQLException;

public class SimpleRegisterService implements RegisterService {
    final static Logger logger = Logger.getLogger(SimpleRegisterService.class);

    private final UserDao userDao;

    public SimpleRegisterService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void registerUser(String email, String name) throws SQLException, ServiceException {
        try {
            if(email == null || email.equals("") || name == null || name.equals("")) {
                throw new RegisterException("email or name is null or empty");
            }

            userDao.addUser(email, name);
            logger.info(name+"'d user has been added to the database");
        } catch (RegisterException ex) {
            logger.warn("REGISTER ERROR "+ex.getMessage());
        }
    }
}

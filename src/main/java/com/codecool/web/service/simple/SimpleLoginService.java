package com.codecool.web.service.simple;

import com.codecool.web.dao.UserDao;
import com.codecool.web.model.User;
import com.codecool.web.service.LoginService;
import com.codecool.web.service.exception.LogingException;
import com.codecool.web.service.exception.ServiceException;
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

    @Override
    public User loginGoogleUser(String email, String name) throws LogingException, SQLException {
        User user;
        try {
            user = userDao.findByEmail(email);
            if(userDao.findByEmail(email)==null){
            SimpleRegisterService simpleRegisterService = new SimpleRegisterService(userDao);
            simpleRegisterService.registerUser(email,name);
                userDao.findByEmail(email);
                return user;
            }
            else {
                user = userDao.findByEmail(email);
                userDao.findByEmail(email);
                return user;
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }
         return null;

    }


}

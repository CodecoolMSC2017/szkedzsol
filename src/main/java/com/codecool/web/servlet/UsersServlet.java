package com.codecool.web.servlet;

import com.codecool.web.dao.ScheduleDao;
import com.codecool.web.dao.UserDao;
import com.codecool.web.dao.database.DatabaseScheduleDao;
import com.codecool.web.dao.database.DatabaseUserDao;
import com.codecool.web.model.User;
import com.codecool.web.service.ScheduleService;
import com.codecool.web.service.UserService;
import com.codecool.web.service.exception.ScheduleException;
import com.codecool.web.service.exception.ServiceException;
import com.codecool.web.service.exception.UserException;
import com.codecool.web.service.simple.SimpleScheduleService;
import com.codecool.web.service.simple.SimpleUserService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/protected/users")
public class UsersServlet extends AbstractServlet {
    final Logger logger = Logger.getLogger(UsersServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("USERS SERVLET CALLED");
        try (Connection connection = getConnection(req.getServletContext())) {
            UserDao userDao = new DatabaseUserDao(connection);
            UserService userService = new SimpleUserService(userDao);
            User tmpUser1 = (User) req.getSession().getAttribute("user");

            if (userService.isAdmin(tmpUser1)) {
                try {
                    List<User> users = userService.getAllUsers();
                    req.setAttribute("users", users);
                    sendMessage(resp, 200, users);
                    logger.debug("User list sent by DOGET");

                } catch (IOException e) {
                    logger.warn("IOEXCEPTION "+e.getMessage());
                }
            } else {
                ScheduleDao scheduleDao = new DatabaseScheduleDao(connection);
                ScheduleService scheduleService = new SimpleScheduleService(scheduleDao);
                User tmpUser = (User) req.getSession().getAttribute("user");
                scheduleService.getSchedules(tmpUser.getId());
            }
        } catch (SQLException e) {
            logger.warn("SQL EXCEPTION IN SERVLET "+e.getMessage() );
        } catch (ScheduleException e) {
            logger.warn("SERVICE EXCEPTION "+e.getMessage());

        }
    }
}

package com.codecool.web.servlet;

import com.codecool.web.dao.UserDao;
import com.codecool.web.dao.database.DatabaseUserDao;
import com.codecool.web.model.User;
import com.codecool.web.service.LoginService;
import com.codecool.web.service.exception.LogingException;
import com.codecool.web.service.exception.ServiceException;
import com.codecool.web.service.simple.SimpleLoginService;
import org.apache.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/login")
public final class LoginServlet extends AbstractServlet {
    final Logger logger = Logger.getLogger(LoginServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("LOGIN SERVLET DO POST CALLED");
        try (Connection connection = getConnection(req.getServletContext())) {
            UserDao userDao = new DatabaseUserDao(connection);
            LoginService loginService = new SimpleLoginService(userDao);

            String email = req.getParameter("email");
            String name = req.getParameter("name");
            User user = loginService.loginUser(email, name);
            req.getSession().setAttribute("user", user);

            logger.info("Current user logged in succesfully " + user.getName());

            sendMessage(resp, HttpServletResponse.SC_OK, user);
        } catch (LogingException ex) {
            logger.warn("Login failed becasue of SERVICE EXCEPTION");
            sendMessage(resp, HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
        } catch (SQLException ex) {
            logger.error("Login failed because of sql EXCEPTION");
            handleSqlError(resp, ex);
        }
    }
}

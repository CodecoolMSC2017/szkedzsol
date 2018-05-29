package com.codecool.web.servlet;

import com.codecool.web.dao.UserDao;
import com.codecool.web.dao.database.DatabaseUserDao;
import com.codecool.web.model.User;
import com.codecool.web.service.LoginService;
import com.codecool.web.service.RegisterService;
import com.codecool.web.service.exception.ServiceException;
import com.codecool.web.service.simple.SimpleLoginService;
import com.codecool.web.service.simple.SimpleRegisterService;
import org.apache.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/register")
public class RegisterServlet extends AbstractServlet {
    final Logger logger = Logger.getLogger(RegisterServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("REGISTER SERVLET DO POST CALLED");
        try (Connection connection = getConnection(req.getServletContext())) {
            UserDao userDao = new DatabaseUserDao(connection);
            RegisterService registerService = new SimpleRegisterService(userDao);

            String email = req.getParameter("email");
            String name = req.getParameter("name");

            registerService.registerUser(email, name);
            logger.info("REGISTER SERVLET DO POST SUCCESSFULL");

        } catch (SQLException ex) {
            handleSqlError(resp, ex);
            logger.error("SQL EXCEPTION THROWN "+ex.getMessage());
        } catch (ServiceException se) {
            sendMessage(resp, HttpServletResponse.SC_UNAUTHORIZED, se.getMessage());
            logger.warn("SERVICE EXCEPTION THRWON "+se.getMessage());
        }
    }
}

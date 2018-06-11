package com.codecool.web.servlet;

import com.codecool.web.dao.ScheduleDao;
import com.codecool.web.dao.UserDao;
import com.codecool.web.dao.database.DatabaseScheduleDao;
import com.codecool.web.dao.database.DatabaseUserDao;
import com.codecool.web.model.Role;
import com.codecool.web.model.User;
import com.codecool.web.service.LoginService;
import com.codecool.web.service.exception.LogingException;
import com.codecool.web.service.simple.SimpleLoginService;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
@WebServlet("/googlelogin")
public class GoogleLoginServlet extends AbstractServlet {
    final Logger logger = Logger.getLogger(GoogleLoginServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("GoogleLoginServlet SERVLET DO POST CALLED");
        try (Connection connection = getConnection(req.getServletContext())) {
            UserDao userDao = new DatabaseUserDao(connection);
            LoginService loginService = new SimpleLoginService(userDao);
            JsonNode jsonNode = createJsonNodeFromRequest(req);
            String email = jsonNode.get("email").textValue();
            String name = jsonNode.get("name").textValue();

            loginService.loginGoogleUser(email, name);
            User user = userDao.findByEmail(email);
            req.getSession().setAttribute("user", user);

            sendMessage(resp, HttpServletResponse.SC_OK, user);
            logger.info("GoogleLoginServlet SERVLET DO POST SUCCESFULL");
        } catch (SQLException ex) {
            logger.error("SQL EXCEPTION THROWN "+ex.getMessage());
        } catch (LogingException e) {
            e.printStackTrace();
        }
    }
}

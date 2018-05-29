package com.codecool.web.servlet;

import com.codecool.web.model.User;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/protected/logout")
public final class LogoutServlet extends HttpServlet {
    final Logger logger = Logger.getLogger(LogoutServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user  =(User) req.getSession().getAttribute("user");
        logger.info("PROFILE SERVLET DO GET CALLED BY USER "+user.getName());
        req.getSession().invalidate();
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}

package com.codecool.web.servlet;

import com.codecool.web.dao.UserDao;
import com.codecool.web.dao.database.DatabaseUserDao;
import com.codecool.web.model.User;
import com.codecool.web.service.LoginService;
import com.codecool.web.service.exception.LogingException;
import com.codecool.web.service.simple.SimpleLoginService;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;


@WebServlet("/googlelogin")
public class GoogleLoginServlet extends AbstractServlet {
    final Logger logger = Logger.getLogger(GoogleLoginServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("GoogleLoginServlet SERVLET DO POST CALLED");
        try (Connection connection = getConnection(req.getServletContext())) {
            HttpTransport transport = new NetHttpTransport();
            JsonFactory jsonFactory = new JacksonFactory();

            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                // Specify the CLIENT_ID of the app that accesses the backend:
                .setAudience(Collections.singletonList("429697848140-q1h0ncjk2ejk6jk3huh3e93aoh4ef7ju.apps.googleusercontent.com"))
                // Or, if multiple clients access the backend:
                //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
                .build();

// (Receive idTokenString by HTTPS POST)

            GoogleIdToken idToken = verifier.verify(req.getParameter("idtoken"));
            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();



                // Get profile information from payload
                String email = payload.getEmail();
                String name = (String) payload.get("name");

            UserDao userDao = new DatabaseUserDao(connection);
            LoginService loginService = new SimpleLoginService(userDao);
            JsonNode jsonNode = createJsonNodeFromRequest(req);


            loginService.loginGoogleUser(email, name);
            User user = userDao.findByEmail(email);
            req.getSession().setAttribute("user", user);

            sendMessage(resp, HttpServletResponse.SC_OK, user);
            logger.info("GoogleLoginServlet SERVLET DO POST SUCCESFULL");
        }} catch (SQLException ex) {
            logger.error("SQL EXCEPTION THROWN "+ex.getMessage());
        } catch (LogingException e) {
            e.printStackTrace();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
    }
        }




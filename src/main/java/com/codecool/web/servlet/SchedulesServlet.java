package com.codecool.web.servlet;


import com.codecool.web.dao.ScheduleDao;
import com.codecool.web.dao.UserDao;
import com.codecool.web.dao.database.DatabaseScheduleDao;
import com.codecool.web.dao.database.DatabaseUserDao;
import com.codecool.web.model.Schedule;
import com.codecool.web.model.User;
import com.codecool.web.service.RegisterService;
import com.codecool.web.service.ScheduleService;
import com.codecool.web.service.exception.ScheduleException;
import com.codecool.web.service.exception.ServiceException;
import com.codecool.web.service.simple.SimpleRegisterService;
import com.codecool.web.service.simple.SimpleScheduleService;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/protected/schedules")
public class SchedulesServlet extends AbstractServlet {
    final Logger logger = Logger.getLogger(SchedulesServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("SCHEDULES SERVLET DO GET CALLED");
        try (Connection connection = getConnection(req.getServletContext())) {
            ScheduleDao scheduleDao = new DatabaseScheduleDao(connection);
            ScheduleService scheduleService = new SimpleScheduleService(scheduleDao);

            User user = (User) req.getSession().getAttribute("user");
            int userId = user.getId();

            List<Schedule> scheduleList = scheduleService.getSchedules(userId);

            req.setAttribute("user", user);
            req.setAttribute("schedules", scheduleList);
            sendMessage(resp, 200, scheduleList);
            logger.info("SCHEDULES SERVLET DO GET SUCCESFULL");
        } catch (SQLException e ){
            logger.error("SQL EXCEPTION THROWN "+e.getMessage());
        } catch (ScheduleException e) {
            logger.warn("SERVICE EXCEPTION THRWON "+e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("SCHEDULES SERVLET DO POST CALLED");

        try (Connection connection = getConnection(req.getServletContext())) {
            ScheduleDao scheduleDao = new DatabaseScheduleDao(connection);
            ScheduleService scheduleService = new SimpleScheduleService(scheduleDao);

            User user = (User) req.getSession().getAttribute("user");
            int userId = user.getId();
            String scheduleTitle = req.getParameter("scheduleTitle");

            scheduleService.addSchedule(userId, scheduleTitle);
            doGet(req, resp);
            logger.info("SCHEDULES SERVLET DO POST SUCCESFULL");
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
            logger.error("SQL EXCEPTION THROWN "+ex.getMessage());
        } catch (ServiceException se) {
            sendMessage(resp, HttpServletResponse.SC_UNAUTHORIZED, se.getMessage());
        } catch (ServletException e) {
            logger.warn("SERVICE EXCEPTION THRWON "+e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("SCHEDULES SERVLET DO DELETE CALLED");

        try (Connection connection = getConnection(req.getServletContext())) {
            ScheduleDao scheduleDao = new DatabaseScheduleDao(connection);
            ScheduleService scheduleService = new SimpleScheduleService(scheduleDao);

            JsonNode jsonNode = createJsonNodeFromRequest(req);
            int id = Integer.parseInt(jsonNode.get("scheduleId").textValue());

            scheduleService.deleteSchedule(id);

            User user = (User) req.getSession().getAttribute("user");
            int userId = user.getId();

            List<Schedule> scheduleList = scheduleService.getSchedules(userId);
            sendMessage(resp,200, scheduleList);
            logger.info("SCHEDULES SERVLET DO DELETE SUCCESFULL");
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
            logger.error("SQL EXCEPTION THROWN "+ex.getMessage());
        } catch (ServiceException se) {
            sendMessage(resp, HttpServletResponse.SC_UNAUTHORIZED, se.getMessage());
            logger.warn("SERVICE EXCEPTION THRWON "+se.getMessage());
        }
    }
}

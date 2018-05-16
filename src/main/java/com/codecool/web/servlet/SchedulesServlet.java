package com.codecool.web.servlet;


import com.codecool.web.dao.ScheduleDao;
import com.codecool.web.dao.UserDao;
import com.codecool.web.dao.database.DatabaseScheduleDao;
import com.codecool.web.dao.database.DatabaseUserDao;
import com.codecool.web.model.Schedule;
import com.codecool.web.model.User;
import com.codecool.web.service.RegisterService;
import com.codecool.web.service.ScheduleService;
import com.codecool.web.service.exception.ServiceException;
import com.codecool.web.service.simple.SimpleRegisterService;
import com.codecool.web.service.simple.SimpleScheduleService;
import com.fasterxml.jackson.databind.JsonNode;

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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            ScheduleDao scheduleDao = new DatabaseScheduleDao(connection);
            ScheduleService scheduleService = new SimpleScheduleService(scheduleDao);

            User user = (User) req.getSession().getAttribute("user");
            int userId = user.getId();

            List<Schedule> scheduleList = scheduleService.getSchedules(userId);

            req.setAttribute("user", user);
            req.setAttribute("schedules", scheduleList);
            sendMessage(resp, 200, scheduleList);
        } catch (SQLException | ServiceException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            ScheduleDao scheduleDao = new DatabaseScheduleDao(connection);
            ScheduleService scheduleService = new SimpleScheduleService(scheduleDao);

            User user = (User) req.getSession().getAttribute("user");
            int userId = user.getId();
            String scheduleTitle = req.getParameter("scheduleTitle");

            scheduleService.addSchedule(userId, scheduleTitle);
            doGet(req, resp);
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
        } catch (ServiceException se) {
            sendMessage(resp, HttpServletResponse.SC_UNAUTHORIZED, se.getMessage());
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
        } catch (ServiceException se) {
            sendMessage(resp, HttpServletResponse.SC_UNAUTHORIZED, se.getMessage());
        }
    }
}

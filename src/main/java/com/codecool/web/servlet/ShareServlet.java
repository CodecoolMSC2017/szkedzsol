package com.codecool.web.servlet;

import com.codecool.web.dao.ScheduleDao;
import com.codecool.web.dao.database.DatabaseScheduleDao;
import com.codecool.web.model.Schedule;
import com.codecool.web.service.ScheduleService;
import com.codecool.web.service.exception.ScheduleException;
import com.codecool.web.service.simple.SimpleScheduleService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/share")
public class ShareServlet extends AbstractServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            String currentId = req.getParameter("id");
            ScheduleDao scheduleDao = new DatabaseScheduleDao(connection);
            ScheduleService scheduleService = new SimpleScheduleService(scheduleDao);
            Schedule publicSchedules = scheduleService.getScheduleById(Integer.parseInt(currentId));

            req.setAttribute("publicSchedules", publicSchedules);
            sendMessage(resp, 202, publicSchedules);
        } catch (SQLException | ScheduleException e) {
            e.printStackTrace();
        }
    }
}

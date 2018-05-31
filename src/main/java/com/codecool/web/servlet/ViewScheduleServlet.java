package com.codecool.web.servlet;

import com.codecool.web.dao.ScheduleDao;
import com.codecool.web.dao.TaskDao;
import com.codecool.web.dao.database.DatabaseScheduleDao;
import com.codecool.web.dao.database.DatabaseTaskDao;
import com.codecool.web.dto.ScheduleDto;
import com.codecool.web.model.Schedule;
import com.codecool.web.model.Task;
import com.codecool.web.model.User;
import com.codecool.web.service.ScheduleService;
import com.codecool.web.service.TaskService;
import com.codecool.web.service.exception.ScheduleException;
import com.codecool.web.service.exception.ServiceException;
import com.codecool.web.service.simple.SimpleScheduleService;
import com.codecool.web.service.simple.SimpleTaskService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/protected/viewschedule")
public class ViewScheduleServlet extends AbstractServlet {
    final Logger logger = Logger.getLogger(ViewScheduleServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("VIEW SCHEDULE SERVLET DO GET CALLED");
        try (Connection connection = getConnection(req.getServletContext())) {
            ScheduleDao scheduleDao = new DatabaseScheduleDao(connection);
            ScheduleService scheduleService = new SimpleScheduleService(scheduleDao);

            String strId = req.getParameter("scheduleId");
            int scheduleId = Integer.parseInt(strId);

            List<ScheduleDto> scheduleDtoList = scheduleService.getScheduleDto(scheduleId);

            req.setAttribute("schedule", scheduleDtoList);
            sendMessage(resp, 200, scheduleDtoList);
            logger.info("SCHEDULE SERVLET DO GET SUCCESFULL");
        } catch (SQLException e) {
            logger.error("SQL EXCEPTION THROWN "+e.getMessage());
        }
    }
}

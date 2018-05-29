package com.codecool.web.servlet;

import com.codecool.web.dao.ScheduleDao;
import com.codecool.web.dao.database.DatabaseScheduleDao;
import com.codecool.web.model.Schedule;
import com.codecool.web.service.ScheduleService;
import com.codecool.web.service.exception.ScheduleException;
import com.codecool.web.service.exception.ServiceException;
import com.codecool.web.service.simple.SimpleScheduleService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/protected/schedule")
public class ScheduleServlet extends AbstractServlet {
    final Logger logger = Logger.getLogger(ScheduleServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("SCHEDULE SERVLET DO GET CALLED");
        try (Connection connection = getConnection(req.getServletContext())) {
            ScheduleDao scheduleDao = new DatabaseScheduleDao(connection);
            ScheduleService scheduleService = new SimpleScheduleService(scheduleDao);

            String strId = req.getParameter("id");
            int scheduleId = Integer.parseInt(strId);
            System.out.println(scheduleId);

            Schedule schedule = scheduleService.getScheduleById(scheduleId);
            System.out.println(schedule.getId());

            req.setAttribute("schedule", schedule);
            sendMessage(resp, 200, schedule);
            logger.info("SCHEDULE SERVLET DO GET SUCCESFULL");
        } catch (SQLException e) {
            logger.error("SQL EXCEPTION THROWN "+e.getMessage());
        } catch (ScheduleException e) {
            logger.warn("SERVICE EXCEPTION THRWON "+e.getMessage());
        }
    }
}

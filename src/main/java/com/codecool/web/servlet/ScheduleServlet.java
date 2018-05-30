package com.codecool.web.servlet;

import com.codecool.web.dao.ScheduleDao;
import com.codecool.web.dao.database.DatabaseScheduleDao;
import com.codecool.web.model.Schedule;
import com.codecool.web.model.User;
import com.codecool.web.service.ScheduleService;
import com.codecool.web.service.exception.ScheduleException;
import com.codecool.web.service.exception.ServiceException;
import com.codecool.web.service.simple.SimpleScheduleService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

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

            Schedule schedule = scheduleService.getScheduleById(scheduleId);

            req.setAttribute("schedule", schedule);
            sendMessage(resp, 200, schedule);
            logger.info("SCHEDULE SERVLET DO GET SUCCESFULL");
        } catch (SQLException e) {
            logger.error("SQL EXCEPTION THROWN "+e.getMessage());
        } catch (ScheduleException e) {
            logger.warn("SERVICE EXCEPTION THRWON "+e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("SCHEDULE SERVLET DO PUT CALLED");
        try (Connection connection = getConnection(req.getServletContext())) {
            ScheduleDao scheduleDao = new DatabaseScheduleDao(connection);
            ScheduleService scheduleService = new SimpleScheduleService(scheduleDao);

            JsonNode jsonNode = createJsonNodeFromRequest(req);
            int scheduleId = Integer.parseInt(jsonNode.get("scheduleId").textValue());
            String colName = (jsonNode.get("dayName").textValue());
            ObjectNode tasks = (ObjectNode) jsonNode.get("tasks");

            ObjectMapper mapper = new ObjectMapper();
            Map<Integer, Integer> taskMap = mapper.convertValue(tasks, new TypeReference<Map<Integer, Integer>>() {});

            scheduleService.taskToSchedule(colName, scheduleId, taskMap);

            logger.info("SCHEDULE SERVLET DO PUT SUCCESFULL");
        } catch (SQLException ex) {
            logger.error("SQL EXCEPTION THROWN "+ex.getMessage());
        }
    }
}

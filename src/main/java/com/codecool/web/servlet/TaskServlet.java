package com.codecool.web.servlet;

import com.codecool.web.dao.ScheduleDao;
import com.codecool.web.dao.TaskDao;
import com.codecool.web.dao.database.DatabaseScheduleDao;
import com.codecool.web.dao.database.DatabaseTaskDao;
import com.codecool.web.model.Schedule;
import com.codecool.web.model.Task;
import com.codecool.web.model.User;
import com.codecool.web.service.ScheduleService;
import com.codecool.web.service.TaskService;
import com.codecool.web.service.exception.ServiceException;
import com.codecool.web.service.simple.SimpleScheduleService;
import com.codecool.web.service.simple.SimpleTaskService;
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

@WebServlet("/protected/task")
public class TaskServlet extends AbstractServlet {
    final Logger logger = Logger.getLogger(TaskServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("TASK SERVLET DO GET CALLED");
        try (Connection connection = getConnection(req.getServletContext())) {
            TaskDao taskDao = new DatabaseTaskDao(connection);
            TaskService taskService = new SimpleTaskService(taskDao);

            User user = (User) req.getSession().getAttribute("user");
            int userId = user.getId();

            String strTaskId = req.getParameter("id");
            int taskId = Integer.parseInt(strTaskId);

            Task task = taskService.getTaskById(taskId, userId);

            req.setAttribute("user", user);
            req.setAttribute("task", task);
            sendMessage(resp, 200, task);
            logger.info("TASK SERVLET DO GET SUCCESFULL");
        } catch (SQLException e) {
            logger.error("SQL EXCEPTION THROWN "+e.getMessage());
        } catch (ServiceException e) {
            logger.warn("SERVICE EXCEPTION THRWON "+e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("TASKS SERVLET DO PUT CALLED");
        try (Connection connection = getConnection(req.getServletContext())) {
            TaskDao taskDao = new DatabaseTaskDao(connection);
            TaskService taskService = new SimpleTaskService(taskDao);

            User user = (User) req.getSession().getAttribute("user");
            int userId = user.getId();

            JsonNode jsonNode = createJsonNodeFromRequest(req);
            int id = Integer.parseInt(jsonNode.get("taskId").textValue());
            String name = (jsonNode.get("taskName").textValue());
            String description = (jsonNode.get("taskDescription").textValue());
            taskService.modifyTask(id,name, description);

            Task task = taskService.getTaskById(id, userId);
            sendMessage(resp,200, task);
            logger.info("TASK SERVLET DO PUT SUCCESFULL");
        } catch (SQLException ex) {
            logger.error("SQL EXCEPTION THROWN "+ex.getMessage());
        } catch (ServiceException se) {
            sendMessage(resp, HttpServletResponse.SC_UNAUTHORIZED, se.getMessage());
            logger.warn("SERVICE EXCEPTION THRWON "+se.getMessage());

        }
    }
}

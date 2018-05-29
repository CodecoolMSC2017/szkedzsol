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

@WebServlet("/protected/tasks")
public class TasksServlet extends AbstractServlet {
    final Logger logger = Logger.getLogger(TasksServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("TASKS SERVLET DO GET CALLED");
        try (Connection connection = getConnection(req.getServletContext())) {
            TaskDao taskDao = new DatabaseTaskDao(connection);
            TaskService taskService = new SimpleTaskService(taskDao);

            User user = (User) req.getSession().getAttribute("user");
            int userId = user.getId();

            List<Task> taskList = taskService.findByUserId(userId);

            req.setAttribute("user", user);
            req.setAttribute("tasks", taskList);
            sendMessage(resp, 200, taskList);
            logger.info("TASKS SERVLET DO GET SUCCESFULL");
        } catch (SQLException e) {
            logger.error("SQL EXCEPTION THROWN "+e.getMessage());
        } catch (ServiceException e) {
            logger.warn("SERVICE EXCEPTION THRWON "+e.getMessage());
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("TASKS SERVLET DO POST CALLED");
        try (Connection connection = getConnection(req.getServletContext())) {
            TaskDao taskDao = new DatabaseTaskDao(connection);
            TaskService taskService = new SimpleTaskService(taskDao);

            User user = (User) req.getSession().getAttribute("user");
            int userId = user.getId();
            String name = req.getParameter("name");
            String description = req.getParameter("description");
            //    System.out.println(description+"dakokapitany");
            taskService.addTask(name, description, userId);
            doGet(req, resp);
            logger.info("TASKS SERVLET DO POST SUCCESFULL");
        } catch (SQLException e) {
            logger.error("SQL EXCEPTION THROWN "+e.getMessage());
        } catch (ServiceException se) {
            sendMessage(resp, HttpServletResponse.SC_UNAUTHORIZED, se.getMessage());
            logger.warn("SERVICE EXCEPTION THRWON "+se.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("TASKS SERVLET DO DELETE CALLED");
        try (Connection connection = getConnection(req.getServletContext())) {
            TaskDao taskDao = new DatabaseTaskDao(connection);
            TaskService taskService = new SimpleTaskService(taskDao);

            JsonNode jsonNode = createJsonNodeFromRequest(req);
            int id = Integer.parseInt(jsonNode.get("taskId").textValue());

            taskService.deleteTask(id);

            User user = (User) req.getSession().getAttribute("user");
            int userId = user.getId();

            List<Task> taskList = taskService.findByUserId(userId);
            sendMessage(resp,200, taskList);
            logger.info("TASKS SERVLET DO DELETE SUCCESFULL");
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
            logger.error("SQL EXCEPTION THROWN "+ex.getMessage());
        } catch (ServiceException se) {
            sendMessage(resp, HttpServletResponse.SC_UNAUTHORIZED, se.getMessage());
            logger.warn("SERVICE EXCEPTION THRWON "+se.getMessage());
        }
    }
}

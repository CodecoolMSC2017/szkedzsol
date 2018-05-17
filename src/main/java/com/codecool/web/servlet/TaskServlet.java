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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
        } catch (ServiceException se) {
            sendMessage(resp, HttpServletResponse.SC_UNAUTHORIZED, se.getMessage());
        }
    }
}

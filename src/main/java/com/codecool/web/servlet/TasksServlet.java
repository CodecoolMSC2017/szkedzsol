package com.codecool.web.servlet;


import com.codecool.web.dao.TaskDao;
import com.codecool.web.dao.database.DatabaseTaskDao;
import com.codecool.web.model.Task;
import com.codecool.web.model.User;
import com.codecool.web.service.TaskService;
import com.codecool.web.service.exception.ServiceException;
import com.codecool.web.service.simple.SimpleTaskService;

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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            TaskDao taskDao = new DatabaseTaskDao(connection);
            TaskService taskService = new SimpleTaskService(taskDao);

            User user = (User) req.getSession().getAttribute("user");
            int userId = user.getId();

            List<Task> taskList = taskService.findByUserId(userId);

            req.setAttribute("user", user);
            req.setAttribute("tasks", taskList);
            sendMessage(resp, 200, taskList);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ServiceException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }
}

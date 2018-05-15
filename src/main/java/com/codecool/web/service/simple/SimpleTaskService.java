package com.codecool.web.service.simple;

import com.codecool.web.dao.TaskDao;
import com.codecool.web.model.Task;
import com.codecool.web.service.TaskService;
import com.codecool.web.service.exception.ServiceException;

import java.sql.SQLException;
import java.util.List;

public class SimpleTaskService implements TaskService {

    private final TaskDao taskDao;

    public SimpleTaskService(TaskDao taskDao) {
        this.taskDao = taskDao;
    }


    @Override
    public List<Task> findByUserId(int userId) throws SQLException, ServiceException {
        try {
            return taskDao.findByUserId(userId);
        } catch (IllegalArgumentException ex) {
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public void addTask(String name, String description, int userId) throws SQLException, ServiceException {
        try {
            if(name == null || name.equals("") || description == null || description.equals("")) {
                throw new ServiceException("Incorrect name or descreption");
            } else {
                taskDao.insertTask(name, description, userId);
            }
        } catch (IllegalArgumentException ex) {
            throw new ServiceException(ex.getMessage());
        }
    }
}

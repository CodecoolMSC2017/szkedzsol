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

    @Override
    public Task getTaskById(int taskId, int userId) throws SQLException, ServiceException {
        try{
            return taskDao.findByUserAndTaskId(taskId, userId);
        } catch (IllegalArgumentException ex) {
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public void deleteTask(int id) throws SQLException, ServiceException {
        try {
            if (id == 0) {
                throw new ServiceException("Incorrect id");
            } else {
                taskDao.deleteTask(id);
            }
        } catch (IllegalArgumentException ex) {
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public void modifyTask(int id, String name, String description) throws SQLException, ServiceException {
        try {
            if (!name.equals("") && !description.equals("") && description != null && name != null) {
                taskDao.modifyTask(id, name, description);
            } else if (!name.equals("") && name != null && description.equals("") || description == null) {
                taskDao.modifyTaskName(id, name);
            } else if (!description.equals("") && description != null && name.equals("") || name == null) {
                taskDao.modifyTaskDescription(id, description);
            } else {
                throw new ServiceException("Fill at least one box");
            }

        } catch (IllegalArgumentException ex) {
            throw new ServiceException(ex.getMessage());
        }
    }
}

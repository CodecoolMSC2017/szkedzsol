package com.codecool.web.service.simple;

import com.codecool.web.dao.TaskDao;
import com.codecool.web.model.Task;
import com.codecool.web.service.TaskService;
import com.codecool.web.service.exception.ServiceException;
import com.codecool.web.service.exception.TaskException;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

public class SimpleTaskService implements TaskService {

    final static Logger logger = Logger.getLogger(SimpleTaskService.class);

    private final TaskDao taskDao;

    public SimpleTaskService(TaskDao taskDao) {
        this.taskDao = taskDao;
    }


    @Override
    public List<Task> findByUserId(int userId) throws SQLException, TaskException {
        try {
            return taskDao.findByUserId(userId);
        } catch (IllegalArgumentException ex) {
            logger.error("ARGUMENT MISSMATCH "+ex.getMessage());
            throw new TaskException(ex.getMessage());
        }
    }

    @Override
    public void addTask(String name, String description, int userId) throws SQLException, TaskException {
        try {
            if(name == null || name.equals("") || description == null || description.equals("")) {
                throw new TaskException("Incorrect name or descreption");
            } else {
                taskDao.insertTask(name, description, userId);
                logger.info("TASK ADDED TO DATABASE");

            }
        } catch (TaskException ex) {
            logger.warn("TASK EXCEPTION THROWN "+ex.getMessage());
        }
    }

    @Override
    public Task getTaskById(int taskId, int userId) throws SQLException, TaskException {
        try{
            return taskDao.findByUserAndTaskId(taskId, userId);
        } catch (IllegalArgumentException ex) {
            logger.error("ARGUMENT MISSMATCH "+ex.getMessage());
            throw new TaskException(ex.getMessage());
        }
    }

    @Override
    public void deleteTask(int id) throws SQLException, TaskException {
        try {
            if (id == 0) {
                throw new TaskException("Incorrect id");
            } else {
                taskDao.deleteTask(id);
                logger.info("TASK DELETED SUCCESFULLY");
            }
        } catch (TaskException ex) {
            logger.error(ex.getMessage());
        }
    }

    @Override
    public void modifyTask(int id, String name, String description) throws SQLException, TaskException {
        try {
            if (!name.equals("") && !description.equals("") && description != null && name != null) {
                taskDao.modifyTask(id, name, description);
                logger.info("TASK MODIFIED SUCCESSFULLY WITH ALL FIELDS");
            } else if (!name.equals("") && name != null && description.equals("") || description == null) {
                taskDao.modifyTaskName(id, name);
                logger.info("TASK MODIFIED SUCCESSFULLY WITH TWO FIELDS");
            } else if (!description.equals("") && description != null && name.equals("") || name == null) {
                taskDao.modifyTaskDescription(id, description);
                logger.info("TASK MODIFIED SUCCESSFULLY WITH TWO FIELDS");
            } else {
                throw new TaskException("Fill at least one box");
            }

        } catch (TaskException ex) {
            logger.warn(ex.getMessage());
        }
    }
}

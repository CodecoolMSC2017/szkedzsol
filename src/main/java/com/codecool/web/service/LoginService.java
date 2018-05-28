package com.codecool.web.service;

import com.codecool.web.model.User;
import com.codecool.web.service.exception.LogingException;
import com.codecool.web.service.exception.ServiceException;

import java.sql.SQLException;

public interface LoginService {

    User loginUser(String email, String name) throws SQLException, LogingException;
}

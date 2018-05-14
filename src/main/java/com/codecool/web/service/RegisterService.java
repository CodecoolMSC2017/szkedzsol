package com.codecool.web.service;

import com.codecool.web.service.exception.ServiceException;

import java.sql.SQLException;

public interface RegisterService {

    void registerUser(String email, String name) throws SQLException, ServiceException;
}

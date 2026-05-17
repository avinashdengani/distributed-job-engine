package com.avinash.jobengine.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JobRepository {

    private static final String URL = "jdbc:postgresql://localhost:5432/jobengine";
    private static final String USER = "admin";
    private static final String PASSWORD = "admin";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

package com.quickbase.domain.service;

import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;

import java.sql.SQLException;

public interface ConfigureDBService {
    void createDBConnection() throws SQLException;

    void closeDBConnection() throws SQLException;

    JdbcPooledConnectionSource getConnection();

}

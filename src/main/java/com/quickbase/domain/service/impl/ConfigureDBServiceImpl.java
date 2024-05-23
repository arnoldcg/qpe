package com.quickbase.domain.service.impl;

import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.quickbase.domain.service.ConfigureDBService;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;

@Slf4j(topic = "ConfigureDBService")
public class ConfigureDBServiceImpl implements ConfigureDBService {

    private JdbcPooledConnectionSource jdbcPooledConnectionSource;

    @Override
    public void createDBConnection() throws SQLException {

        this.jdbcPooledConnectionSource
                = new JdbcPooledConnectionSource("jdbc:sqlite:resources/data/citystatecountry.db");

        log.info("Created connection to database: citystatecountry.db");
    }

    @Override
    public void closeDBConnection() throws SQLException {
        if (this.jdbcPooledConnectionSource != null) {

            this.jdbcPooledConnectionSource.close();
            log.warn("Closed connection to database: citystatecountry.db");
        }
        else{
            log.error("Connection to database is not defined.");
        }
    }

    @Override
    public JdbcPooledConnectionSource getConnection() {
        return this.jdbcPooledConnectionSource;
    }
}

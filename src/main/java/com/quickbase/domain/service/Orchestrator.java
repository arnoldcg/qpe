package com.quickbase.domain.service;

import java.sql.SQLException;

public interface Orchestrator {
    void configureApplication() throws SQLException;

    void checkServiceHealth() throws SQLException;
    void retrieveTotalPopulationByCountry();
    void closeApplication() throws SQLException;
}

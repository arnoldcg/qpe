package com.quickbase.domain.service;

import java.io.IOException;
import java.sql.SQLException;

public interface Orchestrator {
    void configureApplication(String[] args) throws SQLException, IOException;

    void checkServiceHealth() throws SQLException;
    void retrieveTotalPopulationByCountry() throws Exception;
    void closeApplication() throws SQLException, IOException;
}

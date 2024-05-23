package com.quickbase.domain.service;

import com.quickbase.domain.entity.State;

import java.sql.SQLException;

public interface StateService {
    void healthCheck() throws SQLException;
    long getPopulation (State state);

    long getPopulation (long stateId) throws SQLException;
}

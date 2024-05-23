package com.quickbase.domain.service;

import com.quickbase.domain.entity.City;

import java.sql.SQLException;

public interface CityService {
    void healthCheck() throws SQLException;
    long getPopulation (City city) throws SQLException;
    long getPopulation (long id) throws SQLException;
}

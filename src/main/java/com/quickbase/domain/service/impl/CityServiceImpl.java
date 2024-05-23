package com.quickbase.domain.service.impl;

import com.j256.ormlite.dao.Dao;
import com.quickbase.domain.entity.City;
import com.quickbase.domain.service.CityService;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;

@Slf4j(topic = "CityServiceImpl")
public class CityServiceImpl implements CityService {

    private final Dao<City, Long> cityDao;

    public CityServiceImpl(Dao<City, Long> cityDao) {
        this.cityDao = cityDao;
    }

    @Override
    public void healthCheck() throws SQLException {
        long totalCities = cityDao.countOf();
        log.warn("healthCheck total cities: " + totalCities);
    }

    @Override
    public long getPopulation(City city) throws SQLException {
        return this.cityDao.queryForId(city.getCityId()).getPopulation();
    }

    @Override
    public long getPopulation(long id) throws SQLException {
        return this.cityDao.queryForId(id).getPopulation();
    }
}

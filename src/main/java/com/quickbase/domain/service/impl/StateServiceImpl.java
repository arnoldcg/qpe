package com.quickbase.domain.service.impl;

import com.j256.ormlite.dao.Dao;
import com.quickbase.domain.entity.City;
import com.quickbase.domain.entity.State;
import com.quickbase.domain.service.StateService;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j(topic = "StateServiceImpl")
public class StateServiceImpl implements StateService {

    private final Dao<State, Long> stateDao;

    public StateServiceImpl(Dao<State, Long> stateDao) {
        this.stateDao = stateDao;
    }

    @Override
    public void healthCheck() throws SQLException {
        long totalCountries = stateDao.countOf();
        log.warn("healthCheck total states: " + totalCountries);
    }

    @Override
    public long getPopulation(State state) {
        AtomicLong result = new AtomicLong();
        List<City> cities = new ArrayList<>(state.getCities());
        cities.forEach(city -> {
            long temp = city.getPopulation() == null ? 0 : city.getPopulation();
            result.addAndGet(temp);
        });

        return result.get();
    }

    @Override
    public long getPopulation(long stateId) throws SQLException {
        State state = this.stateDao.queryForId(stateId);
        return this.getPopulation(state);
    }
}

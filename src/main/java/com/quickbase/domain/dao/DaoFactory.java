package com.quickbase.domain.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.quickbase.domain.entity.City;
import com.quickbase.domain.entity.Country;
import com.quickbase.domain.entity.State;

import java.sql.SQLException;

public class DaoFactory {

    private final JdbcPooledConnectionSource connectionSource;

    public DaoFactory(JdbcPooledConnectionSource connectionSource) {
        this.connectionSource = connectionSource;
    }

    public Dao<City, Long> getCityDAO() throws SQLException {
        return DaoManager.createDao(this.connectionSource, City.class);
    }

    public Dao<State, Long> getStateDAO() throws SQLException {
        return DaoManager.createDao(this.connectionSource, State.class);
    }

    public Dao<Country, Long> getCountryDAO() throws SQLException {
        return DaoManager.createDao(this.connectionSource, Country.class);
    }
}

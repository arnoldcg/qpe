package com.quickbase.domain.service.impl;

import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.quickbase.domain.dao.DaoFactory;
import com.quickbase.domain.service.*;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;

@Slf4j(topic = "OrchestratorImpl")
public class OrchestratorImpl implements Orchestrator {

    private ConfigureDBService configureDBService;
    private CityService cityService;

    private StateService stateService;

    private CountryService countryService;

    @Override
    public void configureApplication() throws SQLException {
        log.info("Starting application configuration. \n");

        log.info("Creating database connection. \n");
        this.configureDBService = new ConfigureDBServiceImpl();
        configureDBService.createDBConnection();
        JdbcPooledConnectionSource connectionSource = configureDBService.getConnection();


        log.info("Configuring DAO layer. \n");
        DaoFactory factory = new DaoFactory(connectionSource);

        log.info("Creating the services.");
        this.cityService = new CityServiceImpl(factory.getCityDAO());
        this.stateService = new StateServiceImpl(factory.getStateDAO());
        this.countryService = new CountryServiceImpl(factory.getCountryDAO(), stateService);

    }

    @Override
    public void checkServiceHealth() throws SQLException {
        log.info("Checking service health. \n");
        this.cityService.healthCheck();
        this.stateService.healthCheck();
        this.countryService.healthCheck();
    }

    @Override
    public void retrieveTotalPopulationByCountry() {
       this.countryService.populationByCountry();
    }

    @Override
    public void closeApplication() throws SQLException {
        this.configureDBService.closeDBConnection();
    }
}

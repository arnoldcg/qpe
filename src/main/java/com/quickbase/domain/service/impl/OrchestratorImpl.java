package com.quickbase.domain.service.impl;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.opencsv.CSVWriter;
import com.quickbase.domain.dao.DaoFactory;
import com.quickbase.domain.dto.PopulationByCountry;
import com.quickbase.domain.service.*;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j(topic = "OrchestratorImpl")
public class OrchestratorImpl implements Orchestrator {

    private ConfigureDBService configureDBService;
    private CityService cityService;

    private StateService stateService;

    private CountryService countryService;

    private ObjectWriter jsonWriter;

    private CSVWriter csvWriter;


    @Override
    public void configureApplication() throws SQLException, IOException {
        log.info("Starting application configuration. \n");

        log.info("Creating database connection.");
        this.configureDBService = new ConfigureDBServiceImpl();
        configureDBService.createDBConnection();
        JdbcPooledConnectionSource connectionSource = configureDBService.getConnection();
        log.info("Connection to database created successfully...\n");

        log.info("Configuring DAO layer.");
        DaoFactory factory = new DaoFactory(connectionSource);

        log.info("Creating the services.");
        this.cityService = new CityServiceImpl(factory.getCityDAO());
        this.stateService = new StateServiceImpl(factory.getStateDAO());
        this.countryService = new CountryServiceImpl(factory.getCountryDAO(), stateService);


        log.info("Configuring the writers.");
        ObjectMapper mapper = new ObjectMapper();
        this.jsonWriter = mapper.writer(new DefaultPrettyPrinter());
        this.csvWriter = new CSVWriter(new FileWriter("population_by_country.csv"));
    }

    @Override
    public void checkServiceHealth() throws SQLException {
        log.info("Checking service health.");

        this.cityService.healthCheck();
        this.stateService.healthCheck();
        this.countryService.healthCheck();

        log.info("Services started... \n");
    }

    @Override
    public void retrieveTotalPopulationByCountry() throws Exception {

        log.info("Starting the data collection. \n");

        List<PopulationByCountry> populationByCountries = this.countryService.populationByCountry();
        List<String[]> csvEntries = populationByCountries
                .stream()
                .map(item -> new String[]{item.getCountryName(), Long.toString(item.getTotalPopulation())})
                .collect(Collectors.toList());


        log.info("Writing json file.");
        this.jsonWriter.writeValue(new File("population_by_country.json"), populationByCountries);

        log.info("Writing csv file.\n");
        String[] entries = {"countryName", "totalPopulation"};
        this.csvWriter.writeNext(entries);
        this.csvWriter.writeAll(csvEntries);
        this.csvWriter.flush();
    }

    @Override
    public void closeApplication() throws SQLException, IOException {
        this.configureDBService.closeDBConnection();
        this.csvWriter.close();
        log.info("Program successfully finished. Was a pleasure Quickbase: Arnold Castroman Garcia");
    }
}

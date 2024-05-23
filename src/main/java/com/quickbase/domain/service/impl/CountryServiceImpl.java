package com.quickbase.domain.service.impl;

import com.j256.ormlite.dao.CloseableWrappedIterable;
import com.j256.ormlite.dao.Dao;
import com.quickbase.devint.ConcreteStatService;
import com.quickbase.domain.dto.PopulationByCountry;
import com.quickbase.domain.entity.Country;
import com.quickbase.domain.entity.State;
import com.quickbase.domain.service.CountryService;
import com.quickbase.domain.service.StateService;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static com.quickbase.domain.mapper.PopulationByCountryMapper.POPULATION_BY_COUNTRY_MAPPER;

@Slf4j(topic = "CountryServiceImpl")
public class CountryServiceImpl implements CountryService {

    private final Dao<Country, Long> countryDao;

    private final StateService stateService;

    private final ConcreteStatService concreteStatService;

    public CountryServiceImpl(Dao<Country, Long> cityDao, StateService stateService) {
        this.countryDao = cityDao;
        this.stateService = stateService;
        this.concreteStatService = new ConcreteStatService();
    }

    @Override
    public void healthCheck() throws SQLException {
        long totalCountries = countryDao.countOf();
        log.warn("healthCheck total countries: " + totalCountries);
    }

    @Override
    public List<Country> getAllCountries() {
        CloseableWrappedIterable<Country> countriesIterator = this.countryDao.getWrappedIterable();
        List<Country> countries = new ArrayList<>();
        try {
            countriesIterator.forEach(countries::add);
        } catch (Exception e) {
            log.error("Error connecting to database to retrieve country information");
        }

        return countries;
    }

    @Override
    public void populationByCountry() {

        List<PopulationByCountry> populationByCountriesSQL = getCountryInformationFromDB();
        List<PopulationByCountry> populationByCountriesList = POPULATION_BY_COUNTRY_MAPPER
                .bindList(this.concreteStatService.GetCountryPopulations());
        List<PopulationByCountry> combinedList = POPULATION_BY_COUNTRY_MAPPER
                .combineCountryInformation(populationByCountriesSQL, populationByCountriesList);

        long x = 0;
    }

    private List<PopulationByCountry> getCountryInformationFromDB() {
        List<PopulationByCountry> populationByCountries = new ArrayList<>();
        List<Country> countries = this.getAllCountries();
        countries.forEach(country -> {
            long countryPopulation = this.getPopulation(country);
            PopulationByCountry tempItem = POPULATION_BY_COUNTRY_MAPPER
                    .bindSQL(country, countryPopulation);
            populationByCountries.add(tempItem);
        });

        return populationByCountries;
    }

    @Override
    public long getPopulation(Country country) {
        AtomicLong result = new AtomicLong();
        List<State> states = new ArrayList<>(country.getStates());
        states.forEach(state -> result.addAndGet(this.stateService.getPopulation(state)));

        return result.get();
    }

    @Override
    public long getPopulation(long countryId) throws SQLException {
        Country country = this.countryDao.queryForId(countryId);
        return this.getPopulation(country);
    }
}

package com.quickbase.domain.service;

import com.quickbase.domain.entity.Country;

import java.sql.SQLException;
import java.util.List;

public interface CountryService {
    void healthCheck() throws SQLException;

    long getPopulation(Country country) throws SQLException;

    long getPopulation(long countryId) throws SQLException;

    public void populationByCountry();

    List<Country> getAllCountries();

}

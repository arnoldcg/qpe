package com.quickbase.domain.mapper;

import com.quickbase.domain.dto.PopulationByCountry;
import com.quickbase.domain.entity.Country;
import org.apache.commons.lang3.tuple.Pair;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mapper
public interface PopulationByCountryMapper {

    PopulationByCountryMapper POPULATION_BY_COUNTRY_MAPPER = Mappers.getMapper(PopulationByCountryMapper.class);

    default PopulationByCountry bindSQL(Country country, long countryPopulation) {
        PopulationByCountry populationByCountry = PopulationByCountry.builder()
                .countryName(country.getCountryName())
                .totalPopulation(countryPopulation)
                .build();

        return populationByCountry;
    }

    default List<PopulationByCountry> bindList(List<Pair<String, Integer>> pairs) {
        List<PopulationByCountry> populationByCountries = new ArrayList<>();

        pairs.forEach(key -> {
            String countryName = key.getKey();
            long countryPopulation = key.getValue();

            PopulationByCountry populationByCountry = PopulationByCountry.builder()
                    .countryName(countryName)
                    .totalPopulation(countryPopulation)
                    .build();

            populationByCountries.add(populationByCountry);
        });

        return populationByCountries;
    }

    default List<PopulationByCountry> combineCountryInformation(List<PopulationByCountry> sqlDatabase,
                                                                List<PopulationByCountry> statiscList) {
        List<PopulationByCountry> result = Stream.of(sqlDatabase, statiscList)
                .flatMap(List::stream)
                .collect(
                        Collectors.groupingBy(
                                PopulationByCountry::getCountryName,
                                Collectors.summarizingLong(
                                        obj -> {
                                            if (sqlDatabase.contains(obj) && statiscList.contains(obj)) {
                                                int index = sqlDatabase.indexOf(obj);
                                                return sqlDatabase.get(index).getTotalPopulation();
                                            }
                                            if (sqlDatabase.contains(obj)) {
                                                int index = sqlDatabase.indexOf(obj);
                                                return sqlDatabase.get(index).getTotalPopulation();
                                            } else {
                                                int index = statiscList.indexOf(obj);
                                                return statiscList.get(index).getTotalPopulation();
                                            }
                                        })
                        )
                )
                .entrySet().stream()
                .map(
                        e -> PopulationByCountry
                                .builder()
                                .countryName(e.getKey())
                                .totalPopulation(e.getValue().getSum())
                                .build()
                )
                .collect(Collectors.toList());

        return result;
    }
}

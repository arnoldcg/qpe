package com.quickbase.domain.mapper;

import com.quickbase.domain.dto.PopulationByCountry;
import com.quickbase.domain.entity.Country;
import org.apache.commons.lang3.tuple.Pair;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mapper
public interface PopulationByCountryMapper {

    PopulationByCountryMapper POPULATION_BY_COUNTRY_MAPPER = Mappers.getMapper(PopulationByCountryMapper.class);

    default PopulationByCountry bindSQL(Country country, long countryPopulation) {
        PopulationByCountry populationByCountry = PopulationByCountry.builder()
                .id(country.getCountryId())
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

        populationByCountries.sort(Comparator.comparing(PopulationByCountry::getCountryName));
        return populationByCountries;
    }

    default List<PopulationByCountry> combineCountryInformation(List<PopulationByCountry> sqlDatabase,
                                                                List<PopulationByCountry> statiscList,
                                                                HashMap<String, String> sameNames) {

        List<PopulationByCountry> filtered = statiscList
                .stream()
                .filter(item -> !eliminateSimilar(sqlDatabase, sameNames, item))
                .collect(Collectors.toList());

        List<PopulationByCountry> result = Stream.of(sqlDatabase, filtered)
                .flatMap(List::stream)
                .collect(
                        Collectors.groupingBy(
                                PopulationByCountry::getCountryName,
                                Collectors.summarizingLong(
                                        obj -> pickCountryPopulation(sqlDatabase, statiscList, obj))
                        )
                )
                .entrySet().stream()
                .map(
                        e -> PopulationByCountry
                                .builder()
                                .countryName(e.getKey())
                                .totalPopulation(e.getValue().getMin())
                                .build()
                )
                .distinct()
                .sorted(Comparator.comparing(PopulationByCountry::getCountryName))
                .collect(Collectors.toList());


        return result;
    }

    static boolean eliminateSimilar(List<PopulationByCountry> sqlDatabase, HashMap<String, String> sameNames, PopulationByCountry item) {
        boolean result = false;
        if (sameNames.containsKey(item.getCountryName())) {
            String possibleSQLName = sameNames.get(item.getCountryName());

            int matches = (int) sqlDatabase
                    .stream()
                    .filter(foreignItem -> foreignItem.getCountryName().equals(possibleSQLName))
                    .count();

            result = matches >= 1;
        }
        return result;
    }

    static long pickCountryPopulation(List<PopulationByCountry> sqlDatabase,
                                      List<PopulationByCountry> statiscList,
                                      PopulationByCountry obj) {

        if (sqlDatabase.contains(obj) && statiscList.contains(obj)) {
            int index = sqlDatabase.indexOf(obj);
            return sqlDatabase.get(index).getTotalPopulation();
        } else if (sqlDatabase.contains(obj)) {
            int index = sqlDatabase.indexOf(obj);
            return sqlDatabase.get(index).getTotalPopulation();
        } else if (statiscList.contains(obj)) {
            int index = statiscList.indexOf(obj);
            return statiscList.get(index).getTotalPopulation();
        } else return 0L;
    }
}

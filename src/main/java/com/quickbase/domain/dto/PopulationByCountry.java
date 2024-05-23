package com.quickbase.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.util.HashMap;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Jacksonized
public class PopulationByCountry {

    @JsonIgnore
    private long id;

    private String countryName;

    private long totalPopulation;

    public boolean equals(Object obj) {

        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        boolean equal;
        PopulationByCountry temp = (PopulationByCountry) obj;

        //Standard case when the names are different
        equal = temp.countryName.equals(this.countryName);

        return equal;
    }


}

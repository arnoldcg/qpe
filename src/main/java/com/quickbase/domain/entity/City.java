package com.quickbase.domain.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;

@DatabaseTable(tableName = "City")
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class City {

    @Id
    @Column(name = "CityId")
    private long cityId;

    @Column(name = "CityName")
    private String cityName;

    @Column(name = "Population")
    private Long population;

    @Column(name = "StateId")
    @DatabaseField(foreign = true, canBeNull = false, columnName = "StateId")
    private State state;
}

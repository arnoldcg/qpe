package com.quickbase.domain.entity;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;

@DatabaseTable(tableName = "State")
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class State {

    @Id
    @Column(name = "StateId")
    private long stateId;

    @Column(name = "StateName")
    private String stateName;

    @Column(name = "CountryId")
    @DatabaseField(foreign = true, canBeNull = false,columnName = "CountryId")
    private Country country;

    @ForeignCollectionField(columnName = "state", foreignFieldName = "state")
    private ForeignCollection<City> cities;
}

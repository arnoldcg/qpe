package com.quickbase.domain.entity;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;

@DatabaseTable(tableName = "Country")
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Country {
    @Id
    @Column(name = "CountryId")
    private long countryId;

    @Column(name = "CountryName")
    private String countryName;

    @ForeignCollectionField(columnName = "country", foreignFieldName = "country")
    private ForeignCollection<State> states;

}

package com.genesys.raa.agg.definition;

import lombok.Getter;

//@ToString
@Getter
public enum ColumnGroupType {
    NONE("","NONE"),
    GROUP_BY("$G","GROUP"),
    SUM("$S","SUM"),
    COUNT("$C","COUNT");

    private String value;
    private String description;

    ColumnGroupType(String value, String description) {
        this.value = value;
        this.description = description;
    }



    @Override
    public String toString() {
        return description;
    }
}

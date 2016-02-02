package com.genesys.raa.agg.sql;

//@ToString
public enum ColumnGroupType {
    NONE(""),
    GROUP_BY("$G"),
    SUM("$S"),
    COUNT("$C");

    private String def;

    ColumnGroupType(String def) {
        this.def = def;
    }

    @Override
    public String toString() {
        return def;
    }
}

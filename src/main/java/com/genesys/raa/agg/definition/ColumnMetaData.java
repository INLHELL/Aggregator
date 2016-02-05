package com.genesys.raa.agg.definition;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class ColumnMetaData {

    private int position;
    private String name;
    private String label;
    private int type;
    private String typeName;
    private ColumnType groupType;
    private boolean indexed;

    public boolean isGroupBy() {
        return groupType == ColumnType.GROUP_BY;
    }

    public boolean isSum() {
        return groupType == ColumnType.SUM;
    }

    public boolean isCount() {
        return groupType == ColumnType.COUNT;
    }
}

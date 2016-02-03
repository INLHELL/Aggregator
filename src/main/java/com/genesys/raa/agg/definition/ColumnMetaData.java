package com.genesys.raa.agg.definition;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class ColumnMetaData {

    private int positionNumber;
    private String name;
    private String label;
    private int type;
    private String typeName;
    private ColumnGroupType groupType;
    private boolean indexed;


    public boolean isGroupBy() {
        return groupType == ColumnGroupType.GROUP_BY;
    }

    public boolean isSum() {
        return groupType == ColumnGroupType.SUM;
    }

    public boolean isCount() {
        return groupType == ColumnGroupType.COUNT;
    }
}

package com.genesys.raa.agg.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.genesys.raa.agg.definition.SqlMetaData;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "DEFINITION")
@Access(AccessType.FIELD)
@TypeDefs({
        @TypeDef(name = "SqlMetaData", typeClass = SqlMetaData.class)
})
public class Definition {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_GEN")
    @SequenceGenerator(name="SEQ_GEN", sequenceName="AGG_DEFINITION_SEQ")
    private long id;

    @Column(nullable = false, length = 50, unique = true)
    private String name;

    private Date createDate;
    private Date updateDate;

    @Lob
    @Column(nullable = false)
    private String sql;

    @Type(type = "SqlMetaData")
    private String sqlMetaData;

    /*
    It is a JSON-string

        columns:
            groupColumns,
            sumColumns
            indexColumns
            countColumns
            noneColumns

        parameters:
            IN-parameters
            OUT-parameters

        queryCost???
     */
//    private int parametersCount;
//    private int queryCost;

    public Definition(String name, String sql, String sqlMetaData) throws JsonProcessingException {
        this.name = name;
        this.sql = sql;
        this.sqlMetaData = sqlMetaData;
    }

    public Definition() {

    }
}

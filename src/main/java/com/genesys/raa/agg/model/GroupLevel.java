package com.genesys.raa.agg.model;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by vkhaluti on 05-Feb-2016.
 */
@Data
@Access(AccessType.FIELD)
@Entity(name = "RAA_GROUP_LEVEL")
public class GroupLevel {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_GEN")
    @SequenceGenerator(name="SEQ_GEN", sequenceName="AGG_GROUP_LEVEL_SEQ")
    private long id;

    @ManyToOne
    @JoinColumn(name = "GROUP_ID")
    private Group group;

    @Column(name = "LEVEL_NUM")
    private int levelNum;

    @Column(length = 10)
    private String code;

    @Column(length = 50)
    private String name;

    private String levelTable;
    private String levelColumn;

    @Column(length = 255)
    private String description;
}

package com.genesys.raa.agg.model;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by vkhaluti on 05-Feb-2016.
 */
@Data
@Access(AccessType.FIELD)
@Entity(name = "GROUP_LEVEL")
public class GroupLevel {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_GEN")
    @SequenceGenerator(name="SEQ_GEN", sequenceName="AGG_GROUP_LEVEL_SEQ")
    long id;

    // TODO find out how to setup entities relations
    /*@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GROUP_ID", referencedColumnName = "ID", nullable = false)
    Group group;*/

    @Column(length = 50)
    private String name;

    @Column(length = 10)
    private String code;

    @Column(length = 255)
    private String description;

    @Column(name = "LEVEL_NUM")
    private int level;

    @Column(name = "PARENT_ID")
    private long parentId;

    // TODO find out how to setup entities relations
    /*@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID")
    private GroupLevel parent;*/

}

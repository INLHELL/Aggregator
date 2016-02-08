package com.genesys.raa.agg.model;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by vkhaluti on 05-Feb-2016.
 */
@Data
//@Entity(name = "GROUP")
@Access(AccessType.FIELD)
public class Group {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_GEN")
    @SequenceGenerator(name="SEQ_GEN", sequenceName="AGG_GROUP_SEQ")
    private long id;

    @Column(length = 50)
    private String name;

    @Column(length = 10)
    private String code;

    @Column(length = 255)
    private String description;


    // TODO find out how to setup entities relations
    //@OneToMany(fetch = FetchType.LAZY)
    //private Set<GroupLevel> groupLevels = new HashSet<>();
}

package com.genesys.raa.agg.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by SPIDER on 03.02.2016.
 */
@Data
@Entity(name = "RAA_JOB_HISTORY")
public class JobHistory {
    @Id
    private long id;
}

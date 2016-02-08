package com.genesys.raa.agg.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.PreparedStatement;
import java.util.List;

@Data
@Entity(name = "RAA_AGGREGATE")
public class Aggregate {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO, generator="SEQ_GEN")
	@SequenceGenerator(name="SEQ_GEN", sequenceName="AGG_AGGREGATE_SEQ")
	private long id;

	@ManyToOne
	@JoinColumn(name = "DEFINITION_ID")
	private Definition definition;

	@ManyToOne
	@JoinColumn(name = "GROUP_ID")
	private Group group;

	private String tenantKey;

}

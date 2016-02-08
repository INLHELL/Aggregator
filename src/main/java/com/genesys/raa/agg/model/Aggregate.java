package com.genesys.raa.agg.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.PreparedStatement;
import java.util.List;

@Data
@Entity
public class Aggregate {

	@Id
	private long id;

	/*public Aggregate() throws Exception {
	}

	public Aggregate(String aggregateName, String selectSql,
					 List<String> groupColumns) throws Exception {
		// TODO Auto-generated constructor stub
	}

	PreparedStatement preparedStatement;

	*//**
	 * @return the preparedStatement
	 *//*
	public PreparedStatement getPreparedStatement() {
		return preparedStatement;
	}

	public void setColumns(String[] columns) {
		// TODO Auto-generated method stub
		
	}
	*/
}

package com.genesys.raa.agg.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.genesys.raa.agg.definition.ColumnGroupType;
import com.genesys.raa.agg.definition.ColumnMetaData;
import com.google.common.collect.ListMultimap;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Entity(name = "DEFINITION")
@ToString(exclude = {"mapper", "metaColumns"})
public class Definition {

    @Id
    @Column(name="id")
   // @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    private String aggregateName;
    private String selectSql;

   // private ListMultimap<ColumnGroupType, ColumnMetaData> metaColumns = LinkedListMultimap.create();

    @Column(name = "META_DATA")
    private String metaColumnsJson;

    @Autowired
    private ObjectMapper mapper;
//	private List<String> columns = new ArrayList<String>();
//	private List<String> groupColumns;
//	private List<String> sumColumns = new ArrayList<String>();
//	private List<String> sqlParameters = new ArrayList<String>();

    private int parametersCount;
    private int queryCost;

    public Definition(String aggregateName, String selectSql, ListMultimap<ColumnGroupType, ColumnMetaData>
            columnGroups) throws JsonProcessingException {
        this.aggregateName = aggregateName;
        this.selectSql = selectSql;
       // this.metaColumns = columnGroups;

//		ObjectMapper mapper = new ObjectMapper().registerModule(new GuavaModule());
        this.metaColumnsJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(columnGroups);
    }

    /*public ListMultimap<ColumnGroupType, ColumnMetaData> getMetaColumns() throws IOException {
        return mapper.readValue(metaColumnsJson, ListMultimap.class);
    }*/

    /**
     * @param scaleUnit
     * @return name of the table in which an aggregated data is stored for this time scale
     */
	/*public String getTableName(TimeScaleUnit scaleUnit ) {
		 
		String tablePrefix = configuration.getDatabaseTablePrefix();
		String tableName = tablePrefix + "_" + aggregateName + "_" + scaleUnit.toString();
		return tableName;
	}*/

    /**
     * @return the name
     */
    public String getName() {
        return aggregateName;
    }

    /**
     * @return the sql
     */
    public String getSelectSql() {
        return selectSql;
    }
    /**
     * @return the columns
     */
//	public List<String> getColumns() {
//		return columns;
//	}
//	/**
//	 * @return the groupColumns
//	 */
//	public List<String> getGroupByColumns() {
//		return groupColumns;
//	}
//	/**
//	 * @return the sumColumns
//	 */
//	public List<String> getSumColumns() {
//		return sumColumns;
//	}

    /**
     * @return the parametersCount
     */
    public int getParametersCount() {
        return parametersCount;
    }

    /**
     * @return the queryCost
     */
    public int getQueryCost() {
        return queryCost;
    }

    public List<String> getIndexColumns() {
        // TODO Auto-generated method stub
        return null;
    }

}

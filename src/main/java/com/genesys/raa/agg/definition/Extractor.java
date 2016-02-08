package com.genesys.raa.agg.definition;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.genesys.raa.agg.model.Definition;
import com.genesys.raa.agg.persistence.DefinitionPersistence;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by SPIDER on 03.02.2016.
 */
@Component
public class Extractor {

    private static final String SQL_EXTENSION = "sql";
    private static final String INDEX_MARKER = "$I";

    @Value("${aggregate.templates.dir}")
    private String aggregateTemplatesDir;

    @Autowired
    private Deployer deployer;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private DefinitionPersistence definitionPersistence;

    @Autowired
    ObjectMapper mapper;


    @PostConstruct
    private void init() throws IOException {
        final Set<Path> aggregateTemplatePaths = Files.list(new File(aggregateTemplatesDir).toPath())
            .collect(Collectors.toSet());
        final Map<String, Pair<String, PreparedStatement>> nameToSqlAndStatement = this
            .extractStatementFromTemplate(aggregateTemplatePaths);
        final Map<String, Pair<String, Set<ColumnMetaData>>> nameToSqlAndColumnMetaData = this
            .extractColumnMetaDataFromStatement(nameToSqlAndStatement);
//        final Map<String, Pair<String, Set<ParameterMetaData>>> nameToSqlAndColumnMetaData = this
// .extractParameterMetaDataFromStatement(nameToSqlAndStatement);
        for (Definition definition : this.buildDefinitionBasedOnMetaData(nameToSqlAndColumnMetaData)) {
            Definition existingDefinition = definitionPersistence.findByName(definition.getName());
            if (existingDefinition != null) {
                definition.setId(existingDefinition.getId());
            }
            definitionPersistence.save(definition);
        }

    }

    private Set<Definition> buildDefinitionBasedOnMetaData(Map<String, Pair<String, Set<ColumnMetaData>>>
                                                               nameToSqlAndMetaData) {
        Set<Definition> definitions = Sets.newHashSet();
        nameToSqlAndMetaData.entrySet()
            .forEach((aggregateNameColumnsMetaData)
//                -> Errors.rethrow().wrap(()
                    -> {
                    final String aggregateName = aggregateNameColumnsMetaData.getKey();
                    final Collection<ColumnMetaData> columnsMetaData = aggregateNameColumnsMetaData.getValue()
                        .getRight();
                    final String aggregateQueryString = aggregateNameColumnsMetaData.getValue().getLeft();
                    try {
                        definitions
                            .add(new Definition(aggregateName, aggregateQueryString, mapper
                                .writerWithDefaultPrettyPrinter()
                                .writeValueAsString(columnsMetaData)));
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                }
//            )
            );
        return definitions;
    }


    /*
    Read *.sql files from specific folder, parse it with PreparedStatement
    and create definitions (Definition entity class)
    and populate to database to DEFINITION table
     */
    private Map<String, Pair<String, Set<ColumnMetaData>>> extractColumnMetaDataFromStatement(Map<String,
        Pair<String, PreparedStatement>> nameToSqlAndStatement) {
        Map<String, Pair<String, Set<ColumnMetaData>>> nameToSqlAndColumnMetaData = Maps.newHashMap();
        nameToSqlAndStatement
            .entrySet()
            .forEach(
                (aggregateNamePreparedStatement) ->

//                    Errors.rethrow().wrap(() ->
                {
                    ResultSetMetaData aggregateResultSetMetaData = null;
                    try {
                        aggregateResultSetMetaData = aggregateNamePreparedStatement
                            .getValue().getRight().getMetaData();

                        final int columnCount = aggregateResultSetMetaData.getColumnCount();
                        final ResultSetMetaData finalAggregateResultSetMetaData = aggregateResultSetMetaData;
                        Set<ColumnMetaData> columnMetaDatas = IntStream.range(1, columnCount + 1).mapToObj
                            (columnPosition -> {
                                String columnName = null;
                                ColumnMetaData columnMetaData = null;
                                try {
                                    columnName = finalAggregateResultSetMetaData.getColumnName(columnPosition);
                                    ColumnGroupType columnType = defineColumnType(columnName);
                                    String columnNameWithoutDescription = columnName.replace(columnType.getValue(), "");
                                    boolean isIndexed = columnNameWithoutDescription.contains(INDEX_MARKER);
                                    columnNameWithoutDescription = columnNameWithoutDescription
                                        .replace(INDEX_MARKER, "");

                                    // TODO replace with builder!
                                    columnMetaData = new ColumnMetaData(
                                        columnPosition,
                                        columnNameWithoutDescription,
                                        finalAggregateResultSetMetaData.getColumnLabel(columnPosition),
                                        finalAggregateResultSetMetaData.getColumnType(columnPosition),
                                        finalAggregateResultSetMetaData.getColumnTypeName(columnPosition),
                                        columnType,
                                        isIndexed,
                                        finalAggregateResultSetMetaData.getPrecision(columnPosition),
                                        finalAggregateResultSetMetaData.getScale(columnPosition)
                                    );
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                                return columnMetaData;
                            }).collect(Collectors.toSet());
                        final String aggregateName = aggregateNamePreparedStatement.getKey();
                        final String aggregateQueryString = aggregateNamePreparedStatement.getValue().getLeft();
                        nameToSqlAndColumnMetaData.put(aggregateName, Pair.of(aggregateQueryString, columnMetaDatas));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
//            )
            );
        return nameToSqlAndColumnMetaData;
    }

    private ColumnGroupType defineColumnType(String columnName) {
        if (columnName.contains(ColumnGroupType.COUNT.getValue()))
            return ColumnGroupType.COUNT;
        else if (columnName.contains(ColumnGroupType.GROUP_BY.getValue()))
            return ColumnGroupType.GROUP_BY;
        else if (columnName.contains(ColumnGroupType.SUM.getValue()))
            return ColumnGroupType.SUM;
        else
            return ColumnGroupType.NONE;
    }

    private Map<String, Pair<String, PreparedStatement>> extractStatementFromTemplate(Set<Path>
                                                                                          aggregateTemplatePaths) {
        final Map<String, Pair<String, PreparedStatement>> aggregateNamePreparedStatementMap = Maps
            .newHashMapWithExpectedSize(20);

        aggregateTemplatePaths.forEach(
            aggregateTemplatePath -> {
                final String aggregateTemplatePathAsString = aggregateTemplatePath.toString();
                if (FilenameUtils.getExtension(aggregateTemplatePathAsString).equalsIgnoreCase(SQL_EXTENSION)) {
                    final String aggregateTemplateBaseName = FilenameUtils.getBaseName(aggregateTemplatePathAsString);
                    try {
                        String aggregateQueryString = new String(Files.readAllBytes(aggregateTemplatePath));
                        PreparedStatement aggregateStatement = dataSource.getConnection().prepareStatement
                            (aggregateQueryString);
                        aggregateNamePreparedStatementMap
                            .put(aggregateTemplateBaseName, Pair.of(aggregateQueryString, aggregateStatement));
                    } catch (IOException | SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        );
        return aggregateNamePreparedStatementMap;
    }

}

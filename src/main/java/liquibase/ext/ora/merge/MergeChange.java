package liquibase.ext.ora.merge;

import java.util.ArrayList;
import java.util.List;

import liquibase.ext.ora.AbstractOracleChange;
import liquibase.change.ChangeMetaData;
import liquibase.change.ChangeWithColumns;
import liquibase.change.ColumnConfig;
import liquibase.change.DatabaseChange;
import liquibase.change.DatabaseChangeProperty;
import liquibase.database.Database;
import liquibase.exception.ValidationErrors;
import liquibase.statement.SqlStatement;

@DatabaseChange(name="merge", description = "Merge", priority = ChangeMetaData.PRIORITY_DEFAULT + 200)
public class MergeChange extends AbstractOracleChange implements ChangeWithColumns<ColumnConfig>{

    private String sourceTableName;
    private String sourceSchemaName;
    private String targetSchemaName;
    private String targetTableName;
    private String onCondition;
    private String updateCondition;
    private String deleteCondition;
    private String insertCondition;
    private String insertColumnsNameList;
    private String insertColumnsValueList;
    private String updateList;
    private List<ColumnConfig> columns;
    
    public MergeChange() {
        columns = new ArrayList<ColumnConfig>();
    }
    
    @Override
    public ValidationErrors validate(Database database) {
        ValidationErrors validate = super.validate(database);
        validate.checkRequiredField("columns", columns);
        return validate;
    }
    
    @Override
    public void setColumns(List<ColumnConfig> columns) {
        this.columns = columns;
    }

    @Override
    public void addColumn(ColumnConfig column) {
        columns.add(column);
    }

    public void removeColumn(ColumnConfig column) {
        columns.remove(column);
    }
    
    @DatabaseChangeProperty(mustEqualExisting = "table.column", description = "Data to insert into columns", requiredForDatabase = "all")
    public List<ColumnConfig> getColumns() {
        return columns;
    }

    public String getSourceTableName() {
        return sourceTableName;
    }

    public void setSourceTableName(String sourceTableName) {
        this.sourceTableName = sourceTableName;
    }

    public String getSourceSchemaName() {
        return sourceSchemaName;
    }

    public void setSourceSchemaName(String sourceSchemaName) {
        this.sourceSchemaName = sourceSchemaName;
    }

    public String getTargetSchemaName() {
        return targetSchemaName;
    }

    public void setTargetSchemaName(String targetSchemaName) {
        this.targetSchemaName = targetSchemaName;
    }

    public String getTargetTableName() {
        return targetTableName;
    }

    public void setTargetTableName(String targetTableName) {
        this.targetTableName = targetTableName;
    }

    public String getOnCondition() {
        return onCondition;
    }

    public void setOnCondition(String onCondition) {
        this.onCondition = onCondition;
    }

    public String getUpdateCondition() {
        return updateCondition;
    }

    public void setUpdateCondition(String updateCondition) {
        this.updateCondition = updateCondition;
    }

    public String getDeleteCondition() {
        return deleteCondition;
    }

    public void setDeleteCondition(String deleteCondition) {
        this.deleteCondition = deleteCondition;
    }

    public String getInsertCondition() {
        return insertCondition;
    }

    public void setInsertCondition(String insertCondition) {
        this.insertCondition = insertCondition;
    }

    public String getInsertColumnsNameList() {
        return insertColumnsNameList;
    }

    public void setInsertColumnsNameList(String insertColumnsNameList) {
        this.insertColumnsNameList = insertColumnsNameList;
    }

    public String getInsertColumnsValueList() {
        return insertColumnsValueList;
    }

    public void setInsertColumnsValueList(String insertColumnsValueList) {
        this.insertColumnsValueList = insertColumnsValueList;
    }

    public String getUpdateList() {
        return updateList;
    }

    public void setUpdateList(String updateList) {
        this.updateList = updateList;
    }

    @Override
    public SqlStatement[] generateStatements(Database database) {

        String sourceSchemaName = getSourceSchemaName() == null ? database.getDefaultSchemaName() : getSourceSchemaName();
        String targetSchemaName = getTargetSchemaName() == null ? database.getDefaultSchemaName() : getTargetSchemaName();

        MergeStatement statement = new MergeStatement(getSourceTableName(), sourceSchemaName, getTargetTableName(),
                targetSchemaName);
        statement.setOnCondition(getOnCondition());
        statement.setDeleteCondition(getDeleteCondition());
        statement.setInsertCondition(getInsertCondition());
        statement.setUpdateCondition(getUpdateCondition());
        statement.setUpdateList(getUpdateList());
        
        for (ColumnConfig column : columns) {

        	if (database.supportsAutoIncrement()
        			&& column.isAutoIncrement() != null && column.isAutoIncrement()) {
            	// skip auto increment columns as they will be generated by the database
            	continue;
            }

            statement.addColumnValue(column.getName(), column.getValueObject());
        }
        

        return new SqlStatement[]{statement};
    }

    public String getConfirmationMessage() {

        return "Tables " + getSourceTableName() + " & " + getTargetTableName() + " merged";
    }

}

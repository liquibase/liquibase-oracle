package liquibase.ext.ora.merge;

import liquibase.statement.AbstractSqlStatement;
import liquibase.statement.SqlStatement;

public class MergeStatement extends AbstractSqlStatement {

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


    public MergeStatement(String sourceTableName, String sourceSchemaName,
                          String targetTableName, String targetSchemaName) {

        this.sourceTableName = sourceTableName;
        this.sourceSchemaName = sourceSchemaName;
        this.targetSchemaName = targetSchemaName;
        this.targetTableName = targetTableName;
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


}

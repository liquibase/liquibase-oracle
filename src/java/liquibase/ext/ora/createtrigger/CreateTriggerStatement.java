package liquibase.ext.ora.createtrigger;

import liquibase.statement.AbstractSqlStatement;
import liquibase.statement.SqlStatement;


public class CreateTriggerStatement extends AbstractSqlStatement {

    private String tableName;
    private String schemaName;
    private String tablespace;
    private String triggerName;
    private String afterBeforeInsteadOf;
    private String columnNames;
    private Boolean replace;
    private Boolean delete;
    private Boolean insert;
    private Boolean update;
    private Boolean updateOf;

    private Boolean forEachRow;
    private String viewName;
    private String nestedTableColumn;
    private String whenCondition;

    private String procedure;

    public CreateTriggerStatement(String schemaName, String triggerName, String afterBeforeInsteadOf) {
        this.schemaName = schemaName;
        this.triggerName = triggerName;
        this.afterBeforeInsteadOf = afterBeforeInsteadOf;
        this.replace = false;
        this.delete = false;
        this.insert = false;
        this.update = false;
        this.updateOf = false;
        this.forEachRow = false;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTriggerName() {
        return this.triggerName;
    }

    public void setTriggerName(String triggerName) {
        this.triggerName = triggerName;
    }

    public String getAfterBeforeInsteadOf() {
        return this.afterBeforeInsteadOf;
    }

    public void setAfterBeforeInsteadOf(String afterBeforeInsteadOf) {
        this.afterBeforeInsteadOf = afterBeforeInsteadOf;
    }

    public Boolean getDelete() {
        return this.delete;
    }

    public void setDelete(Boolean delete) {
        this.delete = delete;
    }

    public Boolean getInsert() {
        return this.insert;
    }

    public void setInsert(Boolean insert) {
        this.insert = insert;
    }

    public Boolean getUpdate() {
        return this.update;
    }

    public void setUpdate(Boolean update) {
        this.update = update;
    }

    public Boolean getUpdateOf() {
        return this.updateOf;
    }

    public void setUpdateOf(Boolean updateOf) {
        this.updateOf = updateOf;
    }

    public String getColumnNames() {
        return this.columnNames;
    }

    public void setColumnNames(String columnNames) {
        this.columnNames = columnNames;
    }

    public String getSchemaName() {
        return this.schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public String getTablespace() {
        return this.tablespace;
    }

    public void setTablespace(String tablespace) {
        this.tablespace = tablespace;
    }

    public Boolean getReplace() {
        return this.replace;
    }

    public void setReplace(Boolean replace) {
        this.replace = replace;
    }

    public Boolean getForEachRow() {
        return forEachRow;
    }

    public void setForEachRow(Boolean forEachRow) {
        this.forEachRow = forEachRow;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public String getNestedTableColumn() {
        return nestedTableColumn;
    }

    public void setNestedTableColumn(String nestedTableColumn) {
        this.nestedTableColumn = nestedTableColumn;
    }

    public String getWhenCondition() {
        return whenCondition;
    }

    public void setWhenCondition(String whenCondition) {
        this.whenCondition = whenCondition;
    }

    public String getProcedure() {
        return procedure;
    }

    public void setProcedure(String procedure) {
        this.procedure = procedure;
    }

}

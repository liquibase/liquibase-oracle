package liquibase.ext.ora.splittable;

import liquibase.statement.AbstractSqlStatement;
import liquibase.statement.SqlStatement;

public class SplitTableStatement extends AbstractSqlStatement {

    private String splitTableName;
    private String splitTableSchemaName;
    private String newTableName;
    private String newTableSchemaName;
    private String columnNameList;
    private String primaryKeyColumnName;
    private String context;

    public String getSplitTableName() {
        return splitTableName;
    }

    public void setSplitTableName(String splitTableName) {
        this.splitTableName = splitTableName;
    }

    public String getSplitTableSchemaName() {
        return splitTableSchemaName;
    }

    public void setSplitTableSchemaName(String splitTableSchemaName) {
        this.splitTableSchemaName = splitTableSchemaName;
    }

    public String getNewTableName() {
        return newTableName;
    }

    public void setNewTableName(String newTableName) {
        this.newTableName = newTableName;
    }

    public String getNewTableSchemaName() {
        return newTableSchemaName;
    }

    public void setNewTableSchemaName(String newTableSchemaName) {
        this.newTableSchemaName = newTableSchemaName;
    }

    public String getColumnNameList() {
        return columnNameList;
    }

    public void setColumnNameList(String columnNameList) {
        this.columnNameList = columnNameList;
    }

    public String getPrimaryKeyColumnName() {
        return primaryKeyColumnName;
    }

    public void setPrimaryKeyColumnName(String primaryKeyColumnName) {
        this.primaryKeyColumnName = primaryKeyColumnName;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public SplitTableStatement(String SplitTableName, String SplitTableSchemaName, String NewTableName,
                               String NewTableSchemaName, String Context) {
        this.newTableName = NewTableName;
        this.newTableSchemaName = NewTableSchemaName;
        this.splitTableName = SplitTableName;
        this.splitTableSchemaName = SplitTableSchemaName;
        this.context = Context;
    }

}

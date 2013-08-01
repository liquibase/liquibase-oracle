package liquibase.ext.ora.addcheck;

import liquibase.ext.ora.check.CheckState;
import liquibase.statement.SqlStatement;

public class AddCheckStatement extends CheckState implements SqlStatement {

    private String schemaName;
    private String tableName;
    private String tablespace;
    private String constraintName;
    private String condition;


    public AddCheckStatement(String schemaName, String tableName, String constraintName, String condition) {
        this.schemaName = schemaName;
        this.tableName = tableName;
        this.constraintName = constraintName;
        this.condition = condition;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public String getTableName() {
        return tableName;
    }

    public String getConstraintName() {
        return constraintName;
    }

    public String getTablespace() {
        return tablespace;
    }

    public AddCheckStatement setTablespace(String tablespace) {
        this.tablespace = tablespace;
        return this;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }


}

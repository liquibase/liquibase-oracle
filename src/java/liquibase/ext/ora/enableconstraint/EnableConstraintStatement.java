package liquibase.ext.ora.enableconstraint;

import liquibase.statement.AbstractSqlStatement;
import liquibase.statement.SqlStatement;

public class EnableConstraintStatement extends AbstractSqlStatement {

    private String tableName;
    private String tablespace;
    private String schemaName;
    private String constraintName;

    public EnableConstraintStatement(String tableName, String schemaName, String constraintName) {
        this.constraintName = constraintName;
        this.schemaName = schemaName;
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }

    public EnableConstraintStatement setTablespace(String tablespace) {
        this.tablespace = tablespace;
        return this;
    }

    public String getTablespace() {
        return tablespace;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public String getConstraintName() {
        return constraintName;
    }
}

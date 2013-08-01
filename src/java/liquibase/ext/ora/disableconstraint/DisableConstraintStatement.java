package liquibase.ext.ora.disableconstraint;

import liquibase.statement.AbstractSqlStatement;
import liquibase.statement.SqlStatement;

public class DisableConstraintStatement extends AbstractSqlStatement {

    private String schemaName;
    private String tableName;
    private String tablespace;
    private String constraintName;

    public DisableConstraintStatement(String schemaName, String tableName, String constraintName) {
        this.constraintName = constraintName;
        this.tableName = tableName;
        this.schemaName = schemaName;
    }

    public String getSchemaName() {
        return schemaName;
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

    public DisableConstraintStatement setTablespace(String tablespace) {
        this.tablespace = tablespace;
        return this;
    }

}

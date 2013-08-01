package liquibase.ext.ora.dropcheck;

import liquibase.statement.AbstractSqlStatement;
import liquibase.statement.SqlStatement;

public class DropCheckStatement extends AbstractSqlStatement {

    private String schemaName;
    private String tableName;
    private String constraintName;
    private String tablespace;

    public DropCheckStatement(String schemaName, String tableName, String constraintName) {
        this.constraintName = constraintName;
        this.schemaName = schemaName;
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public String getConstraintName() {
        return constraintName;
    }

    public String getTableSpace() {
        return tablespace;
    }

    public DropCheckStatement setTableSpace(String tablespace) {
        this.tablespace = tablespace;
        return this;
    }

}

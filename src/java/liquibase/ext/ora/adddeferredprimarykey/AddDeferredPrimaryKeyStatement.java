package liquibase.ext.ora.adddeferredprimarykey;

import liquibase.statement.AbstractSqlStatement;
import liquibase.statement.SqlStatement;

//TODO: zamieniæ prefixy is na get!

public class AddDeferredPrimaryKeyStatement extends AbstractSqlStatement {

    private final String schemaName;
    private final String tableName;
    private final String constraintName;
    private final String columnNames;
    private Boolean deferrable;
    private Boolean initiallyDeferred;

    public AddDeferredPrimaryKeyStatement(String schemaName, String tableName, String columnNames, String constraintName) {
        this.schemaName = schemaName;
        this.tableName = tableName;
        this.columnNames = columnNames;
        this.constraintName = constraintName;
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

    public String getColumnNames() {
        return columnNames;
    }

    public Boolean getDeferrable() {
        return deferrable;
    }

    public Boolean getInitiallyDeferred() {
        return initiallyDeferred;
    }

    public void setDeferrable(Boolean deferrable) {
        this.deferrable = deferrable;
    }

    public void setInitiallyDeferred(Boolean initiallyDeferred) {
        this.initiallyDeferred = initiallyDeferred;
    }

}

package liquibase.ext.ora.adddeferredprimarykey;

import liquibase.ext.ora.AbstractOracleChange;
import liquibase.change.Change;
import liquibase.change.ChangeMetaData;
import liquibase.change.DatabaseChange;
import liquibase.change.core.DropPrimaryKeyChange;
import liquibase.database.Database;
import liquibase.statement.SqlStatement;

@DatabaseChange(name="addDeferredPrimaryKey", description = "Add deferred primary key", priority = ChangeMetaData.PRIORITY_DEFAULT + 200)
public class AddDeferredPrimaryKeyChange extends AbstractOracleChange {

    private String schemaName;
    private String tableName;
    private String constraintName;
    private String columnNames;
    private Boolean deferrable;
    private Boolean initiallyDeferred;

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getConstraintName() {
        return constraintName;
    }

    public void setConstraintName(String constraintName) {
        this.constraintName = constraintName;
    }

    public String getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(String columnNames) {
        this.columnNames = columnNames;
    }

    public Boolean getDeferrable() {
        return deferrable;
    }

    public void setDeferrable(Boolean deferrable) {
        this.deferrable = deferrable;
    }

    public Boolean getInitiallyDeferred() {
        return initiallyDeferred;
    }

    public void setInitiallyDeferred(Boolean initiallyDerred) {
        this.initiallyDeferred = initiallyDerred;
    }

    public SqlStatement[] generateStatements(Database database) {
        String schemaName = getSchemaName() == null ? database.getDefaultSchemaName() : getSchemaName();

        boolean deferrable = false;
        if (getDeferrable() != null) {
            deferrable = getDeferrable();
        }

        boolean initiallyDeferred = false;
        if (getInitiallyDeferred() != null) {
            initiallyDeferred = getInitiallyDeferred();
        }

        AddDeferredPrimaryKeyStatement statement = new AddDeferredPrimaryKeyStatement(schemaName, getTableName(),
                getColumnNames(), getConstraintName());
        statement.setDeferrable(deferrable);
        statement.setInitiallyDeferred(initiallyDeferred);

        return new SqlStatement[]{statement};
    }

    @Override
    protected Change[] createInverses() {
        DropPrimaryKeyChange inverse = new DropPrimaryKeyChange();
        inverse.setSchemaName(getSchemaName());
        inverse.setTableName(getTableName());
        inverse.setConstraintName(getConstraintName());

        return new Change[]{inverse,};
    }

    public String getConfirmationMessage() {
        return "Deferred primary key added to " + getTableName() + " (" + getColumnNames() + ")";
    }

}

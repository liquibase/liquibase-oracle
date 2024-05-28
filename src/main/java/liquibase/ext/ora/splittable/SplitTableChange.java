package liquibase.ext.ora.splittable;

import liquibase.ext.ora.AbstractOracleChange;
import liquibase.change.ChangeMetaData;
import liquibase.change.DatabaseChange;
import liquibase.changelog.ChangeSet;
import liquibase.database.Database;
import liquibase.statement.SqlStatement;

@DatabaseChange(name="splitTable", description = "Split table", priority = ChangeMetaData.PRIORITY_DEFAULT + 200)
public class SplitTableChange extends AbstractOracleChange {

    private String splitTableName;
    private String splitTableSchemaName;
    private String newTableName;
    private String newTableSchemaName;
    private String columnNameList;
    private String primaryKeyColumnName;

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

    public SqlStatement[] generateStatements(Database database) {

        String splitTableSchemaName = getSplitTableSchemaName() == null ? database.getDefaultSchemaName()
                : getSplitTableSchemaName();
        String newTableSchemaName = getNewTableSchemaName() == null ? database.getDefaultSchemaName()
                : getNewTableSchemaName();

        SplitTableStatement statement = new SplitTableStatement(getSplitTableName(), splitTableSchemaName,
                getNewTableName(), newTableSchemaName, getRefactoringLevel());
        statement.setColumnNameList(getColumnNameList());
        statement.setPrimaryKeyColumnName(getPrimaryKeyColumnName());

        return new SqlStatement[]{statement};
    }

    public String getRefactoringLevel() {
        String level = null;
        ChangeSet changeSet = getChangeSet();

        if (changeSet != null && changeSet.getContexts() != null) {
            if (super.getChangeSet().getContexts().toString().contains("TRANSITION".toLowerCase())) {
                level = "TRANSITION";
            } else if (super.getChangeSet().getContexts().toString().contains("RESULTING".toLowerCase())) {
                level = "RESULTING";
            } else {
                level = "BASIC";
            }
        } else {
            level = "BASIC";
        }
        return level;
    }

    public String getConfirmationMessage() {

        return getSplitTableName() + " splitted. Created new table " + getNewTableName();
    }

}

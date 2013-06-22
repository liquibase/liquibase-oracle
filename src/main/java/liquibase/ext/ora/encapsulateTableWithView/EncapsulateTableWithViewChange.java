package liquibase.ext.ora.encapsulateTableWithView;

import liquibase.change.AbstractChange;
import liquibase.change.Change;
import liquibase.change.ChangeMetaData;
import liquibase.change.DatabaseChange;
import liquibase.change.core.DropViewChange;
import liquibase.change.core.RenameTableChange;
import liquibase.database.Database;
import liquibase.statement.SqlStatement;
import liquibase.statement.core.CreateViewStatement;
import liquibase.statement.core.RenameTableStatement;

@DatabaseChange(name="encapsulateTableWithView", description = "Encapsulate table with view", priority = ChangeMetaData.PRIORITY_DEFAULT)
public class EncapsulateTableWithViewChange extends AbstractChange {

    private String schemaName;
    private String tableName;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public SqlStatement[] generateStatements(Database database) {
        return new SqlStatement[]{new RenameTableStatement(null, schemaName, tableName, "T" + tableName),
                new CreateViewStatement(null, schemaName, tableName, "SELECT * FROM T" + tableName, true)};
    }

    public String getConfirmationMessage() {
        // return "View "+tableName+" created and table renamed to T"+tableName;
        return "Table " + tableName + " encapsulated with view";
    }

    @Override
    protected Change[] createInverses() {
        DropViewChange dropView = new DropViewChange();
        dropView.setViewName(tableName);

        RenameTableChange renameTable = new RenameTableChange();
        renameTable.setOldTableName("T" + tableName);
        renameTable.setNewTableName(tableName);

        return new Change[]{dropView, renameTable};
    }

}

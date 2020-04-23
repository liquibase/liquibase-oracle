package liquibase.ext.ora.dropmaterializedview;

import liquibase.change.AbstractChange;
import liquibase.change.ChangeMetaData;
import liquibase.change.DatabaseChange;
import liquibase.database.Database;
import liquibase.statement.SqlStatement;

@DatabaseChange(name="dropMaterializedView", description = "Drop Materialized View", priority = ChangeMetaData.PRIORITY_DEFAULT + 200)
public class DropMaterializedViewChange extends AbstractChange {

    private String schemaName;
    private String viewName;

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public String getConfirmationMessage() {
        return "Materialized view " + getViewName() + " has been droped";
    }

    public SqlStatement[] generateStatements(Database database) {
        DropMaterializedViewStatement statement = new DropMaterializedViewStatement(getViewName());
        statement.setSchemaName(getSchemaName());

        return new SqlStatement[]{statement};
    }
}

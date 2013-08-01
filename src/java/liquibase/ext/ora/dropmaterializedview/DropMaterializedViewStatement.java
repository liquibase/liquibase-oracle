package liquibase.ext.ora.dropmaterializedview;

import liquibase.statement.AbstractSqlStatement;
import liquibase.statement.SqlStatement;

public class DropMaterializedViewStatement extends AbstractSqlStatement {
    private String schemaName;
    private String viewName;

    public DropMaterializedViewStatement(String viewName) {
        this.viewName = viewName;
    }

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


}

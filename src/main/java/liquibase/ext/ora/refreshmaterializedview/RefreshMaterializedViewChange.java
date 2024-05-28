package liquibase.ext.ora.refreshmaterializedview;

import liquibase.ext.ora.AbstractOracleChange;
import liquibase.change.ChangeMetaData;
import liquibase.change.DatabaseChange;
import liquibase.database.Database;
import liquibase.statement.SqlStatement;

@DatabaseChange(name="refreshMaterializedView", description = "Refresh Materialized View", priority = ChangeMetaData.PRIORITY_DEFAULT + 200)
public class RefreshMaterializedViewChange extends AbstractOracleChange {

    private String schemaName;
    private String viewName;
    private Boolean atomicRefresh;
    private String refreshType;

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

    @Override
	public String getConfirmationMessage() {
        return "Materialized view " + getViewName() + " has been refreshed";
    }

    @Override
	public SqlStatement[] generateStatements(Database database) {
        RefreshMaterializedViewStatement statement = new RefreshMaterializedViewStatement(getViewName());
        statement.setSchemaName(getSchemaName());
        statement.setAtomicRefresh(getAtomicRefresh());
        statement.setRefreshType(getRefreshType());

        return new SqlStatement[]{statement};
    }

	public Boolean getAtomicRefresh() {
		return atomicRefresh;
	}

	public void setAtomicRefresh(Boolean atomicRefresh) {
		this.atomicRefresh = atomicRefresh;
	}

	public String getRefreshType() {
		return refreshType;
	}

	public void setRefreshType(String refreshType) {
		this.refreshType = refreshType;
	}
}

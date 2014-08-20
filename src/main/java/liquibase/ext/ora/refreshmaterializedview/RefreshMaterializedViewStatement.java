package liquibase.ext.ora.refreshmaterializedview;

import liquibase.statement.AbstractSqlStatement;

public class RefreshMaterializedViewStatement extends AbstractSqlStatement {
    private String schemaName;
    private String viewName;
    private Boolean atomicRefresh;
    private String refreshType;

    public RefreshMaterializedViewStatement(String viewName) {
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

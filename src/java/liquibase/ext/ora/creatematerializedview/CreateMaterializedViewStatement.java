package liquibase.ext.ora.creatematerializedview;

import liquibase.statement.AbstractSqlStatement;
import liquibase.statement.SqlStatement;

public class CreateMaterializedViewStatement extends AbstractSqlStatement {
    private String schemaName;
    private String viewName;
    private String columnAliases;
    private String objectType;
    private Boolean reducedPrecision;
    private Boolean usingIndex;
    private String tableSpace;
    private Boolean forUpdate;
    private String queryRewrite;
    private String subquery;
    private String enableOnPrebuiltTable;

    public CreateMaterializedViewStatement(String viewName, String subquery) {
        this.viewName = viewName;
        this.subquery = subquery;
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

    public String getColumnAliases() {
        return columnAliases;
    }

    public void setColumnAliases(String columnAliases) {
        this.columnAliases = columnAliases;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public Boolean getReducedPrecision() {
        return reducedPrecision;
    }

    public void setReducedPrecision(Boolean reducedPrecision) {
        this.reducedPrecision = reducedPrecision;
    }

    public Boolean getUsingIndex() {
        return usingIndex;
    }

    public void setUsingIndex(Boolean usingIndex) {
        this.usingIndex = usingIndex;
    }

    public String getTableSpace() {
        return tableSpace;
    }

    public void setTableSpace(String tableSpace) {
        this.tableSpace = tableSpace;
    }

    public Boolean getForUpdate() {
        return forUpdate;
    }

    public void setForUpdate(Boolean forUpdate) {
        this.forUpdate = forUpdate;
    }

    public String getQueryRewrite() {
        return queryRewrite;
    }

    public void setQueryRewrite(String queryRewrite) {
        this.queryRewrite = queryRewrite;
    }

    public String getSubquery() {
        return subquery;
    }

    public void setSubquery(String subquery) {
        this.subquery = subquery;
    }

    public String getEnableOnPrebuiltTable() {
      return enableOnPrebuiltTable;
    }
    
    public void setEnableOnPrebuiltTable(String enableOnPrebuiltTable) {
      this.enableOnPrebuiltTable=enableOnPrebuiltTable;
    }
}

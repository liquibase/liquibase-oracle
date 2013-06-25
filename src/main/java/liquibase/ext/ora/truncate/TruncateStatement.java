package liquibase.ext.ora.truncate;

import liquibase.statement.AbstractSqlStatement;

public class TruncateStatement extends AbstractSqlStatement {
    private String schemaName;
    private String tableName;
    private String clusterName;
    private Boolean purgeMaterializedViewLog;
    private Boolean reuseStorage;

    public TruncateStatement(String schemaName, String tableName, String clusterName) {
        this.schemaName = schemaName;
        this.tableName = tableName;
        this.clusterName = clusterName;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public String getTableName() {
        return tableName;
    }

    public String getClusterName() {
        return clusterName;
    }

    public Boolean purgeMaterializedViewLog() {
        return purgeMaterializedViewLog;
    }

    public TruncateStatement setPurgeMaterializedViewLog(Boolean purgeMaterializedViewLog) {
        this.purgeMaterializedViewLog = purgeMaterializedViewLog;
        return this;
    }

    public Boolean reuseStorage() {
        return reuseStorage;
    }

    public TruncateStatement setReuseStorage(Boolean reuseStorage) {
        this.reuseStorage = reuseStorage;
        return this;
    }
}

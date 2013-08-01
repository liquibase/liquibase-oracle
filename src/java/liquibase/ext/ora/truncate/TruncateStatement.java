package liquibase.ext.ora.truncate;

import liquibase.statement.AbstractSqlStatement;
import liquibase.statement.SqlStatement;

public class TruncateStatement extends AbstractSqlStatement {
    private String schemaName;
    private String tableName;
    private String clusterName;
    private boolean purgeMaterializedViewLog;
    private boolean reuseStorage;

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

    public boolean purgeMaterializedViewLog() {
        return purgeMaterializedViewLog;
    }

    public TruncateStatement setPurgeMaterializedViewLog(boolean purgeMaterializedViewLog) {
        this.purgeMaterializedViewLog = purgeMaterializedViewLog;
        return this;
    }

    public boolean reuseStorage() {
        return reuseStorage;
    }

    public TruncateStatement setReuseStorage(boolean reuseStorage) {
        this.reuseStorage = reuseStorage;
        return this;
    }
}

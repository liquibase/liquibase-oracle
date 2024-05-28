package liquibase.ext.ora.truncate;

import liquibase.ext.ora.AbstractOracleChange;
import liquibase.change.ChangeMetaData;
import liquibase.change.DatabaseChange;
import liquibase.database.Database;
import liquibase.statement.SqlStatement;

@DatabaseChange(name="truncate", description = "Truncate", priority = ChangeMetaData.PRIORITY_DEFAULT + 200)
public class TruncateChange extends AbstractOracleChange {

    private String schemaName;
    private String tableName;
    private String clusterName;
    private Boolean purgeMaterializedViewLog;
    private Boolean reuseStorage;

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

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public Boolean getPurgeMaterializedViewLog() {
        return purgeMaterializedViewLog;
    }

    public void setPurgeMaterializedViewLog(Boolean purgeMaterializedViewLog) {
        this.purgeMaterializedViewLog = purgeMaterializedViewLog;
    }

    public Boolean getReuseStorage() {
        return reuseStorage;
    }

    public void setReuseStorage(Boolean reuseStorage) {
        this.reuseStorage = reuseStorage;
    }

    public String getConfirmationMessage() {
        if (tableName != null && tableName.length() > 0) {
            return "Table " + tableName + " truncated";
        } else {
            return "Cluster " + clusterName + " truncated";
        }
    }

    public SqlStatement[] generateStatements(Database database) {
        boolean purgeMaterializedViewLog = false;
        if (getPurgeMaterializedViewLog() != null) {
            purgeMaterializedViewLog = getPurgeMaterializedViewLog();
        }

        return new SqlStatement[]{new TruncateStatement(schemaName, tableName, clusterName).setPurgeMaterializedViewLog(
                purgeMaterializedViewLog).setReuseStorage(reuseStorage)};
    }
}

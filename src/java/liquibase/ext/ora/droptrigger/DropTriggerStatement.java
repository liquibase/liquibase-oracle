package liquibase.ext.ora.droptrigger;

import liquibase.statement.AbstractSqlStatement;
import liquibase.statement.SqlStatement;

public class DropTriggerStatement extends AbstractSqlStatement {
    private String triggerName;
    private String schemaName;

    public DropTriggerStatement(String schemaName, String triggerName) {
        this.schemaName = schemaName;
        this.triggerName = triggerName;
    }

    public String getTriggerName() {
        return triggerName;
    }

    public void setTriggerName(String triggerName) {
        this.triggerName = triggerName;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }
}

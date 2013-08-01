package liquibase.ext.ora.disabletrigger;

import liquibase.statement.AbstractSqlStatement;
import liquibase.statement.SqlStatement;

public class DisableTriggerStatement extends AbstractSqlStatement {

    private String schemaName;
    private String triggerName;

    public DisableTriggerStatement(String schemaName, String triggerName) {
        this.schemaName = schemaName;
        this.triggerName = triggerName;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public String getTriggerName() {
        return triggerName;
    }

    public void setTriggerName(String triggerName) {
        this.triggerName = triggerName;
    }

}

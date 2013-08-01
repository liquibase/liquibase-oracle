package liquibase.ext.ora.enabletrigger;

import liquibase.statement.AbstractSqlStatement;
import liquibase.statement.SqlStatement;

public class EnableTriggerStatement extends AbstractSqlStatement {

    private String schemaName;
    private String triggerName;

    public EnableTriggerStatement(String schemaName, String triggerName) {
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

package liquibase.ext.ora.renametrigger;

import liquibase.statement.AbstractSqlStatement;
import liquibase.statement.SqlStatement;

public class RenameTriggerStatement extends AbstractSqlStatement {

    private String schemaName;
    private String triggerName;
    private String newName;

    public RenameTriggerStatement(String schemaName, String triggerName, String newName) {
        this.schemaName = schemaName;
        this.triggerName = triggerName;
        this.newName = newName;
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

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

}

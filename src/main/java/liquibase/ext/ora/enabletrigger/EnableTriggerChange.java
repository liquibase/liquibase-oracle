package liquibase.ext.ora.enabletrigger;

import liquibase.change.AbstractChange;
import liquibase.change.Change;
import liquibase.change.ChangeMetaData;
import liquibase.change.DatabaseChange;
import liquibase.database.Database;
import liquibase.ext.ora.disabletrigger.DisableTriggerChange;
import liquibase.statement.SqlStatement;

@DatabaseChange(name="enableTrigger", description = "Enable Trigger", priority = ChangeMetaData.PRIORITY_DEFAULT)
public class EnableTriggerChange extends AbstractChange {

    private String schemaName;
    private String triggerName;

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

    public String getConfirmationMessage() {
        return "Trigger has been enabled.";
    }

    @Override
    protected Change[] createInverses() {
        DisableTriggerChange inverse = new DisableTriggerChange();
        inverse.setSchemaName(getSchemaName());
        inverse.setTriggerName(getTriggerName());
        return new Change[]{inverse,};
    }

    public SqlStatement[] generateStatements(Database database) {
        EnableTriggerStatement statement = new EnableTriggerStatement(getSchemaName() == null ? database
                .getDefaultSchemaName() : getSchemaName(), getTriggerName());

        return new SqlStatement[]{statement};
    }

}

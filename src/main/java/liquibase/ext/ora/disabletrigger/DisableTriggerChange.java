package liquibase.ext.ora.disabletrigger;

import liquibase.change.AbstractChange;
import liquibase.change.Change;
import liquibase.change.ChangeMetaData;
import liquibase.change.DatabaseChange;
import liquibase.database.Database;
import liquibase.ext.ora.enabletrigger.EnableTriggerChange;
import liquibase.statement.SqlStatement;

@DatabaseChange(name="disableTrigger", description = "Disable Trigger", priority = ChangeMetaData.PRIORITY_DEFAULT)
public class DisableTriggerChange extends AbstractChange {

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
        return "Trigger has been disabled.";
    }

    @Override
    protected Change[] createInverses() {
        EnableTriggerChange inverse = new EnableTriggerChange();
        inverse.setSchemaName(getSchemaName());
        inverse.setTriggerName(getTriggerName());
        return new Change[]{inverse,};
    }

    public SqlStatement[] generateStatements(Database database) {
        DisableTriggerStatement statement = new DisableTriggerStatement(getSchemaName() == null ? database
                .getDefaultSchemaName() : getSchemaName(), getTriggerName());

        return new SqlStatement[]{statement};
    }

}

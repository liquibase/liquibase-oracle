package liquibase.ext.ora.disabletrigger;

import liquibase.change.AbstractChange;
import liquibase.change.Change;
import liquibase.change.ChangeMetaData;
import liquibase.database.Database;
import liquibase.ext.ora.enabletrigger.EnableTriggerChange;
import liquibase.statement.SqlStatement;

public class DisableTriggerChange extends AbstractChange {

    public DisableTriggerChange() {
        super("disableTrigger", "Disable Trigger", ChangeMetaData.PRIORITY_DEFAULT);
    }

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

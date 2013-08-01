package liquibase.ext.ora.renametrigger;

import liquibase.change.AbstractChange;
import liquibase.change.ChangeMetaData;
import liquibase.database.Database;
import liquibase.statement.SqlStatement;

public class RenameTriggerChange extends AbstractChange {

    public RenameTriggerChange() {
        super("renameTrigger", "Rename Trigger", ChangeMetaData.PRIORITY_DEFAULT);
    }

    private String schemaName;
    private String triggerName;
    private String newName;

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

    public String getConfirmationMessage() {
        return "Trigger has been renamed.";
    }

    public SqlStatement[] generateStatements(Database database) {
        RenameTriggerStatement statement = new RenameTriggerStatement(getSchemaName() == null ? database
                .getDefaultSchemaName() : getSchemaName(), getTriggerName(), getNewName());

        return new SqlStatement[]{statement};
    }

}

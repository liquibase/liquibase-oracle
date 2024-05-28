package liquibase.ext.ora.renametrigger;

import liquibase.ext.ora.AbstractOracleChange;
import liquibase.change.ChangeMetaData;
import liquibase.change.DatabaseChange;
import liquibase.database.Database;
import liquibase.statement.SqlStatement;

@DatabaseChange(name="renameTrigger", description = "Rename Trigger", priority = ChangeMetaData.PRIORITY_DEFAULT + 200)
public class RenameTriggerChange extends AbstractOracleChange {

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
        RenameTriggerStatement statement = new RenameTriggerStatement(getSchemaName(), getTriggerName(), getNewName());

        return new SqlStatement[]{statement};
    }

}

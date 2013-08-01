package liquibase.ext.ora.droptrigger;

import liquibase.change.AbstractChange;
import liquibase.change.ChangeMetaData;
import liquibase.database.Database;
import liquibase.statement.SqlStatement;

public class DropTriggerChange extends AbstractChange {
    private String triggerName;
    private String schemaName;

    public DropTriggerChange() {
        super("dropTrigger", "Drop Trigger", ChangeMetaData.PRIORITY_DEFAULT);
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

    public String getConfirmationMessage() {
        return "Trigger" + getTriggerName() + " has been droped";
    }

    public SqlStatement[] generateStatements(Database database) {


        String schemaName = getSchemaName() == null ? database.getDefaultSchemaName() : getSchemaName();

        DropTriggerStatement statement = new DropTriggerStatement(schemaName, getTriggerName());

        return new SqlStatement[]{
                statement
        };
    }

}

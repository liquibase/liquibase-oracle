package liquibase.ext.ora.dropcheck;

import liquibase.change.ChangeMetaData;
import liquibase.change.DatabaseChange;
import liquibase.database.Database;
import liquibase.ext.ora.check.CheckAttribute;
import liquibase.statement.SqlStatement;

@DatabaseChange(name="dropCheck", description = "Drop check", priority = ChangeMetaData.PRIORITY_DEFAULT + 200)
public class DropCheckChange extends CheckAttribute {

    public DropCheckChange() {
    }


    public SqlStatement[] generateStatements(Database database) {

        String schemaname = getSchemaName() == null ? database.getDefaultSchemaName() : getSchemaName();

        DropCheckStatement statement = new DropCheckStatement(schemaname, getTableName(), getConstraintName());

        return new SqlStatement[]
                {
                        statement
                };
    }

    public String getConfirmationMessage() {
        return getConstraintName() + " check DROPPED from " + getTableName();
    }

}

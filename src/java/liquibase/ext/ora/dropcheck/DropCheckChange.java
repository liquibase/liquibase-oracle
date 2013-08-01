package liquibase.ext.ora.dropcheck;

import liquibase.change.ChangeMetaData;
import liquibase.database.Database;
import liquibase.ext.ora.check.CheckAttribute;
import liquibase.statement.SqlStatement;

public class DropCheckChange extends CheckAttribute {

    public DropCheckChange() {
        super("dropCheck", "drop check", ChangeMetaData.PRIORITY_DEFAULT);
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

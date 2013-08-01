package liquibase.ext.ora.addcheck;

import liquibase.database.Database;
import liquibase.ext.ora.check.CheckAttribute;
import liquibase.ext.ora.dropcheck.DropCheckChange;

import liquibase.statement.SqlStatement;
import liquibase.change.Change;
import liquibase.change.ChangeMetaData;


public class AddCheckChange extends CheckAttribute {


    public AddCheckChange() {
        super("addCheck", "Add Check", ChangeMetaData.PRIORITY_DEFAULT);
    }


    public SqlStatement[] generateStatements(Database database) {

        String schemaName = getSchemaName() == null ? database.getDefaultSchemaName() : getSchemaName();

        AddCheckStatement statement = new AddCheckStatement(schemaName, getTableName(), getConstraintName(), getCondition());
        statement.setTablespace(getTablespace());
        statement.setDisable(getDisable());
        statement.setDeferrable(getDeferrable());
        statement.setInitiallyDeferred(getInitiallyDeferred());
        statement.setRely(getRely());
        statement.setValidate(getValidate());

        return new SqlStatement[]{statement};
    }

    public String getConfirmationMessage() {
        return "Constraint check " + getConstraintName() + " has been added to " + getTableName();
    }

    protected Change[] createInverses() {
        DropCheckChange inverse = new DropCheckChange();
        inverse.setSchemaName(getSchemaName());
        inverse.setTableName(getTableName());
        inverse.setConstraintName(getConstraintName());

        return new Change[]{inverse};
    }

}

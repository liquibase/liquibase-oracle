package liquibase.ext.ora.disableconstraint;

import liquibase.change.Change;
import liquibase.change.ChangeMetaData;
import liquibase.database.Database;
import liquibase.ext.ora.check.CheckAttribute;
import liquibase.ext.ora.enableconstraint.EnableConstraintChange;
import liquibase.statement.SqlStatement;

public class DisableConstraintChange extends CheckAttribute {

    public DisableConstraintChange() {
        super("disableConstraint", "disable constraint", ChangeMetaData.PRIORITY_DEFAULT);
    }

    @Override
    public SqlStatement[] generateStatements(Database database) {

        String schemaName = getSchemaName() == null ? database.getDefaultSchemaName() : getSchemaName();

        DisableConstraintStatement statement = new DisableConstraintStatement(schemaName, getTableName(),
                getConstraintName());
        statement.setTablespace(getTablespace());
        return new SqlStatement[]{statement};
    }

    @Override
    public String getConfirmationMessage() {

        return "constraint " + getConstraintName() + " DISABLED in " + getTableName();
    }

    @Override
    protected Change[] createInverses() {
        EnableConstraintChange inverse = new EnableConstraintChange();
        inverse.setSchemaName(getSchemaName());
        inverse.setTableName(getTableName());
        inverse.setConstraintName(getConstraintName());

        return new Change[]{inverse};
    }

}

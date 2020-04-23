package liquibase.ext.ora.enableconstraint;

import liquibase.change.Change;
import liquibase.change.ChangeMetaData;
import liquibase.change.DatabaseChange;
import liquibase.database.Database;
import liquibase.ext.ora.check.CheckAttribute;
import liquibase.ext.ora.disableconstraint.DisableConstraintChange;
import liquibase.statement.SqlStatement;

@DatabaseChange(name="enableConstraint", description = "Enable constraint", priority = ChangeMetaData.PRIORITY_DEFAULT + 200)
public class EnableConstraintChange extends CheckAttribute {

    public EnableConstraintChange() {
    }

    @Override
    public SqlStatement[] generateStatements(Database database) {

        String schemaName = getSchemaName() == null ? database.getDefaultSchemaName() : getSchemaName();

        EnableConstraintStatement statement = new EnableConstraintStatement(getTableName(), schemaName, getConstraintName());
        statement.setTablespace(getTablespace());

        return new SqlStatement[]{statement};
    }

    @Override
    public String getConfirmationMessage() {
        return "Constraint " + getConstraintName() + " ENABLED in " + getTableName();
    }

    @Override
    protected Change[] createInverses() {
        DisableConstraintChange inverse = new DisableConstraintChange();
        inverse.setSchemaName(getSchemaName());
        inverse.setTableName(getTableName());
        inverse.setConstraintName(getConstraintName());

        return new Change[]{inverse};
    }

}

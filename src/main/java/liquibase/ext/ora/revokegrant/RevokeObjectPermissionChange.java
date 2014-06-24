package liquibase.ext.ora.revokegrant;

import liquibase.change.Change;
import liquibase.change.ChangeMetaData;
import liquibase.change.DatabaseChange;
import liquibase.database.Database;
import liquibase.ext.ora.addgrant.AbstractObjectPermissionChange;
import liquibase.ext.ora.addgrant.GrantObjectPermissionChange;
import liquibase.statement.SqlStatement;


@DatabaseChange(name="revokeObjectPermission", description = "Revoke Schema Object Permission", priority = ChangeMetaData.PRIORITY_DEFAULT)
public class RevokeObjectPermissionChange extends AbstractObjectPermissionChange {

    public RevokeObjectPermissionChange() {}


    public RevokeObjectPermissionChange( AbstractObjectPermissionChange other ) {
    	super(other);
    }

    @Override
	public SqlStatement[] generateStatements(Database database) {

        String schemaName = getSchemaName() == null ? database.getDefaultSchemaName() : getSchemaName();

        RevokeObjectPermissionStatement statement = new RevokeObjectPermissionStatement(schemaName, getObjectName(), getRecipientList() );
        statement.setSelect(getSelect());
        statement.setUpdate(getUpdate());
        statement.setInsert(getInsert());
        statement.setDelete(getDelete());
        statement.setExecute(getExecute());

        return new SqlStatement[]{statement};
    }

    @Override
	public String getConfirmationMessage() {
        return "Revoking grants on " + getObjectName() + " that had been given to " + getRecipientList();
    }

    @Override
	protected Change[] createInverses() {
    	GrantObjectPermissionChange inverse = new GrantObjectPermissionChange(this);
        return new Change[]{inverse};
    }
}

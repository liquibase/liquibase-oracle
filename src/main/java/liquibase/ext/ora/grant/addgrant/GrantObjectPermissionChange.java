package liquibase.ext.ora.grant.addgrant;

import liquibase.change.Change;
import liquibase.change.ChangeMetaData;
import liquibase.change.DatabaseChange;
import liquibase.database.Database;
import liquibase.ext.ora.grant.AbstractObjectPermissionChange;
import liquibase.ext.ora.grant.AbstractObjectPermissionStatement;
import liquibase.ext.ora.grant.revokegrant.RevokeObjectPermissionChange;
import liquibase.statement.SqlStatement;


@DatabaseChange(name="grantObjectPermission", description = "Grant Schema Object Permission", priority = ChangeMetaData.PRIORITY_DEFAULT)
public class GrantObjectPermissionChange extends AbstractObjectPermissionChange {

    public GrantObjectPermissionChange() {}

    public GrantObjectPermissionChange( AbstractObjectPermissionChange other ) {
    	super(other);
    }

    @Override
	public SqlStatement[] generateStatements(Database database) {
      String schemaName = getSchemaName() == null ? database.getDefaultSchemaName() : getSchemaName();

      AbstractObjectPermissionStatement statement = new GrantObjectPermissionStatement(schemaName, getObjectName(), getRecipientList());
      statement.setSelect(getSelect());
      statement.setUpdate(getUpdate());
      statement.setInsert(getInsert());
      statement.setDelete(getDelete());
      statement.setExecute(getExecute());
      statement.setReferences(getReferences());
      statement.setGrantOption(getGrantOption());

      return new SqlStatement[]{statement};
    }

	@Override
	public String getConfirmationMessage() {
        return "Grants on " + getObjectName() + " have been given to " + getRecipientList();
    }

    @Override
	protected Change[] createInverses() {
    	RevokeObjectPermissionChange inverse = new RevokeObjectPermissionChange(this);
        return new Change[]{inverse};
    }

}

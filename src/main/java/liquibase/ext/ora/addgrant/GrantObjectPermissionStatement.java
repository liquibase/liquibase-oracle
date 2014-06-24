package liquibase.ext.ora.addgrant;


public class GrantObjectPermissionStatement extends AbstractObjectPermissionStatement {

	public GrantObjectPermissionStatement() {}

    public GrantObjectPermissionStatement(String schemaName, String objectName,
			String recipientList) {
		super(schemaName, objectName, recipientList);
	}
}

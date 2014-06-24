package liquibase.ext.ora.revokegrant;

import liquibase.ext.ora.addgrant.AbstractObjectPermissionStatement;

public class RevokeObjectPermissionStatement extends AbstractObjectPermissionStatement {

	public RevokeObjectPermissionStatement() {}

	public RevokeObjectPermissionStatement(String schemaName, String objectName,
			String recipientList) {
		super(schemaName, objectName, recipientList);
	}
}

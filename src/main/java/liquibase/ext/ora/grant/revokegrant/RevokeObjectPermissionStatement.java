package liquibase.ext.ora.grant.revokegrant;

import liquibase.ext.ora.grant.AbstractObjectPermissionStatement;

public class RevokeObjectPermissionStatement extends AbstractObjectPermissionStatement {

	public RevokeObjectPermissionStatement() {}

	public RevokeObjectPermissionStatement(String schemaName, String objectName,
			String recipientList) {
		super(schemaName, objectName, recipientList);
	}
}

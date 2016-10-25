package liquibase.ext.ora.grant.addgrant;

import liquibase.ext.ora.grant.AbstractObjectPermissionStatement;


public class GrantObjectPermissionStatement extends AbstractObjectPermissionStatement {

  public GrantObjectPermissionStatement() {
  }

  public GrantObjectPermissionStatement(String schemaName, String objectName,
                                        String recipientList) {
    super(schemaName, objectName, recipientList);
  }
}

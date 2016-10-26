package liquibase.ext.ora.grant.addgrant;

import liquibase.ext.ora.grant.AbstractObjectPermissionStatement;


public class GrantObjectPermissionStatement extends AbstractObjectPermissionStatement {

  private Boolean grantOption = Boolean.FALSE;

  public Boolean getGrantOption() {
    if ( grantOption == null ) {
      return false;
    }
    return grantOption;
  }

  public void setGrantOption(final Boolean grantOption) {
    this.grantOption = grantOption;
  }
  public GrantObjectPermissionStatement() {
  }

  public GrantObjectPermissionStatement(String schemaName, String objectName,
                                        String recipientList) {
    super(schemaName, objectName, recipientList);
  }
}

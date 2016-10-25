package liquibase.ext.ora.grant;

import java.util.ArrayList;
import java.util.List;
import liquibase.exception.ValidationErrors;
import liquibase.statement.AbstractSqlStatement;
import liquibase.util.StringUtils;

public abstract class AbstractObjectPermissionStatement extends
		AbstractSqlStatement {

	protected String schemaName;
	protected String objectName;
	protected String recipientList;
	private Boolean select = Boolean.FALSE;
	private Boolean update = Boolean.FALSE;
	private Boolean insert = Boolean.FALSE;
	private Boolean delete = Boolean.FALSE;
	private Boolean execute = Boolean.FALSE;
  private Boolean references = Boolean.FALSE;
  private Boolean index = Boolean.FALSE;
  private Boolean grantOption = Boolean.FALSE;

	public AbstractObjectPermissionStatement() {
		super();
	}

    public AbstractObjectPermissionStatement(String schemaName, String objectName,
			String recipientList) {
		this();
		this.schemaName = schemaName;
		this.objectName = objectName;
		this.recipientList = recipientList;
	}

	public String getSchemaName() {
		return schemaName;
	}

	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public String getRecipientList() {
		return recipientList;
	}

	public void setRecipientList(String recipientList) {
		this.recipientList = recipientList;
	}

	public Boolean getSelect() {
		if ( select == null ) {
			return false;
		}
		return select;
	}

	public void setSelect(Boolean select) {
		this.select = select;
	}

	public Boolean getUpdate() {
		if ( update == null ) {
			return false;
		}
		return update;
	}

	public void setUpdate(Boolean update) {
		this.update = update;
	}

	public Boolean getInsert() {
		if ( insert == null ) {
			return false;
		}
		return insert;
	}

	public void setInsert(Boolean insert) {
		this.insert = insert;
	}

	public Boolean getDelete() {
		if ( delete == null ) {
			return false;
		}
		return delete;
	}

	public void setDelete(Boolean delete) {
		this.delete = delete;
	}

	public Boolean getExecute() {
		if ( execute == null ) {
			return false;
		}
		return execute;
	}

	public void setExecute(Boolean execute) {
		this.execute = execute;
	}

  public Boolean getGrantOption() {
    return grantOption;
  }

  public void setGrantOption(final Boolean grantOption) {
    this.grantOption = grantOption;
  }

  public Boolean getReferences() {
    return references;
  }

  public void setReferences(final Boolean references) {
    this.references = references;
  }

  public Boolean getIndex() {
    return index;
  }

  public void setIndex(final Boolean index) {
    this.index = index;
  }

	public String getPermissionList() {
    List<String> permissions = new ArrayList<String>(5);
    if (getSelect()) {
      permissions.add("SELECT");
    }
    if (getUpdate()) {
      permissions.add("UPDATE");
    }
    if (getInsert()) {
      permissions.add("INSERT");
    }
    if (getDelete()) {
      permissions.add("DELETE");
    }
    if (getExecute()) {
      permissions.add("EXECUTE");
    }
    if (getReferences()) {
      permissions.add("REFERENCES");
    }
    if (getIndex()) {
      permissions.add("INDEX");
    }
    return StringUtils.join(permissions, ",");
  }

	public ValidationErrors validate() {
    ValidationErrors validationErrors = new ValidationErrors();
    validationErrors.checkRequiredField("tableName", getObjectName());
    validationErrors.checkRequiredField("recipientList", getRecipientList());
    if (!getSelect()
            && !getUpdate()
            && !getInsert()
            && !getDelete()
            && !getExecute()
            && !getIndex()
            && !getReferences()
            ) {
      validationErrors.addError("You must specify at least one permission.");
    }
    return validationErrors;
  }

}
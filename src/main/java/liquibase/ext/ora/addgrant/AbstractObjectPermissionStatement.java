package liquibase.ext.ora.addgrant;

import liquibase.statement.AbstractSqlStatement;

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

}
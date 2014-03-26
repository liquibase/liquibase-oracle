package liquibase.ext.ora.createSynonym;

import liquibase.statement.AbstractSqlStatement;

public class CreateSynonymStatement extends AbstractSqlStatement {
	
	private boolean replace = false;
	private boolean isPublic = false;
	private String objectName;
	private String objectSchemaName;

	private String synonymName;
	private String synonymSchemaName;

	public boolean isReplace() {
		return replace;
	}

	public void setReplace(boolean replace) {
		this.replace = replace;
	}

	public boolean isPublic() {
		return isPublic;
	}

	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public String getObjectSchemaName() {
		return objectSchemaName;
	}

	public void setObjectSchemaName(String objectSchemaName) {
		this.objectSchemaName = objectSchemaName;
	}

	public String getSynonymName() {
		return synonymName;
	}

	public void setSynonymName(String synonymName) {
		this.synonymName = synonymName;
	}

	public String getSynonymSchemaName() {
		return synonymSchemaName;
	}

	public void setSynonymSchemaName(String synonymSchemaName) {
		this.synonymSchemaName = synonymSchemaName;
	}
}

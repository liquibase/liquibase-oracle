package liquibase.ext.ora.dropSynonym;

import liquibase.statement.AbstractSqlStatement;

public class DropSynonymStatement extends AbstractSqlStatement {
	private Boolean isPublic = false;
	private Boolean force = false;
	private String synonymName;
	private String synonymSchemaName;

	public Boolean isPublic() {
		return isPublic;
	}

	public void setPublic(Boolean isPublic) {
		this.isPublic = isPublic;
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

	public Boolean isForce() {
		return force;
	}

	public void setForce(Boolean force) {
		this.force = force;
	}
}

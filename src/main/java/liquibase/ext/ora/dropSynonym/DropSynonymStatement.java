package liquibase.ext.ora.dropSynonym;

import liquibase.statement.AbstractSqlStatement;

public class DropSynonymStatement extends AbstractSqlStatement {
	private boolean isPublic = false;
	private boolean force = false;
	private String synonymName;
	private String synonymSchemaName;

	public boolean isPublic() {
		return isPublic;
	}

	public void setPublic(boolean isPublic) {
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

	public boolean isForce() {
		return force;
	}

	public void setForce(boolean force) {
		this.force = force;
	}
}

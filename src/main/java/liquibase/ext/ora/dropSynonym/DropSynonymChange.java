package liquibase.ext.ora.dropSynonym;

import java.text.MessageFormat;

import liquibase.ext.ora.AbstractOracleChange;
import liquibase.change.ChangeMetaData;
import liquibase.change.DatabaseChange;
import liquibase.database.Database;
import liquibase.statement.SqlStatement;

@DatabaseChange(name = "dropSynonym", description = "Drop synonym", priority = ChangeMetaData.PRIORITY_DEFAULT + 200)
public class DropSynonymChange extends AbstractOracleChange {

	private Boolean isPublic = false;
	private String synonymSchemaName;
	private String synonymName;
	private Boolean force;

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

    @Override
    public String getConfirmationMessage() {
        return MessageFormat.format("Synonym {0} dropped", getSynonymName());
    }

	@Override
	public SqlStatement[] generateStatements(Database database) {
		DropSynonymStatement statement = new DropSynonymStatement();
		statement.setForce(isForce());
		statement.setSynonymName(getSynonymName());
		statement.setSynonymSchemaName(getSynonymSchemaName());
		statement.setPublic(isPublic());
		return new SqlStatement[] { statement };
	}

}

package liquibase.ext.ora.createSynonym;

import java.text.MessageFormat;

import liquibase.ext.ora.AbstractOracleChange;
import liquibase.change.Change;
import liquibase.change.ChangeMetaData;
import liquibase.change.DatabaseChange;
import liquibase.database.Database;
import liquibase.ext.ora.dropSynonym.DropSynonymChange;
import liquibase.statement.SqlStatement;

@DatabaseChange(name = "createSynonym", description = "Create synonym", priority = ChangeMetaData.PRIORITY_DEFAULT + 200)
public class CreateSynonymChange extends AbstractOracleChange {
	
	private Boolean replace;
	private Boolean isPublic;
	private String objectName;
	private String objectSchemaName;

	private String synonymName;
	private String synonymSchemaName;

	public Boolean isReplace() {
		return replace;
	}

	public void setReplace(Boolean replace) {
		this.replace = replace;
	}

	public Boolean isPublic() {
		return isPublic;
	}

	public void setPublic(Boolean isPublic) {
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

    @Override
    public String getConfirmationMessage() {
        return MessageFormat.format("Synonym {0} created", getSynonymName());
    }

	@Override
	protected Change[] createInverses() {
		DropSynonymChange inverse = new DropSynonymChange();
		inverse.setPublic(isPublic());
		inverse.setSynonymName(getSynonymName());
		inverse.setSynonymSchemaName(getSynonymSchemaName());
		return new Change[] { inverse };
	}
	
	@Override
	public SqlStatement[] generateStatements(Database database) {
		CreateSynonymStatement statement = new CreateSynonymStatement();

		statement.setObjectName(getObjectName());
		statement.setObjectSchemaName(getObjectSchemaName());
		statement.setSynonymName(getSynonymName());
		statement.setSynonymSchemaName(getSynonymSchemaName());

		statement.setReplace(isReplace());
		statement.setPublic(isPublic());
		return new SqlStatement[] { statement };
	}

}

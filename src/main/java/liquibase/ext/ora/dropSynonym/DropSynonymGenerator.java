package liquibase.ext.ora.dropSynonym;

import liquibase.database.Database;
import liquibase.database.core.OracleDatabase;
import liquibase.exception.ValidationErrors;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;

public class DropSynonymGenerator extends AbstractSqlGenerator<DropSynonymStatement> {

	public boolean supports(DropSynonymStatement statement, Database database) {
		return database instanceof OracleDatabase;
	}

	public ValidationErrors validate(DropSynonymStatement statement, Database database, SqlGeneratorChain chain) {

		ValidationErrors validationErrors = new ValidationErrors();
		validationErrors.checkRequiredField("synonymName", statement.getSynonymName());
		return validationErrors;
	}

	/**
	 * DROP [PUBLIC] SYNONYM [ schema. ] synonym [FORCE] ;
	 */
	public Sql[] generateSql(DropSynonymStatement statement, Database database, SqlGeneratorChain chain) {
		StringBuilder sql = new StringBuilder("DROP ");

		if (statement.isPublic() != null && statement.isPublic()) {
			sql.append("PUBLIC ");
		}

		sql.append("SYNONYM ");

		if (statement.getSynonymSchemaName() != null) {
			sql.append(statement.getSynonymSchemaName()).append(".");
		}

		sql.append(statement.getSynonymName());

		if (statement.isForce() != null && statement.isForce()) {
			sql.append(" FORCE");
		}

		return new Sql[] { new UnparsedSql(sql.toString()) };
	}
}

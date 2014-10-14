package liquibase.ext.ora.createSynonym;

import liquibase.database.Database;
import liquibase.database.core.OracleDatabase;
import liquibase.exception.ValidationErrors;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;

public class CreateSynonymGenerator extends AbstractSqlGenerator<CreateSynonymStatement> {

	public boolean supports(CreateSynonymStatement statement, Database database) {
		return database instanceof OracleDatabase;
	}

	public ValidationErrors validate(CreateSynonymStatement statement, Database database, SqlGeneratorChain chain) {

		ValidationErrors validationErrors = new ValidationErrors();
		validationErrors.checkRequiredField("objectName", statement.getObjectName());
		validationErrors.checkRequiredField("synonymName", statement.getSynonymName());
		return validationErrors;
	}

	/**
	 * CREATE [OR REPLACE] [PUBLIC] SYNONYM [schema.]synonym FOR
	 * [schema.]object[@dblink] ;
	 * 
	 */
	public Sql[] generateSql(CreateSynonymStatement statement, Database database, SqlGeneratorChain chain) {
		StringBuilder sql = new StringBuilder("CREATE ");
		if (statement.isReplace() != null && statement.isReplace()) {
			sql.append("OR REPLACE ");
		}

		if (statement.isPublic() != null && statement.isPublic()) {
			sql.append("PUBLIC ");
		}

		sql.append("SYNONYM ");

		if (statement.getSynonymSchemaName() != null) {
			sql.append(statement.getSynonymSchemaName()).append(".");
		}

		sql.append(statement.getSynonymName());
		sql.append(" FOR ");

		if (statement.getObjectSchemaName() != null) {
			sql.append(statement.getObjectSchemaName()).append(".");
		}

		sql.append(statement.getObjectName());

		return new Sql[] { new UnparsedSql(sql.toString()) };
	}
}

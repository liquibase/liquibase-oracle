package liquibase.ext.ora.grant.addgrant;

import liquibase.database.Database;
import liquibase.database.core.OracleDatabase;
import liquibase.exception.ValidationErrors;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;

public class GrantObjectPermissionGenerator extends AbstractSqlGenerator<GrantObjectPermissionStatement> {


    @Override
	public Sql[] generateSql(GrantObjectPermissionStatement statement, Database database,
                             SqlGeneratorChain sqlGeneratorChain) {

      StringBuilder sql = new StringBuilder();

      sql.append("GRANT ");
      sql.append(statement.getPermissionList());
      sql.append(" ON ");
      sql.append(database.escapeTableName(null, statement.getSchemaName(), statement.getObjectName()));
      sql.append(" TO ");
      sql.append(statement.getRecipientList());

      if (statement.getGrantOption()) {
        sql.append(" WITH GRANT OPTION");
      }

      return new Sql[]{new UnparsedSql(sql.toString())};
    }

    @Override
	public boolean supports(GrantObjectPermissionStatement statement, Database database) {
        return database instanceof OracleDatabase;
    }

    @Override
    public ValidationErrors validate(GrantObjectPermissionStatement statement,
    		Database database, SqlGeneratorChain sqlGeneratorChain) {
    	return statement.validate();
    }
}

package liquibase.ext.ora.grant.revokegrant;

import liquibase.database.Database;
import liquibase.database.core.OracleDatabase;
import liquibase.exception.ValidationErrors;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;

public class RevokeObjectPermissionGenerator extends AbstractSqlGenerator<RevokeObjectPermissionStatement> {

    @Override
	public Sql[] generateSql(RevokeObjectPermissionStatement statement, Database database,
                             SqlGeneratorChain sqlGeneratorChain) {

        StringBuilder sql = new StringBuilder();

        sql.append("REVOKE ");
        sql.append( statement.getPermissionList() );
        sql.append( " ON " );
        sql.append(database.escapeTableName(null, statement.getSchemaName(), statement.getObjectName()));
        sql.append( " FROM " );
        sql.append( statement.getRecipientList() );

        return new Sql[]{new UnparsedSql(sql.toString())};
    }

    @Override
    public boolean supports(RevokeObjectPermissionStatement statement,
    		Database database) {
        return database instanceof OracleDatabase;
    }

    @Override
    public ValidationErrors validate(RevokeObjectPermissionStatement statement,
    		Database database, SqlGeneratorChain sqlGeneratorChain) {
    	return statement.validate();
    }

}

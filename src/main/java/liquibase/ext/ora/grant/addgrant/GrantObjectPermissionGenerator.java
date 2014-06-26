package liquibase.ext.ora.grant.addgrant;

import liquibase.database.Database;
import liquibase.ext.ora.grant.AbstractObjectPermissionGenerator;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;

public class GrantObjectPermissionGenerator extends AbstractObjectPermissionGenerator<GrantObjectPermissionStatement> {


    @Override
	public Sql[] generateSql(GrantObjectPermissionStatement statement, Database database,
                             SqlGeneratorChain sqlGeneratorChain) {

        StringBuilder sql = new StringBuilder();

        sql.append("GRANT ");
        sql.append( getPermissionList(statement) );
        sql.append( " ON " );
        sql.append(database.escapeTableName(null, statement.getSchemaName(), statement.getObjectName()));
        sql.append( " TO " );
        sql.append( statement.getRecipientList() );

        return new Sql[]{new UnparsedSql(sql.toString())};
    }

}

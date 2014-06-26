package liquibase.ext.ora.grant.revokegrant;

import liquibase.database.Database;
import liquibase.ext.ora.grant.AbstractObjectPermissionGenerator;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;

public class RevokeObjectPermissionGenerator extends AbstractObjectPermissionGenerator<RevokeObjectPermissionStatement> {


    @Override
	public Sql[] generateSql(RevokeObjectPermissionStatement statement, Database database,
                             SqlGeneratorChain sqlGeneratorChain) {

        StringBuilder sql = new StringBuilder();

        sql.append("REVOKE ");
        sql.append( getPermissionList(statement) );
        sql.append( " ON " );
        sql.append(database.escapeTableName(null, statement.getSchemaName(), statement.getObjectName()));
        sql.append( " FROM " );
        sql.append( statement.getRecipientList() );

        return new Sql[]{new UnparsedSql(sql.toString())};
    }

}

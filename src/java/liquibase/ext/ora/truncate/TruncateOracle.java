package liquibase.ext.ora.truncate;

import liquibase.database.Database;
import liquibase.database.core.OracleDatabase;
import liquibase.exception.ValidationErrors;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;

public class TruncateOracle extends AbstractSqlGenerator<TruncateStatement> {
    public int getPriority() {
        return 15;
    }

    public boolean supports(TruncateStatement truncateStatement, Database database) {
        return database instanceof OracleDatabase;
    }

    public ValidationErrors validate(TruncateStatement truncateStatement,
                                     Database database, SqlGeneratorChain sqlGeneratorChain) {
        ValidationErrors errors = new ValidationErrors();

        boolean noTable = truncateStatement.getTableName() == null || truncateStatement.getTableName().length() == 0;
        boolean noCluster = truncateStatement.getClusterName() == null || truncateStatement.getClusterName().length() == 0;
        if (noTable == noCluster) {
            errors.addError("Either tableName or clusterName must be set");
        }

        return errors;
    }

    public Sql[] generateSql(TruncateStatement truncateStatement,
                             Database database, SqlGeneratorChain sqlGeneratorChain) {
        boolean noTable = truncateStatement.getTableName() == null || truncateStatement.getTableName().length() == 0;
        boolean noCluster = truncateStatement.getClusterName() == null || truncateStatement.getClusterName().length() == 0;
        if (noTable == noCluster) {
            throw new IllegalStateException("Either tableName or clusterName must be set");
        }

        String sql = "TRUNCATE";

        if (noCluster) {
            sql += " TABLE "
                    + database.escapeTableName(truncateStatement.getSchemaName(), truncateStatement.getTableName());
            if (truncateStatement.purgeMaterializedViewLog()) {
                sql += " PURGE MATERIALIZED VIEW LOG";
            }
        } else {
            sql += " CLUSTER "
                    + database.escapeTableName(truncateStatement.getSchemaName(), truncateStatement.getClusterName());
        }

        if (truncateStatement.reuseStorage()) {
            sql += " REUSE STORAGE";
        }

        return new Sql[]{new UnparsedSql(sql)};
    }
}
package liquibase.ext.ora.creatematerializedview;

import liquibase.database.Database;
import liquibase.database.core.OracleDatabase;
import liquibase.exception.ValidationErrors;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;

public class CreateMaterializedViewOracle extends AbstractSqlGenerator<CreateMaterializedViewStatement> {

    public boolean supports(CreateMaterializedViewStatement createMaterializedViewStatement, Database database) {
        return database instanceof OracleDatabase;
    }

    public ValidationErrors validate(CreateMaterializedViewStatement createMaterializedViewStatement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        ValidationErrors validationErrors = new ValidationErrors();
        validationErrors.checkRequiredField("viewName", createMaterializedViewStatement.getViewName());
        validationErrors.checkRequiredField("subquery", createMaterializedViewStatement.getSubquery());
        return validationErrors;
    }

    public Sql[] generateSql(CreateMaterializedViewStatement createMaterializedViewStatement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        StringBuilder sql = new StringBuilder();

        sql.append("CREATE MATERIALIZED VIEW ");
        if (createMaterializedViewStatement.getSchemaName() != null)
            sql.append(createMaterializedViewStatement.getSchemaName()).append(".");
        if (createMaterializedViewStatement.getViewName() != null) {
            sql.append(createMaterializedViewStatement.getViewName())
                    .append(" ");
        }

        if (createMaterializedViewStatement.getColumnAliases() != null)
            sql.append("(")
                    .append(createMaterializedViewStatement.getColumnAliases())
                    .append(") ");
        if (createMaterializedViewStatement.getObjectType() != null) {
            sql.append("OFF ");
            if (createMaterializedViewStatement.getSchemaName() != null)
                sql.append(createMaterializedViewStatement.getSchemaName());
            sql.append(createMaterializedViewStatement.getObjectType())
                    .append(" ");
        }
        
        if(createMaterializedViewStatement.getEnableOnPrebuiltTable() != null && createMaterializedViewStatement.getEnableOnPrebuiltTable() == "true") {
          sql.append("ON PREBUILT TABLE ");
        }
        
        if (createMaterializedViewStatement.getReducedPrecision() != null) {
            if (createMaterializedViewStatement.getReducedPrecision())
                sql.append("WITH ");
            else
                sql.append("WITHOUT ");

            sql.append("REDUCED PRECISION ");
        }

        if (createMaterializedViewStatement.getUsingIndex() != null) {
            if (createMaterializedViewStatement.getUsingIndex()) {
                sql.append("USING INDEX ");
                if (createMaterializedViewStatement.getTableSpace() != null) {
                    sql.append(createMaterializedViewStatement.getTableSpace())
                            .append(" ");
                }
            } else
                sql.append("USING NO INDEX ");
        }

        if (createMaterializedViewStatement.getForUpdate() != null) {
            if (createMaterializedViewStatement.getForUpdate() == true) {
                sql.append("FOR UPDATE ");
            }
        }

        if (createMaterializedViewStatement.getQueryRewrite() != null && createMaterializedViewStatement.getQueryRewrite() == "enable") {
            sql.append("ENABLE QUERY REWRITE ");
        }

        sql.append("AS ");
        if (createMaterializedViewStatement.getSubquery() != null)
            sql.append(createMaterializedViewStatement.getSubquery());

        return new Sql[]
                {
                        new UnparsedSql(sql.toString())
                };
    }
}

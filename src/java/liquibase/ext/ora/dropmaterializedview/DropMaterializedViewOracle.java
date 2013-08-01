package liquibase.ext.ora.dropmaterializedview;

import liquibase.database.Database;
import liquibase.database.core.OracleDatabase;
import liquibase.exception.ValidationErrors;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;

public class DropMaterializedViewOracle extends AbstractSqlGenerator<DropMaterializedViewStatement> {

    public boolean supports(DropMaterializedViewStatement dropMaterializedViewStatement, Database database) {
        return database instanceof OracleDatabase;
    }

    public ValidationErrors validate(DropMaterializedViewStatement dropMaterializedViewStatement, Database database,
                                     SqlGeneratorChain sqlGeneratorChain) {
        ValidationErrors validationErrors = new ValidationErrors();
        validationErrors.checkRequiredField("viewName", dropMaterializedViewStatement.getViewName());
        return validationErrors;
    }

    public Sql[] generateSql(DropMaterializedViewStatement dropMaterializedViewStatement, Database database,
                             SqlGeneratorChain sqlGeneratorChain) {
        StringBuilder sql = new StringBuilder();

        sql.append("Drop materialized view ");
        if (dropMaterializedViewStatement.getViewName() != null) {
            if (dropMaterializedViewStatement.getSchemaName() != null) {
                sql.append(dropMaterializedViewStatement.getSchemaName()).append(".");
            }

            sql.append(dropMaterializedViewStatement.getViewName());
        }
        return new Sql[]{new UnparsedSql(sql.toString())};
    }
}

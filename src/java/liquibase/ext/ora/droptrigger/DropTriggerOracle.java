package liquibase.ext.ora.droptrigger;

import liquibase.database.Database;
import liquibase.database.core.OracleDatabase;
import liquibase.exception.ValidationErrors;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;

public class DropTriggerOracle extends AbstractSqlGenerator<DropTriggerStatement> {

    public boolean supports(DropTriggerStatement statement, Database database) {
        return (database instanceof OracleDatabase);
    }

    public ValidationErrors validate(DropTriggerStatement dropTrigger, Database database, SqlGeneratorChain sqlGeneratorChain) {

        ValidationErrors validationErrors = new ValidationErrors();
        validationErrors.checkRequiredField("schemaName", dropTrigger.getSchemaName());
        return validationErrors;
    }

    public Sql[] generateSql(DropTriggerStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {

        StringBuilder sql = new StringBuilder();

        sql.append("DROP TRIGGER ");
        if (statement.getSchemaName() != null) {
            sql.append(statement.getSchemaName())
                    .append(" ");
        }
        if (statement.getTriggerName() != null) {
            sql.append(statement.getTriggerName());
        }
        return new Sql[]{
                new UnparsedSql(sql.toString())
        };
    }
}

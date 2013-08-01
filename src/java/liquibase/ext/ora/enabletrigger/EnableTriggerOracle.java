package liquibase.ext.ora.enabletrigger;

import liquibase.database.Database;
import liquibase.database.core.OracleDatabase;
import liquibase.exception.ValidationErrors;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;

public class EnableTriggerOracle extends AbstractSqlGenerator<EnableTriggerStatement> {

    public boolean supports(EnableTriggerStatement enableTriggertStatement, Database database) {
        return database instanceof OracleDatabase;
    }

    public ValidationErrors validate(EnableTriggerStatement enableTriggerStatement, Database database,
                                     SqlGeneratorChain sqlGeneratorChain) {
        ValidationErrors validationErrors = new ValidationErrors();
        validationErrors.checkRequiredField("triggerName", enableTriggerStatement.getTriggerName());
        validationErrors.checkDisallowedField("schemaName", enableTriggerStatement.getSchemaName(), database);
        return validationErrors;
    }

    public Sql[] generateSql(EnableTriggerStatement enableTriggerStatement, Database database,
                             SqlGeneratorChain sqlGeneratorChain) {
        StringBuilder sql = new StringBuilder();
        sql.append("ALTER TRIGGER ");
        if (enableTriggerStatement.getSchemaName() != null) {
            sql.append(enableTriggerStatement.getSchemaName()).append(" ");
        }
        if (enableTriggerStatement.getTriggerName() != null) {
            sql.append(enableTriggerStatement.getTriggerName()).append(" ");
        } else {
            throw new IllegalStateException("Sorry but triggerName must be set");
        }

        sql.append("ENABLE");

        return new Sql[]{new UnparsedSql(sql.toString())};
    }
}

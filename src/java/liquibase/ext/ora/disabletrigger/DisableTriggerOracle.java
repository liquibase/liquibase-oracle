package liquibase.ext.ora.disabletrigger;

import liquibase.database.Database;
import liquibase.database.core.OracleDatabase;
import liquibase.exception.ValidationErrors;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;

public class DisableTriggerOracle extends AbstractSqlGenerator<DisableTriggerStatement> {

    public boolean supports(DisableTriggerStatement disableTriggertStatement, Database database) {
        return database instanceof OracleDatabase;
    }

    public ValidationErrors validate(DisableTriggerStatement disableTriggerStatement, Database database,
                                     SqlGeneratorChain sqlGeneratorChain) {
        ValidationErrors validationErrors = new ValidationErrors();
        validationErrors.checkRequiredField("triggerName", disableTriggerStatement.getTriggerName());
        validationErrors.checkDisallowedField("schemaName", disableTriggerStatement.getSchemaName(), database);
        return validationErrors;
    }

    public Sql[] generateSql(DisableTriggerStatement disableTriggerStatement, Database database,
                             SqlGeneratorChain sqlGeneratorChain) {
        StringBuilder sql = new StringBuilder();
        sql.append("ALTER TRIGGER ");
        if (disableTriggerStatement.getSchemaName() != null) {
            sql.append(disableTriggerStatement.getSchemaName()).append(" ");
        }
        if (disableTriggerStatement.getTriggerName() != null) {
            sql.append(disableTriggerStatement.getTriggerName()).append(" ");
        } else {
            throw new IllegalStateException("Sorry but triggerName must be set");
        }

        sql.append("DISABLE");

        return new Sql[]{new UnparsedSql(sql.toString())};
    }
}

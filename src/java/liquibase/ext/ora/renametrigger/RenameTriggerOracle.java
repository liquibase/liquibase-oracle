package liquibase.ext.ora.renametrigger;

import liquibase.change.ChangeMetaData;
import liquibase.database.Database;
import liquibase.database.core.OracleDatabase;
import liquibase.exception.ValidationErrors;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;

public class RenameTriggerOracle extends AbstractSqlGenerator<RenameTriggerStatement> {

    public int getPriority() {
        return ChangeMetaData.PRIORITY_DEFAULT;
    }

    public boolean supports(RenameTriggerStatement renameTriggertStatement, Database database) {
        return database instanceof OracleDatabase;
    }

    public ValidationErrors validate(RenameTriggerStatement renameTriggerStatement, Database database,
                                     SqlGeneratorChain sqlGeneratorChain) {
        ValidationErrors validationErrors = new ValidationErrors();
        validationErrors.checkRequiredField("triggerName", renameTriggerStatement.getTriggerName());
        validationErrors.checkDisallowedField("schemaName", renameTriggerStatement.getSchemaName(), database);
        validationErrors.checkDisallowedField("newName", renameTriggerStatement.getNewName(), database);
        return validationErrors;
    }

    public Sql[] generateSql(RenameTriggerStatement renameTriggerStatement, Database database,
                             SqlGeneratorChain sqlGeneratorChain) {
        StringBuilder sql = new StringBuilder();
        sql.append("ALTER TRIGGER ");
        if (renameTriggerStatement.getSchemaName() != null) {
            sql.append(renameTriggerStatement.getSchemaName()).append(" ");
        }
        if (renameTriggerStatement.getTriggerName() != null) {
            sql.append(renameTriggerStatement.getTriggerName()).append(" ");
        } else {
            throw new IllegalStateException("Sorry but triggerName must be set");
        }

        if (renameTriggerStatement.getNewName() != null) {
            sql.append("RENAME TO ");
            sql.append(renameTriggerStatement.getNewName());
        }

        return new Sql[]{new UnparsedSql(sql.toString())};
    }
}

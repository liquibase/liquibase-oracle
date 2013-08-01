package liquibase.ext.ora.createtrigger;

import liquibase.database.Database;
import liquibase.database.core.OracleDatabase;
import liquibase.exception.ValidationErrors;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;


public class CreateTriggerOracle extends AbstractSqlGenerator<CreateTriggerStatement> {

    public boolean supports(CreateTriggerStatement createTriggertStatement, Database database) {
        return database instanceof OracleDatabase;
    }

    public ValidationErrors validate(CreateTriggerStatement createTriggerStatement, Database database,
                                     SqlGeneratorChain sqlGeneratorChain) {
        ValidationErrors validationErrors = new ValidationErrors();
        validationErrors.checkRequiredField("triggerName", createTriggerStatement.getTriggerName());
        validationErrors.checkRequiredField("afterBeforeInsteadOf", createTriggerStatement.getAfterBeforeInsteadOf());
        validationErrors.checkDisallowedField("schemaName", createTriggerStatement.getSchemaName(), database);
        return validationErrors;
    }

    public Sql[] generateSql(CreateTriggerStatement createTriggerStatement, Database database,
                             SqlGeneratorChain sqlGeneratorChain) {
        StringBuilder sql = new StringBuilder();
        sql.append("CREATE ");

        if (createTriggerStatement.getReplace()) {
            sql.append("OR REPLACE ");
        }

        sql.append("TRIGGER ");

        if (createTriggerStatement.getSchemaName() != null) {
            sql.append(createTriggerStatement.getSchemaName()).append(".");
        }

        if (createTriggerStatement.getTriggerName() != null) {
            sql.append(createTriggerStatement.getTriggerName()).append(" ").append(
                    createTriggerStatement.getAfterBeforeInsteadOf()).append(" ");
        }

        if (createTriggerStatement.getDelete()) {
            sql.append("DELETE ");
        }
        if (createTriggerStatement.getInsert()) {
            if (createTriggerStatement.getDelete()) {
                sql.append("OR ");
            }
            sql.append("INSERT ");
        }
        if (createTriggerStatement.getUpdate()) {
            if (createTriggerStatement.getDelete() || createTriggerStatement.getInsert()) {
                sql.append("OR ");
            }
            sql.append("UPDATE ");
        }

        if (createTriggerStatement.getUpdateOf()) {
            if (createTriggerStatement.getDelete() || createTriggerStatement.getInsert()
                    || createTriggerStatement.getUpdate()) {
                sql.append("OR ");
            }
            sql.append("UPDATE OF ");
            sql.append(createTriggerStatement.getColumnNames()).append(" ");
        }

        sql.append("ON ");
        if (createTriggerStatement.getViewName() != null) {
            if (createTriggerStatement.getNestedTableColumn() != null) {
                sql.append("NESTED TABLE ").append(createTriggerStatement.getNestedTableColumn()).append(" OF ");
            }

            if (createTriggerStatement.getSchemaName() != null) {
                sql.append(createTriggerStatement.getSchemaName()).append(".");
            }
            sql.append(createTriggerStatement.getViewName()).append(" ");
        } else if (createTriggerStatement.getTableName() != null) {
            if (createTriggerStatement.getSchemaName() != null) {
                sql.append(createTriggerStatement.getSchemaName()).append(".");
            }
            sql.append(createTriggerStatement.getTableName()).append(" ");
        }

        if (createTriggerStatement.getForEachRow()) {
            sql.append("FOR EACH ROW ");
        }

        if (createTriggerStatement.getWhenCondition() != null) {
            sql.append("WHEN ").append(createTriggerStatement.getWhenCondition()).append(" ");
        }

        if (createTriggerStatement.getProcedure() != null) {
            sql.append(createTriggerStatement.getProcedure());
        }

        return new Sql[]{new UnparsedSql(sql.toString())};
    }
}

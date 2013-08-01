package liquibase.ext.ora.adddeferredprimarykey;

import liquibase.database.Database;
import liquibase.database.core.OracleDatabase;
import liquibase.exception.ValidationErrors;
import liquibase.exception.Warnings;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;

public class AddDeferredPrimaryKeyOracle extends AbstractSqlGenerator<AddDeferredPrimaryKeyStatement> {

    public Sql[] generateSql(AddDeferredPrimaryKeyStatement statement, Database database,
                             SqlGeneratorChain sqlGeneratorChain) {
        String sql;

        ValidationErrors validationError = this.validate(statement, database, sqlGeneratorChain);
        if (validationError.hasErrors()) {
            for (String errorMassage : validationError.getErrorMessages()) {
                throw new IllegalStateException(errorMassage);
            }

        }

        if (statement.getConstraintName() == null) {
            sql = "ALTER TABLE " + database.escapeTableName(statement.getSchemaName(), statement.getTableName())
                    + " ADD PRIMARY KEY (" + database.escapeColumnNameList(statement.getColumnNames()) + ")";
        } else {
            sql = "ALTER TABLE " + database.escapeTableName(statement.getSchemaName(), statement.getTableName())
                    + " ADD CONSTRAINT " + database.escapeConstraintName(statement.getConstraintName()) + " PRIMARY KEY ("
                    + database.escapeColumnNameList(statement.getColumnNames()) + ")";
        }

        if (statement.getDeferrable() != null) {
            if (statement.getDeferrable()) {
                sql += " DEFERRABLE";
            }
        }
        if (statement.getInitiallyDeferred() != null) {
            if (statement.getInitiallyDeferred()) {
                sql += " INITIALLY DEFERRED";
            }
        }

        return new Sql[]{new UnparsedSql(sql)};
    }

    public boolean supports(AddDeferredPrimaryKeyStatement statement, Database database) {
        return (database instanceof OracleDatabase);
    }

    public ValidationErrors validate(AddDeferredPrimaryKeyStatement statement, Database database,
                                     SqlGeneratorChain sqlGeneratorChain) {
        ValidationErrors validationErrors = new ValidationErrors();

        if (!database.supportsInitiallyDeferrableColumns()) {
            validationErrors.checkDisallowedField("initiallyDeferred", statement.getInitiallyDeferred(), database);
            validationErrors.checkDisallowedField("deferrable", statement.getDeferrable(), database);
        }

        validationErrors.checkRequiredField("columnNames", statement.getColumnNames());
        validationErrors.checkRequiredField("tableName", statement.getTableName());
        return validationErrors;
    }

}

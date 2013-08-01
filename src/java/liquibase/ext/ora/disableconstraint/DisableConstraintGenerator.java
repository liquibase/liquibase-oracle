package liquibase.ext.ora.disableconstraint;

import liquibase.database.Database;
import liquibase.database.core.SQLiteDatabase;
import liquibase.exception.ValidationErrors;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;

public class DisableConstraintGenerator extends AbstractSqlGenerator<DisableConstraintStatement> {

    public Sql[] generateSql(DisableConstraintStatement statement,
                             Database database, SqlGeneratorChain sqlGeneratorChain) {

        StringBuilder sql = new StringBuilder();

        sql.append("ALTER TABLE ").append(database.escapeTableName(statement.getSchemaName(), statement.getTableName()));
        sql.append(" DISABLE ");
        sql.append("CONSTRAINT ").append(database.escapeConstraintName(statement.getConstraintName()));
        return new Sql[]{new UnparsedSql(sql.toString())};
    }

    public boolean supports(DisableConstraintStatement statement, Database database) {

        return (!(database instanceof SQLiteDatabase));
    }

    public ValidationErrors validate(DisableConstraintStatement statement,
                                     Database database, SqlGeneratorChain sqlGeneratorChain) {
        ValidationErrors validationErrors = new ValidationErrors();
        validationErrors.checkRequiredField("tableName", statement.getTableName());
        validationErrors.checkRequiredField("constraintName", statement.getConstraintName());

        return validationErrors;
    }

}

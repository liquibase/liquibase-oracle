package liquibase.ext.ora.dropcheck;

import liquibase.database.Database;
import liquibase.exception.ValidationErrors;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;

public class DropCheckGenerator extends
        AbstractSqlGenerator<DropCheckStatement> {

    public Sql[] generateSql(DropCheckStatement statement, Database database,
                             SqlGeneratorChain sqlGeneratorChain) {

        StringBuilder sql = new StringBuilder();
        sql.append("ALTER TABLE ").append(
                database.escapeTableName(statement.getSchemaName(), statement
                        .getTableName()));
        sql.append(" drop CONSTRAINT ").append(
                database.escapeDatabaseObject(statement.getConstraintName()));

        return new Sql[]{new UnparsedSql(sql.toString())};
    }

    @Override
    public boolean supports(DropCheckStatement statement, Database database) {
        return true;
    }

    public ValidationErrors validate(DropCheckStatement statement,
                                     Database database, SqlGeneratorChain sqlGeneratorChain) {

        ValidationErrors validationErrors = new ValidationErrors();
        validationErrors.checkRequiredField("tableName", statement
                .getTableName());
        validationErrors.checkRequiredField("constraintName", statement
                .getConstraintName());

        return validationErrors;
    }

}

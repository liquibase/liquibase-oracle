package liquibase.ext.ora.addcheck;

import liquibase.database.Database;
import liquibase.database.core.SQLiteDatabase;
import liquibase.exception.ValidationErrors;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;

public class AddCheckGenerator extends AbstractSqlGenerator<AddCheckStatement> {


    public Sql[] generateSql(AddCheckStatement statement, Database database,
                             SqlGeneratorChain sqlGeneratorChain) {

        StringBuilder sql = new StringBuilder();

        sql.append("ALTER TABLE ").append(database.escapeTableName(statement.getSchemaName(), statement.getTableName())).append(" ");
        sql.append("ADD ");
        if (database.escapeDatabaseObject(statement.getConstraintName()) != null)
            sql.append("CONSTRAINT ").append(database.escapeConstraintName(statement.getConstraintName())).append(" ");
        sql.append("CHECK(").append(database.escapeDatabaseObject(statement.getCondition())).append(")");

        if (statement.getDeferrable() != null) {
            if (statement.getDeferrable()) sql.append(" DEFERRABLE");
            else sql.append(" NOT DEFERRABLE");
        }
        if (statement.getInitiallyDeferred() != null) {
            if (statement.getInitiallyDeferred()) sql.append(" INITIALLY DEFERRED");
            else sql.append(" INITIALLY IMMEDIATE");
        }
        if (statement.getDisable() != null) {
            if (statement.getDisable()) sql.append(" DISABLE");
            else sql.append(" ENABLE");
        }
        if (statement.getRely() != null) {
            if (statement.getRely()) sql.append(" RELY");
        }
        if (statement.getValidate() != null) {
            if (statement.getValidate()) sql.append(" VALIDATE");
        }

        return new Sql[]{new UnparsedSql(sql.toString())};
    }

    public boolean supports(AddCheckStatement statement, Database database) {
        return (!(database instanceof SQLiteDatabase));
    }

    public ValidationErrors validate(AddCheckStatement statement,
                                     Database database, SqlGeneratorChain sqlGeneratorChain) {
        ValidationErrors validationErrors = new ValidationErrors();
        validationErrors.checkRequiredField("tableName", statement.getTableName());
        validationErrors.checkRequiredField("condition", statement.getCondition());
        return validationErrors;
    }

}

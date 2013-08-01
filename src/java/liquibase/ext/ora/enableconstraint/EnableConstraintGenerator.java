package liquibase.ext.ora.enableconstraint;

import liquibase.database.Database;
import liquibase.database.core.SQLiteDatabase;
import liquibase.exception.ValidationErrors;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;

public class EnableConstraintGenerator extends AbstractSqlGenerator<EnableConstraintStatement> {

    public Sql[] generateSql(EnableConstraintStatement statement, Database database,
                             SqlGeneratorChain sqlGeneratorChain) {

        StringBuilder sql = new StringBuilder();

        sql.append("ALTER TABLE ").append(database.escapeTableName(statement.getSchemaName(), statement.getTableName())).append(" ENABLE CONSTRAINT ");
        sql.append(database.escapeDatabaseObject(statement.getConstraintName()));

        return new Sql[]{new UnparsedSql(sql.toString())};
    }

    public boolean supports(EnableConstraintStatement statement, Database database) {

        return (!(database instanceof SQLiteDatabase));
    }

    public ValidationErrors validate(EnableConstraintStatement statement,
                                     Database database, SqlGeneratorChain sqlGeneratorChain) {

        ValidationErrors validation = new ValidationErrors();
        validation.checkRequiredField("tableName", statement.getTableName());
        validation.checkRequiredField("constraintName", statement.getConstraintName());

        return validation;
    }

}

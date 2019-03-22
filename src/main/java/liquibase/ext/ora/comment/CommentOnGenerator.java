package liquibase.ext.ora.comment;

import liquibase.database.Database;
import liquibase.database.core.OracleDatabase;
import liquibase.exception.ValidationErrors;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;

public class CommentOnGenerator extends AbstractSqlGenerator<CommentOnStatement> {


    public Sql[] generateSql(CommentOnStatement statement, Database database,
                             SqlGeneratorChain sqlGeneratorChain) {

        String comment = database.escapeStringForDatabase(statement.getComment());
        String tableName = database.escapeTableName(null, statement.getSchemaName(), statement.getTableName());
        String columnName = database.escapeColumnName(null, statement.getSchemaName(), statement.getTableName(), statement.getColumnName());

        String sql = statement.isColumnComment()
                ? String.format("COMMENT ON COLUMN %s.%s IS '%s'", tableName, columnName, comment)
                : String.format("COMMENT ON TABLE %s IS '%s'", tableName, comment);
        return new Sql[]{new UnparsedSql(sql)};
    }

    public boolean supports(CommentOnStatement statement, Database database) {
        return database instanceof OracleDatabase;
    }

    public ValidationErrors validate(CommentOnStatement statement,
                                     Database database, SqlGeneratorChain sqlGeneratorChain) {
        ValidationErrors validationErrors = new ValidationErrors();
        validationErrors.checkRequiredField("tableName", statement.getTableName());
        validationErrors.checkRequiredField("comment", statement.getComment());
        return validationErrors;
    }

}

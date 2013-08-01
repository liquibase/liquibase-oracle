package liquibase.ext.ora.settransaction;

import liquibase.database.Database;
import liquibase.database.core.OracleDatabase;
import liquibase.exception.ValidationErrors;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;

public class SetTransactionOracle extends AbstractSqlGenerator<SetTransactionStatement> {

    public boolean supports(SetTransactionStatement setTransactionStatement, Database database) {
        return database instanceof OracleDatabase;
    }

    public ValidationErrors validate(SetTransactionStatement setTransactionStatement, Database database,
                                     SqlGeneratorChain sqlGeneratorChain) {
        ValidationErrors validationErrors = new ValidationErrors();
        validationErrors.checkDisallowedField("transactionName", setTransactionStatement.getTransactionName(), database);
        validationErrors.checkDisallowedField("rollbackSegment", setTransactionStatement.getRollbackSegment(), database);
        validationErrors.checkDisallowedField("isolationLevel", setTransactionStatement.getIsolationLevel(), database);
        validationErrors.checkDisallowedField("readOnlyWrite", setTransactionStatement.getReadOnlyWrite(), database);
        return validationErrors;
    }

    public Sql[] generateSql(SetTransactionStatement setTransactionStatement, Database database,
                             SqlGeneratorChain sqlGeneratorChain) {
        StringBuilder sql = new StringBuilder();
        sql.append("SET TRANSACTION ");
        if (setTransactionStatement.getIsolationLevel() != null) {
            if (setTransactionStatement.getReadOnlyWrite() != null || setTransactionStatement.getRollbackSegment() != null) {

            }

            sql.append("ISOLATION LEVEL ").append(setTransactionStatement.getIsolationLevel()).append(" ");
        } else if (setTransactionStatement.getRollbackSegment() != null) {
            if (setTransactionStatement.getReadOnlyWrite() != null) {

            }

            sql.append("USE ROLLBACK SEGMENT ").append(setTransactionStatement.getRollbackSegment()).append(" ");
        } else if (setTransactionStatement.getReadOnlyWrite() != null) {
            sql.append("READ ").append(setTransactionStatement.getReadOnlyWrite()).append(" ");
        }
        if (setTransactionStatement.getTransactionName() != null) {
            sql.append("NAME '").append(setTransactionStatement.getTransactionName()).append("'");
        }
        return new Sql[]{new UnparsedSql(sql.toString())};
    }
}

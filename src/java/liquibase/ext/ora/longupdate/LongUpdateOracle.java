package liquibase.ext.ora.longupdate;

import liquibase.database.Database;
import liquibase.database.core.OracleDatabase;
import liquibase.exception.ValidationErrors;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;

public class LongUpdateOracle extends AbstractSqlGenerator<LongUpdateStatement> {

    public boolean supports(LongUpdateStatement longUpdateStatement, Database database) {
        return database instanceof OracleDatabase;
    }

    public ValidationErrors validate(LongUpdateStatement longUpdaterStatement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        ValidationErrors validationErrors = new ValidationErrors();
        validationErrors.checkRequiredField("updateSql", longUpdaterStatement.getUpdateSql());
        return validationErrors;
    }

    public Sql[] generateSql(LongUpdateStatement longUpdateStatement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        StringBuilder sql = new StringBuilder();
        sql.append("declare commit_interval  integer; sleep_seconds integer; c integer; begin commit_interval := ")
                .append(longUpdateStatement.getCommitInterval())
                .append("; ")
                .append("sleep_seconds := ")
                .append(longUpdateStatement.getSleepSeconds())
                .append("; ")
                .append("c := 1; while c > 0 loop ")
                .append(longUpdateStatement.getUpdateSql())
                .append(" and rownum <= ")
                .append(longUpdateStatement.getCommitInterval())
                .append("; c := SQL%ROWCOUNT; COMMIT; dbms_lock.sleep(")
                .append(longUpdateStatement.getSleepSeconds())
                .append("); end loop; end;");


        return new Sql[]
                {
                        new UnparsedSql(sql.toString())
                };
    }
}

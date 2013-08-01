package liquibase.ext.ora.longupdate;

import liquibase.statement.AbstractSqlStatement;
import liquibase.statement.SqlStatement;

public class LongUpdateStatement extends AbstractSqlStatement {
    private Integer commitInterval;
    private Integer sleepSeconds;
    private String updateSql;

    public LongUpdateStatement(String updateSql, Integer commitInterval, Integer sleepSeconds) {
        this.commitInterval = commitInterval;
        this.sleepSeconds = sleepSeconds;
        this.updateSql = updateSql;
    }

    public Integer getCommitInterval() {
        return commitInterval;
    }

    public void setCommitInterval(Integer commitInterval) {
        this.commitInterval = commitInterval;
    }

    public Integer getSleepSeconds() {
        return sleepSeconds;
    }

    public void setSleepSeconds(Integer sleepSeconds) {
        this.sleepSeconds = sleepSeconds;
    }

    public String getUpdateSql() {
        return updateSql;
    }

    public void setUpdateSql(String updateSql) {
        this.updateSql = updateSql;
    }
}

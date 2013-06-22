package liquibase.ext.ora.longupdate;

import liquibase.change.AbstractChange;
import liquibase.change.ChangeMetaData;
import liquibase.change.DatabaseChange;
import liquibase.database.Database;
import liquibase.statement.SqlStatement;

@DatabaseChange(name="longUpdate", description = "Long Update", priority = ChangeMetaData.PRIORITY_DEFAULT)
public class LongUpdateChange extends AbstractChange {
    private Integer commitInterval;
    private Integer sleepSeconds;
    private String updateSql;

    public Integer getCommitInterval() {
        return this.commitInterval;
    }

    public void setCommitInterval(Integer commitInterval) {
        this.commitInterval = commitInterval;
    }

    public Integer getSleepSeconds() {
        return this.sleepSeconds;
    }

    public void setSleepSeconds(Integer sleepSeconds) {
        this.sleepSeconds = sleepSeconds;
    }

    public String getUpdateSql() {
        return this.updateSql;
    }

    public void setUpdateSql(String updateSql) {
        this.updateSql = updateSql;
    }

    public String getConfirmationMessage() {
        return "Update has been done";
    }

    public SqlStatement[] generateStatements(Database database) {
        LongUpdateStatement statement = new LongUpdateStatement(getUpdateSql(), getCommitInterval(), getSleepSeconds());
        return new SqlStatement[]{statement};
    }
}
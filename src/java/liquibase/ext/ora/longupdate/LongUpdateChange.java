package liquibase.ext.ora.longupdate;

import liquibase.change.AbstractChange;
import liquibase.change.ChangeMetaData;
import liquibase.database.Database;
import liquibase.statement.SqlStatement;

public class LongUpdateChange extends AbstractChange {
    private Integer commitInterval;
    private Integer sleepSeconds;
    private String updateSql;

    public LongUpdateChange() {
        super("longUpdate", "Long Update", ChangeMetaData.PRIORITY_DEFAULT);
    }

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
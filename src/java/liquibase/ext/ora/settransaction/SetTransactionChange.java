package liquibase.ext.ora.settransaction;

import liquibase.change.AbstractChange;
import liquibase.change.ChangeMetaData;
import liquibase.database.Database;
import liquibase.statement.SqlStatement;

public class SetTransactionChange extends AbstractChange {
    private String transactionName;
    private String rollbackSegment;
    private String isolationLevel;
    private String readOnlyWrite;

    public SetTransactionChange() {
        super("setTransaction", "Set Transaction", ChangeMetaData.PRIORITY_DEFAULT);
    }

    public String getTransactionName() {
        return transactionName;
    }

    public void setTransactionName(String transactionName) {
        this.transactionName = transactionName;
    }

    public String getRollbackSegment() {
        return rollbackSegment;
    }

    public void setRollbackSegment(String rollbackSegment) {
        this.rollbackSegment = rollbackSegment;
    }

    public String getIsolationLevel() {
        return isolationLevel;
    }

    public void setIsolationLevel(String isolationLevel) {
        this.isolationLevel = isolationLevel;
    }

    public String getReadOnlyWrite() {
        return readOnlyWrite;
    }

    public void setReadOnlyWrite(String readOnlyWrite) {
        this.readOnlyWrite = readOnlyWrite;
    }

    public String getConfirmationMessage() {
        return "Transaction has been set";
    }

    public SqlStatement[] generateStatements(Database database) {
        SetTransactionStatement statement = new SetTransactionStatement(getTransactionName(), getIsolationLevel(), getRollbackSegment(), getReadOnlyWrite());
        return new SqlStatement[]{statement};
    }

}

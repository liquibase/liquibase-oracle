package liquibase.ext.ora.settransaction;

import liquibase.statement.AbstractSqlStatement;
import liquibase.statement.SqlStatement;

public class SetTransactionStatement extends AbstractSqlStatement {
    private String transactionName;
    private String rollbackSegment;
    private String isolationLevel;
    private String readOnlyWrite;

    public SetTransactionStatement(String transactionName, String isolationLevel, String rollbackSegment, String readOnlyWrite) {
        this.transactionName = transactionName;
        this.rollbackSegment = rollbackSegment;
        this.isolationLevel = isolationLevel;
        this.readOnlyWrite = readOnlyWrite;
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


}

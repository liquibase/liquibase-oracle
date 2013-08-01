package liquibase.ext.ora.merge;

import liquibase.change.AbstractChange;
import liquibase.change.ChangeMetaData;
import liquibase.database.Database;
import liquibase.statement.SqlStatement;

public class MergeChange extends AbstractChange {

    public MergeChange() {
        super("merge", "merge", ChangeMetaData.PRIORITY_DEFAULT);
    }

    private String sourceTableName;
    private String sourceSchemaName;
    private String targetSchemaName;
    private String targetTableName;
    private String onCondition;
    private String updateCondition;
    private String deleteCondition;
    private String insertCondition;
    private String insertColumnsNameList;
    private String insertColumnsValueList;
    private String updateList;

    public String getSourceTableName() {
        return sourceTableName;
    }

    public void setSourceTableName(String sourceTableName) {
        this.sourceTableName = sourceTableName;
    }

    public String getSourceSchemaName() {
        return sourceSchemaName;
    }

    public void setSourceSchemaName(String sourceSchemaName) {
        this.sourceSchemaName = sourceSchemaName;
    }

    public String getTargetSchemaName() {
        return targetSchemaName;
    }

    public void setTargetSchemaName(String targetSchemaName) {
        this.targetSchemaName = targetSchemaName;
    }

    public String getTargetTableName() {
        return targetTableName;
    }

    public void setTargetTableName(String targetTableName) {
        this.targetTableName = targetTableName;
    }

    public String getOnCondition() {
        return onCondition;
    }

    public void setOnCondition(String onCondition) {
        this.onCondition = onCondition;
    }

    public String getUpdateCondition() {
        return updateCondition;
    }

    public void setUpdateCondition(String updateCondition) {
        this.updateCondition = updateCondition;
    }

    public String getDeleteCondition() {
        return deleteCondition;
    }

    public void setDeleteCondition(String deleteCondition) {
        this.deleteCondition = deleteCondition;
    }

    public String getInsertCondition() {
        return insertCondition;
    }

    public void setInsertCondition(String insertCondition) {
        this.insertCondition = insertCondition;
    }

    public String getInsertColumnsNameList() {
        return insertColumnsNameList;
    }

    public void setInsertColumnsNameList(String insertColumnsNameList) {
        this.insertColumnsNameList = insertColumnsNameList;
    }

    public String getInsertColumnsValueList() {
        return insertColumnsValueList;
    }

    public void setInsertColumnsValueList(String insertColumnsValueList) {
        this.insertColumnsValueList = insertColumnsValueList;
    }

    public String getUpdateList() {
        return updateList;
    }

    public void setUpdateList(String updateList) {
        this.updateList = updateList;
    }

    public SqlStatement[] generateStatements(Database database) {

        String sourceSchemaName = getSourceSchemaName() == null ? database.getDefaultSchemaName() : getSourceSchemaName();
        String targetSchemaName = getTargetSchemaName() == null ? database.getDefaultSchemaName() : getTargetSchemaName();

        MergeStatement statement = new MergeStatement(getSourceTableName(), sourceSchemaName, getTargetTableName(),
                targetSchemaName);
        statement.setOnCondition(getOnCondition());
        statement.setDeleteCondition(getDeleteCondition());
        statement.setInsertCondition(getInsertCondition());
        statement.setUpdateCondition(getUpdateCondition());
        statement.setInsertColumnsNameList(getInsertColumnsNameList());
        statement.setInsertColumnsValueList(getInsertColumnsValueList());
        statement.setUpdateList(getUpdateList());

        return new SqlStatement[]{statement};
    }

    public String getConfirmationMessage() {

        return "Tables " + getSourceTableName() + " & " + getTargetTableName() + " merged";
    }

}

package liquibase.ext.ora.check;

import liquibase.change.AbstractChange;
import liquibase.change.ChangeMetaData;
import liquibase.database.Database;
import liquibase.statement.SqlStatement;
import liquibase.util.StringUtils;

public class CheckAttribute extends AbstractChange {

    public CheckAttribute() {
        super("checkAttribute", "Check attribute",
                ChangeMetaData.PRIORITY_DEFAULT);
    }

    protected CheckAttribute(String changeName, String changeDescription,
                             int priority) {
        super(changeName, changeDescription, priority);
    }

    private String tableName;
    private String tablespace;
    private String schemaName;
    private String constraintName;
    private String condition;
    private Boolean disable;
    private Boolean deferrable;
    private Boolean initiallyDeferred;
    private Boolean rely;
    private Boolean validate;

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTablespace(String tablespace) {
        this.tablespace = tablespace;
    }

    public String getTablespace() {
        return tablespace;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = StringUtils.trimToNull(schemaName);
    }

    public void setConstraintName(String constraintName) {
        this.constraintName = constraintName;
    }

    public String getConstraintName() {
        return constraintName;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Boolean getDisable() {
        return disable;
    }

    public void setDisable(Boolean disable) {
        this.disable = disable;
    }

    public Boolean getDeferrable() {
        return deferrable;
    }

    public void setDeferrable(Boolean deferrable) {
        this.deferrable = deferrable;
    }

    public Boolean getInitiallyDeferred() {
        return initiallyDeferred;
    }

    public void setInitiallyDeferred(Boolean initiallyDeferred) {
        this.initiallyDeferred = initiallyDeferred;
    }

    public Boolean getValidate() {
        return validate;
    }

    public void setValidate(Boolean validate) {
        this.validate = validate;
    }

    public Boolean getRely() {
        return rely;
    }

    public void setRely(Boolean rely) {
        this.rely = rely;
    }

    public SqlStatement[] generateStatements(Database database) {

        return null;
    }

    public String getConfirmationMessage() {
        return null;
    }

}

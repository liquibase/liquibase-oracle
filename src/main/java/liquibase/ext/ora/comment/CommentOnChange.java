package liquibase.ext.ora.comment;

import liquibase.change.AbstractChange;
import liquibase.change.Change;
import liquibase.change.ChangeMetaData;
import liquibase.change.DatabaseChange;
import liquibase.database.Database;
import liquibase.statement.SqlStatement;


@DatabaseChange(name="commentOn", description = "Create or replace a comment on a table or a column", priority = ChangeMetaData.PRIORITY_DEFAULT + 200)
public class CommentOnChange extends AbstractChange {

    private String schemaName;
    private String tableName;
    private String columnName;
    private String comment;

    public CommentOnChange() {
    }

    public SqlStatement[] generateStatements(Database database) {

        String schemaName = getSchemaName() == null ? database.getDefaultSchemaName() : getSchemaName();
        CommentOnStatement statement = new CommentOnStatement(schemaName, getTableName(), getColumnName(), getComment() );
        return new SqlStatement[]{statement};
    }

    public String getConfirmationMessage() {
        return columnName == null
                ? "Comment has been added to " + getTableName()
                : "Comment has been added to " + getTableName() + "." + getColumnName();
    }

    // Since "COMMENT ON" replaces the existing comment is is not good to remove it as we don;t know what the last one was.
    protected Change[] createInverses() {
        return new Change[]{};
    }

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}

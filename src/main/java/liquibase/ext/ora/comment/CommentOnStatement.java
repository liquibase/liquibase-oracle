package liquibase.ext.ora.comment;

import liquibase.ext.ora.check.CheckState;
import liquibase.statement.AbstractSqlStatement;
import liquibase.statement.SqlStatement;

public class CommentOnStatement extends AbstractSqlStatement implements SqlStatement {

    private String schemaName;
    private String tableName;
    private String columnName;
    private String comment;

    public CommentOnStatement(String schemaName, String tableName, String columnName, String comment) {
        this.schemaName = schemaName;
        this.tableName = tableName;
        this.columnName = columnName;
        this.comment = comment;
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

    public String getColumnName() {
        return columnName;
    }


    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isColumnComment() {
        return columnName != null;
    }

}

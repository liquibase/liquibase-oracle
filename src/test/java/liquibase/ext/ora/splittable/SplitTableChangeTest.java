package liquibase.ext.ora.splittable;

import static org.junit.Assert.assertEquals;

import liquibase.change.ChangeFactory;
import liquibase.change.ChangeMetaData;
import liquibase.database.core.OracleDatabase;
import liquibase.ext.ora.testing.BaseTestCase;
import liquibase.statement.SqlStatement;

import org.junit.Before;
import org.junit.Test;

public class SplitTableChangeTest extends BaseTestCase {

    @Before
    public void setUp() throws Exception {
        changeLogFile = "liquibase/ext/ora/splittable/changelog.test.xml";
        connectToDB();
        cleanDB();
    }

    @Test
    public void test() throws Exception {
        liquiBase.update(null);
    }

    @Test
    public void generateStatement() {
        SplitTableChange change = new SplitTableChange();

        change.setSplitTableSchemaName("SPLIT_TABLE_SCHEMA_NAME");
        change.setSplitTableName("SPLIT_TABLE_NAME");

        change.setNewTableSchemaName("NEW_TABLE_SCHEMA_NAME");
        change.setNewTableName("NEW_TABLE_NAME");

        change.setColumnNameList("FIRST,SECOND");
        change.setPrimaryKeyColumnName("FIRST");

        SqlStatement[] statements = change.generateStatements(new OracleDatabase());
        assertEquals(1, statements.length);
        SplitTableStatement statement = (SplitTableStatement) statements[0];

        assertEquals("SPLIT_TABLE_SCHEMA_NAME", statement.getSplitTableSchemaName());
        assertEquals("SPLIT_TABLE_NAME", statement.getSplitTableName());

        assertEquals("NEW_TABLE_SCHEMA_NAME", statement.getNewTableSchemaName());
        assertEquals("NEW_TABLE_NAME", statement.getNewTableName());

        assertEquals("FIRST,SECOND", statement.getColumnNameList());
        assertEquals("FIRST", statement.getPrimaryKeyColumnName());
    }

    @Test
    public void getConfirmationMessage() {
        SplitTableChange change = new SplitTableChange();

        change.setSplitTableName("SPLIT_TABLE_NAME");
        change.setNewTableName("NEW_TABLE_NAME");

        assertEquals(change.getSplitTableName() + " splitted. Created new table " + change.getNewTableName(), change
                .getConfirmationMessage());
    }

    @Test
    public void getChangeMetaData() {
        SplitTableChange change = new SplitTableChange();

        assertEquals("splitTable", ChangeFactory.getInstance().getChangeMetaData(change).getName());
        assertEquals("split table", ChangeFactory.getInstance().getChangeMetaData(change).getDescription());
        assertEquals(ChangeMetaData.PRIORITY_DEFAULT, ChangeFactory.getInstance().getChangeMetaData(change).getPriority());
    }
}

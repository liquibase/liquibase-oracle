package liquibase.ext.ora.createtrigger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import liquibase.change.Change;
import liquibase.change.ChangeFactory;
import liquibase.change.ChangeMetaData;
import liquibase.changelog.ChangeLogIterator;
import liquibase.changelog.ChangeLogParameters;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.database.Database;
import liquibase.database.core.OracleDatabase;
import liquibase.ext.ora.testing.BaseTestCase;
import liquibase.parser.ChangeLogParserFactory;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.resource.ResourceAccessor;
import liquibase.sql.Sql;
import liquibase.sqlgenerator.SqlGeneratorFactory;
import liquibase.statement.SqlStatement;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CreateTriggerTest extends BaseTestCase {

    @Before
    public void setUp() throws Exception {
        changeLogFile = "liquibase/ext/ora/createtrigger/changelog.test.xml";
        connectToDB();
        cleanDB();
    }

    @Test
    public void getChangeMetaData() {
        CreateTriggerChange createTriggerChange = new CreateTriggerChange();

        assertEquals("createTrigger", ChangeFactory.getInstance().getChangeMetaData(createTriggerChange).getName());
        assertEquals("Create Trigger", ChangeFactory.getInstance().getChangeMetaData(createTriggerChange).getDescription());
        assertEquals(ChangeMetaData.PRIORITY_DEFAULT, ChangeFactory.getInstance().getChangeMetaData(createTriggerChange).getPriority());
    }

    @Test
    public void getConfirmationMessage() {
        CreateTriggerChange change = new CreateTriggerChange();
        change.setTriggerName("TRIGGER_NAME");

        assertEquals("Trigger TRIGGER_NAME has been created", change.getConfirmationMessage());
    }

    @Test
    public void generateStatement() {

        CreateTriggerChange change = new CreateTriggerChange();
        change.setSchemaName("SCHEMA_NAME");
        change.setTableName("TABLE_NAME");
        change.setColumnNames("COLUMN_NAMES");
        change.setTablespace("TABLESPACE_NAME");
        change.setAfterBeforeInsteadOf("AFTER_BEFORE_INSTEADOF");
        change.setNestedTableColumn("NESTED_TABLE_COLUMN");
        change.setProcedure("POCEDURE");
        change.setTriggerName("TRIGGER_NAME");
        change.setViewName("VIEW_NAME");
        change.setWhenCondition("WHEN_CONDITION");

        change.setUpdate(true);
        change.setUpdateOf(true);
        change.setReplace(true);
        change.setDelete(true);
        change.setForEachRow(true);
        change.setInsert(true);

        Database database = new OracleDatabase();
        SqlStatement[] sqlStatements = change.generateStatements(database);

        assertEquals(1, sqlStatements.length);
        assertTrue(sqlStatements[0] instanceof CreateTriggerStatement);

        CreateTriggerStatement sqlStatement = (CreateTriggerStatement) sqlStatements[0];
        assertEquals("SCHEMA_NAME", sqlStatement.getSchemaName());
        assertEquals("TABLE_NAME", sqlStatement.getTableName());
        assertEquals("COLUMN_NAMES", sqlStatement.getColumnNames());
        assertEquals("TABLESPACE_NAME", sqlStatement.getTablespace());
        assertEquals("AFTER_BEFORE_INSTEADOF", sqlStatement.getAfterBeforeInsteadOf());
        assertEquals("NESTED_TABLE_COLUMN", sqlStatement.getNestedTableColumn());
        assertEquals("POCEDURE", sqlStatement.getProcedure());
        assertEquals("TRIGGER_NAME", sqlStatement.getTriggerName());
        assertEquals("VIEW_NAME", sqlStatement.getViewName());
        assertEquals("WHEN_CONDITION", sqlStatement.getWhenCondition());

        assertTrue(sqlStatement.getUpdate());
        assertTrue(sqlStatement.getUpdateOf());
        assertTrue(sqlStatement.getReplace());
        assertTrue(sqlStatement.getDelete());
        assertTrue(sqlStatement.getForEachRow());
        assertTrue(sqlStatement.getInsert());
    }

    @Test
    public void parseAndGenerate() throws Exception {
        if (connection == null) {
            return;
        }

        Database database = liquiBase.getDatabase();
        ResourceAccessor resourceAccessor = new ClassLoaderResourceAccessor();

        ChangeLogParameters changeLogParameters = new ChangeLogParameters();


        DatabaseChangeLog changeLog = ChangeLogParserFactory.getInstance().getParser(changeLogFile, resourceAccessor).parse(changeLogFile,
                changeLogParameters, resourceAccessor);

        changeLog.validate(database);


        List<ChangeSet> changeSets = changeLog.getChangeSets();
        List<String> expectedQuery = new ArrayList<String>();

        expectedQuery
                .add("CREATE TRIGGER zuiolTrigger before INSERT ON TriggerTest FOR EACH ROW DECLARE v_username varchar2(10); BEGIN SELECT pierwsza INTO v_username FROM TriggerTest; :new.created_by := v_username; END;");

        int i = 0;

        for (ChangeSet changeSet : changeSets) {
            for (Change change : changeSet.getChanges()) {
                Sql[] sql = SqlGeneratorFactory.getInstance().generateSql(change.generateStatements(database)[0], database);
                if (i == 2) {
                    assertEquals(expectedQuery.get(0), sql[0].toSql());
                }
            }
            i++;
        }
    }

    @Test
    public void test() throws Exception {
        if (connection == null) {
            return;
        }
        liquiBase.update((String) null);
    }

}

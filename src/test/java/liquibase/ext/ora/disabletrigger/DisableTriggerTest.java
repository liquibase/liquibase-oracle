package liquibase.ext.ora.disabletrigger;

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

import org.junit.Before;
import org.junit.Test;

public class DisableTriggerTest extends BaseTestCase {

    @Before
    public void setUp() throws Exception {
        changeLogFile = "liquibase/ext/ora/disabletrigger/changelog.test.xml";
        connectToDB();
        cleanDB();
    }

    @Test
    public void getChangeMetaData() {
        DisableTriggerChange disableTriggerChange = new DisableTriggerChange();

        assertEquals("disableTrigger", ChangeFactory.getInstance().getChangeMetaData(disableTriggerChange).getName());
        assertEquals("Disable Trigger", ChangeFactory.getInstance().getChangeMetaData(disableTriggerChange).getDescription());
        assertEquals(ChangeMetaData.PRIORITY_DEFAULT, ChangeFactory.getInstance().getChangeMetaData(disableTriggerChange).getPriority());
    }

    @Test
    public void getConfirmationMessage() {
        DisableTriggerChange change = new DisableTriggerChange();
        change.setTriggerName("TRIGGER_NAME");

        assertEquals("Trigger has been disabled.", change.getConfirmationMessage());
    }

    @Test
    public void generateStatement() {

        DisableTriggerChange change = new DisableTriggerChange();
        change.setSchemaName("SCHEMA_NAME");
        change.setTriggerName("TRIGGER_NAME");

        Database database = new OracleDatabase();
        SqlStatement[] sqlStatements = change.generateStatements(database);

        assertEquals(1, sqlStatements.length);
        assertTrue(sqlStatements[0] instanceof DisableTriggerStatement);

        assertEquals("SCHEMA_NAME", ((DisableTriggerStatement) sqlStatements[0]).getSchemaName());
        assertEquals("TRIGGER_NAME", ((DisableTriggerStatement) sqlStatements[0]).getTriggerName());

    }

    @Test
    public void parseAndGenerate() throws Exception {

        Database database = liquiBase.getDatabase();
        ResourceAccessor resourceAccessor = new ClassLoaderResourceAccessor();

        ChangeLogParameters changeLogParameters = new ChangeLogParameters();


        DatabaseChangeLog changeLog = ChangeLogParserFactory.getInstance().getParser(changeLogFile, resourceAccessor).parse(changeLogFile,
                changeLogParameters, resourceAccessor);

        changeLog.validate(database);


        List<ChangeSet> changeSets = changeLog.getChangeSets();

        List<String> expectedQuery = new ArrayList<String>();

        expectedQuery.add("ALTER TRIGGER RenamedZuiolTrigger DISABLE");

        int i = 0;

        for (ChangeSet changeSet : changeSets) {
            for (Change change : changeSet.getChanges()) {
                Sql[] sql = SqlGeneratorFactory.getInstance().generateSql(change.generateStatements(database)[0], database);
                if (i == 5) {
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
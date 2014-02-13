package liquibase.ext.ora.droptrigger;

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

public class DropTriggerTest extends BaseTestCase {

    @Before
    public void setUp() throws Exception {
        changeLogFile = "liquibase/ext/ora/droptrigger/changelog.test.xml";
        connectToDB();
        cleanDB();
    }

    @Test
    public void getChangeMetaData() {
        DropTriggerChange dropTriggerChange = new DropTriggerChange();

        assertEquals("dropTrigger", ChangeFactory.getInstance().getChangeMetaData(dropTriggerChange).getName());
        assertEquals("Drop trigger", ChangeFactory.getInstance().getChangeMetaData(dropTriggerChange).getDescription());
        assertEquals(ChangeMetaData.PRIORITY_DEFAULT, ChangeFactory.getInstance().getChangeMetaData(dropTriggerChange).getPriority());
    }

    @Test
    public void getConfirmationMessage() {
        DropTriggerChange change = new DropTriggerChange();
        change.setTriggerName("TRIGGER_NAME");

        assertEquals("Trigger TRIGGER_NAME has been droped", "Trigger " + change.getTriggerName() + " has been droped");
    }

    @Test
    public void generateStatement() {

        DropTriggerChange change = new DropTriggerChange();
        change.setSchemaName("SCHEMA_NAME");
        change.setTriggerName("TRIGGER_NAME");

        Database database = new OracleDatabase();
        SqlStatement[] sqlStatements = change.generateStatements(database);

        assertEquals(1, sqlStatements.length);
        assertTrue(sqlStatements[0] instanceof DropTriggerStatement);

        assertEquals("SCHEMA_NAME", ((DropTriggerStatement) sqlStatements[0]).getSchemaName());
        assertEquals("TRIGGER_NAME", ((DropTriggerStatement) sqlStatements[0]).getTriggerName());

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

        expectedQuery.add("DROP TRIGGER LIQUIBASE.zuiolTrigger");

        int i = 0;

        for (ChangeSet changeSet : changeSets) {
            for (Change change : changeSet.getChanges()) {
                Sql[] sql = SqlGeneratorFactory.getInstance().generateSql(change.generateStatements(database)[0], database);
                if (i == 4) {
                    assertEquals(expectedQuery.get(0), sql[0].toSql());
                }
            }
            i++;
        }
    }

    @Test
    public void test() throws Exception {
        liquiBase.update((String) null);
    }
}

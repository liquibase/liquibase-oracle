package liquibase.ext.ora.renametrigger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import liquibase.Scope;
import liquibase.change.Change;
import liquibase.change.ChangeFactory;
import liquibase.change.ChangeMetaData;
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

public class RenameTriggerTest extends BaseTestCase {

    @Before
    public void setUp() throws Exception {
        changeLogFile = "liquibase/ext/ora/renametrigger/changelog.test.xml";
        connectToDB();
        cleanDB();
    }

    @Test
    public void getChangeMetaData() {
        RenameTriggerChange renameTriggerChange = new RenameTriggerChange();

        assertEquals("renameTrigger", Scope.getCurrentScope().getSingleton(ChangeFactory.class).getChangeMetaData(renameTriggerChange).getName());
        assertEquals("Rename Trigger", Scope.getCurrentScope().getSingleton(ChangeFactory.class).getChangeMetaData(renameTriggerChange).getDescription());
        assertEquals(ChangeMetaData.PRIORITY_DEFAULT + 200, Scope.getCurrentScope().getSingleton(ChangeFactory.class).getChangeMetaData(renameTriggerChange).getPriority());
    }

    @Test
    public void getConfirmationMessage() {
        RenameTriggerChange change = new RenameTriggerChange();
        change.setTriggerName("TRIGGER_NAME");

        assertEquals("Trigger has been renamed.", change.getConfirmationMessage());
    }

    @Test
    public void generateStatement() {

        RenameTriggerChange change = new RenameTriggerChange();
        change.setSchemaName("SCHEMA_NAME");
        change.setTriggerName("TRIGGER_NAME");
        change.setNewName("NEW_NAME");

        Database database = new OracleDatabase();
        SqlStatement[] sqlStatements = change.generateStatements(database);

        assertEquals(1, sqlStatements.length);
        assertTrue(sqlStatements[0] instanceof RenameTriggerStatement);

        assertEquals("SCHEMA_NAME", ((RenameTriggerStatement) sqlStatements[0]).getSchemaName());
        assertEquals("TRIGGER_NAME", ((RenameTriggerStatement) sqlStatements[0]).getTriggerName());
        assertEquals("NEW_NAME", ((RenameTriggerStatement) sqlStatements[0]).getNewName());

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

        expectedQuery.add("ALTER TRIGGER zuiolTrigger RENAME TO RenamedZuiolTrigger");

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
        if (connection == null) {
            return;
        }
        liquiBase.update((String) null);
    }
}

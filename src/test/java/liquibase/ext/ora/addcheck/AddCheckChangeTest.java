package liquibase.ext.ora.addcheck;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

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
import liquibase.resource.FileSystemResourceAccessor;
import liquibase.resource.ResourceAccessor;
import liquibase.sql.Sql;
import liquibase.sqlgenerator.SqlGeneratorFactory;
import liquibase.statement.SqlStatement;

import org.junit.Before;
import org.junit.Test;

public class AddCheckChangeTest extends BaseTestCase {

    @Before
    public void setUp() throws Exception {
        changeLogFile = "liquibase/ext/ora/addcheck/changelog.test.xml";
        connectToDB();
        cleanDB();
    }

    @Test
    public void test() throws Exception {
        if (connection == null) {
            return;
        }
        liquiBase.update("null");

    }

    @Test
    public void generateStatement() {
        AddCheckChange change = new AddCheckChange();

        change.setSchemaName("SCHEMA_NAME");
        change.setTableName("TABLE_NAME");
        change.setConstraintName("CONSTRAINT");
        change.setCondition("CONDITION");
        change.setDeferrable(true);
        change.setDisable(true);
        change.setInitiallyDeferred(true);
        change.setRely(false);
        change.setValidate(false);

        SqlStatement[] statements = change.generateStatements(new OracleDatabase());
        assertEquals(1, statements.length);
        AddCheckStatement statement = (AddCheckStatement) statements[0];

        assertEquals("SCHEMA_NAME", statement.getSchemaName());
        assertEquals("TABLE_NAME", statement.getTableName());
        assertEquals("CONSTRAINT", statement.getConstraintName());
        assertEquals("CONDITION", statement.getCondition());
        assertEquals(true, statement.getDeferrable());
        assertEquals(true, statement.getDisable());
        assertEquals(true, statement.getInitiallyDeferred());
        assertEquals(false, statement.getRely());
        assertEquals(false, statement.getValidate());
    }

    @Test
    public void getConfirmationMessage() {
        AddCheckChange change = new AddCheckChange();

        change.setTableName("TABLE_NAME");
        change.setConstraintName("CONSTRAINT");

        assertEquals("Constraint check " + change.getConstraintName() + " has been added to " + change.getTableName(),
                change.getConfirmationMessage());
    }

    @Test
    public void getChangeMetaData() {
        AddCheckChange enableCheckChange = new AddCheckChange();

        assertEquals("addCheck", ChangeFactory.getInstance().getChangeMetaData(enableCheckChange).getName());
        assertEquals("Add Check", ChangeFactory.getInstance().getChangeMetaData(enableCheckChange).getDescription());
        assertEquals(ChangeMetaData.PRIORITY_DEFAULT, ChangeFactory.getInstance().getChangeMetaData(enableCheckChange).getPriority());
    }

    @Test
    public void parseAndGenerate() throws Exception {
        Database database = liquiBase.getDatabase();
        ResourceAccessor resourceAccessor = new FileSystemResourceAccessor("src/test/java");

        ChangeLogParameters changeLogParameters = new ChangeLogParameters();


        DatabaseChangeLog changeLog = ChangeLogParserFactory.getInstance().getParser(changeLogFile, resourceAccessor).parse(changeLogFile,
                changeLogParameters, resourceAccessor);

        changeLog.validate(database);

        List<ChangeSet> changeSets = changeLog.getChangeSets();

        List<String> expectedQuery = new ArrayList<String>();

        // expectedQuery.add("ALTER TABLE addcheck ADD CONSTRAINT tom_check CHECK(id between 0 and 5 ) DEFERRABLE INITIALLY DEFERRED DISABLE");
        // expectedQuery.add("ALTER TABLE addcheck ADD CONSTRAINT tom_check1 CHECK(id between 10 and 15) ENABLE");
        expectedQuery.add("ALTER TABLE LBUSER.addcheck ADD CHECK(id between 0 and 5 ) DEFERRABLE INITIALLY DEFERRED DISABLE");

        ChangeSet changeSet = changeSets.get(1);
        Change change = changeSet.getChanges().get(0);
        Sql[] sql = SqlGeneratorFactory.getInstance().generateSql(change.generateStatements(database)[0], database);
        assertEquals(expectedQuery.get(0), sql[0].toSql());
    }
}

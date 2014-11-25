package liquibase.ext.ora.dropcheck;

import static org.junit.Assert.assertEquals;

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

public class DropCheckChangeTest extends BaseTestCase {

    @Before
    public void setUp() throws Exception {
        changeLogFile = "liquibase/ext/ora/dropcheck/changelog.test.xml";
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
        DropCheckChange change = new DropCheckChange();

        change.setSchemaName("SCHEMA_NAME");
        change.setTableName("TABLE_NAME");

        change.setConstraintName("CONSTRAINT");

        SqlStatement[] statements = change.generateStatements(new OracleDatabase());
        assertEquals(1, statements.length);
        DropCheckStatement statement = (DropCheckStatement) statements[0];

        assertEquals("SCHEMA_NAME", statement.getSchemaName());
        assertEquals("TABLE_NAME", statement.getTableName());

        assertEquals("CONSTRAINT", statement.getConstraintName());
    }

    @Test
    public void getConfirmationMessage() {
        DropCheckChange change = new DropCheckChange();

        change.setTableName("TABLE_NAME");
        change.setConstraintName("CONSTRAINT");

        assertEquals(change.getConstraintName() + " check DROPPED from " + change.getTableName(), change
                .getConfirmationMessage());
    }

    @Test
    public void getChangeMetaData() {
        DropCheckChange dropCheckChange = new DropCheckChange();

        assertEquals("dropCheck", ChangeFactory.getInstance().getChangeMetaData(dropCheckChange).getName());
        assertEquals("Drop check", ChangeFactory.getInstance().getChangeMetaData(dropCheckChange).getDescription());
        assertEquals(ChangeMetaData.PRIORITY_DEFAULT, ChangeFactory.getInstance().getChangeMetaData(dropCheckChange).getPriority());
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

        expectedQuery.add("ALTER TABLE LBUSER.test drop CONSTRAINT tom_check");

        int i = 0;

        for (ChangeSet changeSet : changeSets) {
            for (Change change : changeSet.getChanges()) {
                Sql[] sql = SqlGeneratorFactory.getInstance().generateSql(change.generateStatements(database)[0], database);
                if (i == 1) {
                    assertEquals(expectedQuery.get(i - 1), sql[0].toSql());
                }
            }
            i++;
        }
    }
}
package liquibase.ext.ora.grant.addgrant;

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

public class GrantObjectPermissionChangeTest extends BaseTestCase {

	@Before
	public void setUp() throws Exception {
        changeLogFile = "liquibase/ext/ora/addgrant/changelog.test.xml";
        connectToDB();
        cleanDB();
	}

    @Test
    public void test() throws Exception {
        liquiBase.update("null");
    }

    @Test
    public void generateStatement() {
        GrantObjectPermissionChange change = new GrantObjectPermissionChange();

        change.setSchemaName("SCHEMA_NAME");
        change.setObjectName("TABLE_NAME");
        change.setRecipientList("RECIPIENT_USER");
        change.setSelect(true);
        change.setUpdate(true);
        change.setInsert(true);
        change.setDelete(true);
        change.setExecute(true);

        SqlStatement[] statements = change.generateStatements(new OracleDatabase());
        assertEquals(1, statements.length);
        GrantObjectPermissionStatement statement = (GrantObjectPermissionStatement) statements[0];

        assertEquals("SCHEMA_NAME", statement.getSchemaName());
        assertEquals("TABLE_NAME", statement.getObjectName());
        assertEquals("RECIPIENT_USER", statement.getRecipientList());
        assertEquals(true, statement.getSelect());
        assertEquals(true, statement.getUpdate());
        assertEquals(true, statement.getInsert());
        assertEquals(true, statement.getDelete());
        assertEquals(true, statement.getExecute());
    }

    @Test
    public void getConfirmationMessage() {
    	GrantObjectPermissionChange change = new GrantObjectPermissionChange();

        change.setObjectName("TABLE_NAME");
        change.setRecipientList("RECIPIENT_USER");

        assertEquals("Grants on " + change.getObjectName() + " have been given to " + change.getRecipientList(),
                change.getConfirmationMessage());
    }

    @Test
    public void getChangeMetaData() {
    	GrantObjectPermissionChange change = new GrantObjectPermissionChange();

        assertEquals("grantObjectPermission", ChangeFactory.getInstance().getChangeMetaData(change).getName());
        assertEquals("Grant Schema Object Permission", ChangeFactory.getInstance().getChangeMetaData(change).getDescription());
        assertEquals(ChangeMetaData.PRIORITY_DEFAULT, ChangeFactory.getInstance().getChangeMetaData(change).getPriority());
    }

    @Test
    public void parseAndGenerate() throws Exception {
        Database database = liquiBase.getDatabase();
        ResourceAccessor resourceAccessor = new FileSystemResourceAccessor("src/test/java");

        ChangeLogParameters changeLogParameters = new ChangeLogParameters();

        DatabaseChangeLog changeLog = ChangeLogParserFactory.getInstance()
        		.getParser(changeLogFile, resourceAccessor)
        		.parse(changeLogFile,changeLogParameters, resourceAccessor);

        changeLog.validate(database);

        List<ChangeSet> changeSets = changeLog.getChangeSets();
        assertEquals( "number of changesets in the " + changeLogFile + " is incorrect", 2, changeSets.size() );
        ChangeSet changeSet = changeSets.get(1);

        assertEquals("Wrong number of changes found in changeset", 1, changeSet.getChanges().size());
        Change change = changeSet.getChanges().get(0);

        List<String> expectedQueries = new ArrayList<String>();
        expectedQueries.add("GRANT SELECT,UPDATE,INSERT,DELETE ON LIQUIBASE.addgrant TO SYSTEM");

        Sql[] sql = SqlGeneratorFactory.getInstance().generateSql(change.generateStatements(database)[0], database);
        assertEquals( "wrong number of statements generated", expectedQueries.size(), sql.length );
        assertEquals(expectedQueries.get(0), sql[0].toSql());
    }

}

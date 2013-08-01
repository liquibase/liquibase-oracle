package liquibase.ext.ora.truncate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import liquibase.change.Change;
import liquibase.change.ChangeMetaData;
import liquibase.changelog.ChangeLogIterator;
import liquibase.changelog.ChangeLogParameters;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.database.Database;
import liquibase.database.core.OracleDatabase;
import liquibase.ext.ora.test.BaseTest;
import liquibase.parser.ChangeLogParserFactory;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.resource.ResourceAccessor;
import liquibase.sql.Sql;
import liquibase.sqlgenerator.SqlGeneratorFactory;
import liquibase.statement.SqlStatement;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TruncateChangeTest extends BaseTest {

    @Before
    public void setUp() throws Exception {
        changeLogFile = "liquibase/ext/ora/truncate/changelog.test.xml";
        BaseTest.connectToDB();
        cleanDB();
    }

    @Test
    public void getChangeMetaData() {
        TruncateChange truncateChange = new TruncateChange();

        assertEquals("truncate", truncateChange.getChangeMetaData().getName());
        assertEquals("Truncate", truncateChange.getChangeMetaData().getDescription());
        assertEquals(ChangeMetaData.PRIORITY_DEFAULT, truncateChange.getChangeMetaData().getPriority());
    }

    @Test
    public void getConfirmationMessage() {
        TruncateChange change = new TruncateChange();
        change.setTableName("TABLE_NAME");

        assertEquals("Table TABLE_NAME truncated", change.getConfirmationMessage());
    }

    @Test
    public void generateStatement() {
        TruncateChange change = new TruncateChange();
        change.setSchemaName("SCHEMA_NAME");
        change.setTableName("TABLE_NAME");
        change.setClusterName("CLUSTER_NAME");

        change.setPurgeMaterializedViewLog(true);
        change.setReuseStorage(true);

        Database database = new OracleDatabase();
        SqlStatement[] sqlStatements = change.generateStatements(database);

        assertEquals(1, sqlStatements.length);
        assertTrue(sqlStatements[0] instanceof TruncateStatement);

        assertEquals("SCHEMA_NAME", ((TruncateStatement) sqlStatements[0]).getSchemaName());
        assertEquals("TABLE_NAME", ((TruncateStatement) sqlStatements[0]).getTableName());
        assertEquals("CLUSTER_NAME", ((TruncateStatement) sqlStatements[0]).getClusterName());

        assertTrue(((TruncateStatement) sqlStatements[0]).purgeMaterializedViewLog());
        assertTrue(((TruncateStatement) sqlStatements[0]).reuseStorage());
    }

    @Test
    public void parseAndGenerate() throws Exception {
        Database database = liquiBase.getDatabase();
        ResourceAccessor resourceAccessor = new ClassLoaderResourceAccessor();

        ChangeLogParameters changeLogParameters = new ChangeLogParameters();


        DatabaseChangeLog changeLog = ChangeLogParserFactory.getInstance().getParser(changeLogFile, resourceAccessor).parse(changeLogFile,
                changeLogParameters, resourceAccessor);

        database.checkDatabaseChangeLogTable(false, changeLog, null);
        changeLog.validate(database);


        List<ChangeSet> changeSets = changeLog.getChangeSets();

        List<String> expectedQuery = new ArrayList<String>();

        expectedQuery.add("CREATE TABLE truncatetest (id INT, name varchar2(50))");
        expectedQuery.add("insert into truncatetest (id, name) values (1, 'dgolda')");
        expectedQuery.add("TRUNCATE TABLE truncatetest PURGE MATERIALIZED VIEW LOG REUSE STORAGE");
        // expectedQuery.add("TRUNCATE TABLE artur.truncatetest PURGE MATERIALIZED VIEW LOG REUSE STORAGE");

        int i = 0;

        for (ChangeSet changeSet : changeSets) {
            for (Change change : changeSet.getChanges()) {
                // Change change = changeSet.getChanges().get(0);
                Sql[] sql = SqlGeneratorFactory.getInstance().generateSql(change.generateStatements(database)[0], database);

                assertEquals(expectedQuery.get(i), sql[0].toSql());
            }
            i++;
        }
    }

    @Test
    public void test() throws Exception {
        liquiBase.update(null);
        dbTest();
    }

    public void dbTest() {
        Statement statement;
        ResultSet resultSet;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM truncatetest");
            assertTrue("Table is not empty after truncate.", !resultSet.next());
        } catch (SQLException e) {
            Assert.fail("dbTest Fail!\n" + e.getMessage());
        }
    }
}
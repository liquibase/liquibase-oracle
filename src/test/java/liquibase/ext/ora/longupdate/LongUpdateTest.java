package liquibase.ext.ora.longupdate;

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
import liquibase.exception.LiquibaseException;
import liquibase.ext.ora.testing.BaseTestCase;
import liquibase.parser.ChangeLogParserFactory;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.resource.ResourceAccessor;
import liquibase.sql.Sql;
import liquibase.sqlgenerator.SqlGeneratorFactory;
import liquibase.statement.SqlStatement;

import org.junit.Before;
import org.junit.Test;

public class LongUpdateTest extends BaseTestCase {

    @Before
    public void setUp() throws Exception {
        changeLogFile = "liquibase/ext/ora/longupdate/changelog.test.xml";
        connectToDB();
        cleanDB();
    }

    @Test
    public void getChangeMetaData() {
        LongUpdateChange longUpdateChange = new LongUpdateChange();

        assertEquals("longUpdate", Scope.getCurrentScope().getSingleton(ChangeFactory.class).getChangeMetaData(longUpdateChange).getName());
        assertEquals("Long Update", Scope.getCurrentScope().getSingleton(ChangeFactory.class).getChangeMetaData(longUpdateChange).getDescription());
        assertEquals(ChangeMetaData.PRIORITY_DEFAULT + 200, Scope.getCurrentScope().getSingleton(ChangeFactory.class).getChangeMetaData(longUpdateChange).getPriority());
    }

    @Test
    public void getConfirmationMessage() {
        LongUpdateChange change = new LongUpdateChange();

        assertEquals("Update has been done", change.getConfirmationMessage());
    }

    @Test
    public void generateStatement() {

        LongUpdateChange change = new LongUpdateChange();
        change.setCommitInterval(2);
        change.setSleepSeconds(2);
        change.setUpdateSql("UPDATE_SQL");

        Database database = new OracleDatabase();
        SqlStatement[] sqlStatements = change.generateStatements(database);

        assertEquals(1, sqlStatements.length);
        assertTrue(sqlStatements[0] instanceof LongUpdateStatement);

        assertEquals(Integer.valueOf(2), ((LongUpdateStatement) sqlStatements[0]).getCommitInterval());
        assertEquals(Integer.valueOf(2), ((LongUpdateStatement) sqlStatements[0]).getSleepSeconds());
        assertEquals("UPDATE_SQL", ((LongUpdateStatement) sqlStatements[0]).getUpdateSql());

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
                .add("declare commit_interval  integer; sleep_seconds integer; c integer; begin commit_interval := 5; sleep_seconds := 1; c := 1; while c > 0 loop UPDATE LongUpdateTest SET name='checked' where name='test' and rownum <= 5; c := SQL%ROWCOUNT; COMMIT; dbms_lock.sleep(1); end loop; end;");

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

        try {
            liquiBase.update((String) null);
        } catch (LiquibaseException e) {
            System.out.println("Error executing LongUpdateTest. If the error is: \"identifier 'DBMS_LOCK' must be declared\" you need to run 'GRANT exectue ON DBMS_LOCK to LBUSER' with SYS as SYSDBA");
            throw e;
        }
    }
}

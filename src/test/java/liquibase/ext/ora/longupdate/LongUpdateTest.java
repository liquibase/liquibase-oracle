package liquibase.ext.ora.longupdate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import liquibase.Contexts;
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

        assertEquals("longUpdate", ChangeFactory.getInstance().getChangeMetaData(longUpdateChange).getName());
        assertEquals("Long Update", ChangeFactory.getInstance().getChangeMetaData(longUpdateChange).getDescription());
        assertEquals(ChangeMetaData.PRIORITY_DEFAULT, ChangeFactory.getInstance().getChangeMetaData(longUpdateChange).getPriority());
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
        Database database = liquiBase.getDatabase();
        ResourceAccessor resourceAccessor = new ClassLoaderResourceAccessor();

        ChangeLogParameters changeLogParameters = new ChangeLogParameters();


        DatabaseChangeLog changeLog = ChangeLogParserFactory.getInstance().getParser(changeLogFile, resourceAccessor).parse(changeLogFile,
                changeLogParameters, resourceAccessor);

        liquiBase.checkLiquibaseTables( false, changeLog, new Contexts() );
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
        liquiBase.update(new Contexts());
    }
}
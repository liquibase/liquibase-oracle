package liquibase.ext.ora.settransaction;

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

public class SetTransactionTest extends BaseTestCase {

    @Before
    public void setUp() throws Exception {
        changeLogFile = "liquibase/ext/ora/settransaction/changelog.test.xml";
        connectToDB();
        cleanDB();
    }

    @Test
    public void getChangeMetaData() {
        SetTransactionChange setTransactionChange = new SetTransactionChange();

        assertEquals("setTransaction", ChangeFactory.getInstance().getChangeMetaData(setTransactionChange).getName());
        assertEquals("Set Transaction", ChangeFactory.getInstance().getChangeMetaData(setTransactionChange).getDescription());
        assertEquals(ChangeMetaData.PRIORITY_DEFAULT + 200, ChangeFactory.getInstance().getChangeMetaData(setTransactionChange).getPriority());
    }

    @Test
    public void getConfirmationMessage() {
        SetTransactionChange change = new SetTransactionChange();

        assertEquals("Transaction has been set", change.getConfirmationMessage());
    }

    @Test
    public void generateStatement() {

        SetTransactionChange change = new SetTransactionChange();
        change.setTransactionName("TRANSACTION_NAME");
        change.setIsolationLevel("ISOLATION_LEVEL");
        change.setRollbackSegment("ROLLBACK_SEGMENT");
        change.setReadOnlyWrite("READONLYWRITE");

        Database database = new OracleDatabase();
        SqlStatement[] sqlStatements = change.generateStatements(database);

        assertEquals(1, sqlStatements.length);
        assertTrue(sqlStatements[0] instanceof SetTransactionStatement);

        assertEquals("TRANSACTION_NAME", ((SetTransactionStatement) sqlStatements[0]).getTransactionName());
        assertEquals("ISOLATION_LEVEL", ((SetTransactionStatement) sqlStatements[0]).getIsolationLevel());
        assertEquals("ROLLBACK_SEGMENT", ((SetTransactionStatement) sqlStatements[0]).getRollbackSegment());
        assertEquals("READONLYWRITE", ((SetTransactionStatement) sqlStatements[0]).getReadOnlyWrite());

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

        expectedQuery.add("SET TRANSACTION NAME 'zuiolTransaction'");

        int i = 0;

        for (ChangeSet changeSet : changeSets) {
            for (Change change : changeSet.getChanges()) {
                Sql[] sql = SqlGeneratorFactory.getInstance().generateSql(change.generateStatements(database)[0], database);
                // if(i == 2)
                assertEquals(expectedQuery.get(i), sql[0].toSql());
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

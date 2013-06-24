package liquibase.ext.ora.dropmaterializedview;

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
import liquibase.ext.ora.test.BaseTest;
import liquibase.parser.ChangeLogParserFactory;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.resource.ResourceAccessor;
import liquibase.sql.Sql;
import liquibase.sqlgenerator.SqlGeneratorFactory;
import liquibase.statement.SqlStatement;

import org.junit.Before;
import org.junit.Test;

public class DropMaterializedViewTest extends BaseTest {

    @Before
    public void setUp() throws Exception {
        changeLogFile = "liquibase/ext/ora/dropmaterializedview/changelog.test.xml";
        connectToDB();
        cleanDB();
    }

    @Test
    public void getChangeMetaData() {
        DropMaterializedViewChange dropMaterializedViewChange = new DropMaterializedViewChange();

        assertEquals("dropMaterializedView", ChangeFactory.getInstance().getChangeMetaData(dropMaterializedViewChange).getName());
        assertEquals("Drop Materialized View", ChangeFactory.getInstance().getChangeMetaData(dropMaterializedViewChange).getDescription());
        assertEquals(ChangeMetaData.PRIORITY_DEFAULT, ChangeFactory.getInstance().getChangeMetaData(dropMaterializedViewChange).getPriority());
    }

    @Test
    public void getConfirmationMessage() {
        DropMaterializedViewChange change = new DropMaterializedViewChange();
        change.setViewName("VIEW_NAME");

        assertEquals("Materialized view VIEW_NAME has been droped", "Materialized view " + change.getViewName()
                + " has been droped");
    }

    @Test
    public void generateStatement() {

        DropMaterializedViewChange change = new DropMaterializedViewChange();
        change.setSchemaName("SCHEMA_NAME");
        change.setViewName("VIEW_NAME");

        Database database = new OracleDatabase();
        SqlStatement[] sqlStatements = change.generateStatements(database);

        assertEquals(1, sqlStatements.length);
        assertTrue(sqlStatements[0] instanceof DropMaterializedViewStatement);

        assertEquals("SCHEMA_NAME", ((DropMaterializedViewStatement) sqlStatements[0]).getSchemaName());
        assertEquals("VIEW_NAME", ((DropMaterializedViewStatement) sqlStatements[0]).getViewName());
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

        expectedQuery.add("Drop materialized view zuiolView");

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
        liquiBase.update(null);
    }
}

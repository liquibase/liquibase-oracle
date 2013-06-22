package liquibase.ext.ora.creatematerializedview;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

import org.junit.Before;
import org.junit.Test;

public class CreateMaterializedViewTest extends BaseTest {

    @Before
    public void setUp() throws Exception {
        changeLogFile = "liquibase/ext/ora/creatematerializedview/changelog.test.xml";
        connectToDB();
        cleanDB();
    }

    @Test
    public void getChangeMetaData() {
        CreateMaterializedViewChange createMaterializedViewChange = new CreateMaterializedViewChange();

        assertEquals("createMaterializedView", createMaterializedViewChange.getChangeMetaData().getName());
        assertEquals("Create Materialized View", createMaterializedViewChange.getChangeMetaData().getDescription());
        assertEquals(ChangeMetaData.PRIORITY_DEFAULT, createMaterializedViewChange.getChangeMetaData().getPriority());
    }

    @Test
    public void getConfirmationMessage() {
        CreateMaterializedViewChange change = new CreateMaterializedViewChange();
        change.setViewName("VIEW_NAME");

        assertEquals("Materialized view VIEW_NAME has been created", "Materialized view " + change.getViewName()
                + " has been created");
    }

    @Test
    public void generateStatement() {

        CreateMaterializedViewChange change = new CreateMaterializedViewChange();
        change.setSchemaName("SCHEMA_NAME");
        change.setViewName("VIEW_NAME");
        change.setColumnAliases("COLUMN_ALIASES");
        change.setObjectType("OBJECT_TYPE");
        change.setTableSpace("TABLE_SPACE");
        change.setQueryRewrite("QUERY_REWRITE");
        change.setSubquery("SUBQUERY");

        change.setReducedPrecision(true);
        change.setUsingIndex(true);
        change.setForUpdate(true);

        Database database = new OracleDatabase();
        SqlStatement[] sqlStatements = change.generateStatements(database);

        assertEquals(1, sqlStatements.length);
        assertTrue(sqlStatements[0] instanceof CreateMaterializedViewStatement);

        assertEquals("SCHEMA_NAME", ((CreateMaterializedViewStatement) sqlStatements[0]).getSchemaName());
        assertEquals("VIEW_NAME", ((CreateMaterializedViewStatement) sqlStatements[0]).getViewName());
        assertEquals("COLUMN_ALIASES", ((CreateMaterializedViewStatement) sqlStatements[0]).getColumnAliases());
        assertEquals("OBJECT_TYPE", ((CreateMaterializedViewStatement) sqlStatements[0]).getObjectType());
        assertEquals("TABLE_SPACE", ((CreateMaterializedViewStatement) sqlStatements[0]).getTableSpace());
        assertEquals("QUERY_REWRITE", ((CreateMaterializedViewStatement) sqlStatements[0]).getQueryRewrite());
        assertEquals("SUBQUERY", ((CreateMaterializedViewStatement) sqlStatements[0]).getSubquery());

        assertTrue(((CreateMaterializedViewStatement) sqlStatements[0]).getReducedPrecision());
        assertTrue(((CreateMaterializedViewStatement) sqlStatements[0]).getUsingIndex());
        assertTrue(((CreateMaterializedViewStatement) sqlStatements[0]).getForUpdate());
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

        expectedQuery.add("CREATE MATERIALIZED VIEW zuiolView ON PREBUILT TABLE AS select * from Table1");

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
        liquiBase.update(null);
        liquiBase.rollback(1, null);
    }
}

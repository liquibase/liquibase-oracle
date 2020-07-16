package liquibase.ext.ora.refreshmaterializedview;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import liquibase.Contexts;
import liquibase.LabelExpression;
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

public class RefreshMaterializedViewTest extends BaseTestCase {

    @Before
    public void setUp() throws Exception {
        changeLogFile = "liquibase/ext/ora/refreshmaterializedview/changelog.test.xml";
        connectToDB();
        cleanDB();
    }

    @Test
    public void getChangeMetaData() {
        RefreshMaterializedViewChange change = new RefreshMaterializedViewChange();

        assertEquals("metadata name incorrect", "refreshMaterializedView", Scope.getCurrentScope().getSingleton(ChangeFactory.class).getChangeMetaData(change).getName());
        assertEquals("metadata description incorrect", "Refresh Materialized View", Scope.getCurrentScope().getSingleton(ChangeFactory.class).getChangeMetaData(change).getDescription());
        assertEquals("metadata priority incorrect", ChangeMetaData.PRIORITY_DEFAULT + 200, Scope.getCurrentScope().getSingleton(ChangeFactory.class).getChangeMetaData(change).getPriority());
    }

    @Test
    public void getConfirmationMessage() {
        RefreshMaterializedViewChange change = new RefreshMaterializedViewChange();
        change.setViewName("MVIEW_NAME");

        assertEquals("Materialized view MVIEW_NAME has been refreshed", change.getConfirmationMessage() );
    }

    @Test
    public void generateStatement() {
        RefreshMaterializedViewChange change = new RefreshMaterializedViewChange();
        change.setSchemaName("SCHEMA_NAME");
        change.setViewName("VIEW_NAME");
        change.setAtomicRefresh(Boolean.TRUE);
        change.setRefreshType("fast");

        SqlStatement[] sqlStatements = change.generateStatements(new OracleDatabase());

        assertEquals("Wrong number of statements returned", 1, sqlStatements.length);
        assertEquals("Statement of wrong type", RefreshMaterializedViewStatement.class, sqlStatements[0].getClass() );

        assertEquals("schema name not copied", "SCHEMA_NAME", ((RefreshMaterializedViewStatement) sqlStatements[0]).getSchemaName());
        assertEquals("view name not copied", "VIEW_NAME", ((RefreshMaterializedViewStatement) sqlStatements[0]).getViewName());
        assertEquals("atomic refresh not copied", Boolean.TRUE, ((RefreshMaterializedViewStatement) sqlStatements[0]).getAtomicRefresh());
        assertEquals("refresh type not copied", "fast", ((RefreshMaterializedViewStatement) sqlStatements[0]).getRefreshType());
    }

    @Test
    public void parseAndGenerate() throws Exception {
        if (connection == null) {
            return;
        }
        Database database = liquiBase.getDatabase();
        ResourceAccessor resourceAccessor = new ClassLoaderResourceAccessor();

        DatabaseChangeLog changeLog = ChangeLogParserFactory.getInstance()
        		.getParser(changeLogFile, resourceAccessor)
        		.parse(changeLogFile, new ChangeLogParameters(), resourceAccessor);

        liquiBase.checkLiquibaseTables( false, changeLog, new Contexts(), new LabelExpression());

        changeLog.validate(database);

        List<String> expectedSql = new ArrayList<String>();
        expectedSql.add("BEGIN DBMS_MVIEW.REFRESH('TABLE1_MVIEW','?',ATOMIC_REFRESH=>TRUE); END;");
        expectedSql.add("BEGIN DBMS_MVIEW.REFRESH('TABLE1_MVIEW','C',ATOMIC_REFRESH=>FALSE); END;");
        expectedSql.add("BEGIN DBMS_MVIEW.REFRESH('LIQUIBASE.TABLE1_MVIEW','?',ATOMIC_REFRESH=>FALSE); END;");

        List<ChangeSet> changeSets = changeLog.getChangeSets();
        assertEquals( "Test is out of sync with the changelog.test.xml file.  # of changesets",
        		6, changeSets.size());
        ChangeSet changeSet = changeSets.get(changeSets.size()-1);
        assertEquals( "Test is out of sync with the changelog.test.xml file.  # of changes in changeset", 3, changeSet.getChanges().size() );

        List<String> actualSql = new ArrayList<String>();

        for (Change change : changeSet.getChanges()) {
            Sql[] sql = SqlGeneratorFactory.getInstance().generateSql(change.generateStatements(database)[0], database);
            assertEquals( "Wrong number of SQL statements generated", 1, sql.length );
            actualSql.add(sql[0].toSql());
        }

        assertEquals( "Generated SQL statements incorrect", expectedSql, actualSql );
    }

    @Test
    public void testUpdate() throws Exception {
        if (connection == null) {
            return;
        }

        liquiBase.update(new Contexts());
    }
}

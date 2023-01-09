package liquibase.ext.ora.encapsulateTableWithView;

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
import liquibase.ext.ora.testing.BaseTestCase;
import liquibase.parser.ChangeLogParserFactory;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.resource.ResourceAccessor;
import liquibase.sql.Sql;
import liquibase.sqlgenerator.SqlGeneratorFactory;
import liquibase.statement.SqlStatement;
import liquibase.statement.core.CreateViewStatement;
import liquibase.statement.core.RenameTableStatement;

import org.junit.Before;
import org.junit.Test;

public class EncapsulateTableWithViewTest extends BaseTestCase {

    @Before
    public void setUp() throws Exception {
        changeLogFile = "liquibase/ext/ora/encapsulateTableWithView/changelog.test.xml";
        connectToDB();
        cleanDB();
    }

    @Test
    public void getChangeMetaData() {
        EncapsulateTableWithViewChange change = new EncapsulateTableWithViewChange();

        assertEquals("encapsulateTableWithView", Scope.getCurrentScope().getSingleton(ChangeFactory.class).getChangeMetaData(change).getName());
        assertEquals("Encapsulate table with view", Scope.getCurrentScope().getSingleton(ChangeFactory.class).getChangeMetaData(change).getDescription());
        assertEquals(ChangeMetaData.PRIORITY_DEFAULT + 200, Scope.getCurrentScope().getSingleton(ChangeFactory.class).getChangeMetaData(change).getPriority());
    }

    @Test
    public void getConfirmationMessage() {
        EncapsulateTableWithViewChange change = new EncapsulateTableWithViewChange();
        change.setTableName("TABLE_NAME");

        assertEquals("Table TABLE_NAME encapsulated with view", change.getConfirmationMessage());
    }

    @Test
    public void generateStatement() {
        EncapsulateTableWithViewChange change = new EncapsulateTableWithViewChange();
        change.setSchemaName("SCHEMA_NAME");
        change.setTableName("TABLE_NAME");

        Database database = new OracleDatabase();
        SqlStatement[] sqlStatements = change.generateStatements(database);

        assertEquals(2, sqlStatements.length);
        assertTrue(sqlStatements[0] instanceof RenameTableStatement);
        assertTrue(sqlStatements[1] instanceof CreateViewStatement);

        assertEquals("SCHEMA_NAME", ((RenameTableStatement) sqlStatements[0]).getSchemaName());
        assertEquals("TABLE_NAME", ((RenameTableStatement) sqlStatements[0]).getOldTableName());
        assertEquals("TTABLE_NAME", ((RenameTableStatement) sqlStatements[0]).getNewTableName());

        assertEquals("SCHEMA_NAME", ((CreateViewStatement) sqlStatements[1]).getSchemaName());
        assertEquals("TABLE_NAME", ((CreateViewStatement) sqlStatements[1]).getViewName());
        assertEquals("SELECT * FROM TTABLE_NAME", ((CreateViewStatement) sqlStatements[1]).getSelectQuery());
        assertTrue(((CreateViewStatement) sqlStatements[1]).isReplaceIfExists());
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

        expectedQuery.add("CREATE TABLE LBUSER.person (id NUMBER(10), name VARCHAR2(50))");
        expectedQuery.add("INSERT INTO LBUSER.person (id, name) VALUES ('1', 'James')");
        expectedQuery.add("ALTER TABLE LBUSER.person RENAME TO Tperson");
        expectedQuery.add("CREATE OR REPLACE VIEW LBUSER.person AS SELECT * FROM Tperson");

        int i = 0;

        for (ChangeSet changeSet : changeSets) {
            for (Change change : changeSet.getChanges()) {
                Sql[] sql = SqlGeneratorFactory.getInstance().generateSql(change.generateStatements(database)[0], database);

                assertEquals(expectedQuery.get(i), sql[0].toSql());
            }
            i++;
        }
    }
}

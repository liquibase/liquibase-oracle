package liquibase.ext.ora.enableconstraint;

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

public class EnableCheckChangeTest extends BaseTestCase {

    @Before
    public void setUp() throws Exception {
        changeLogFile = "liquibase/ext/ora/enableconstraint/changelog.test.xml";
        connectToDB();
        cleanDB();
    }

    @Test
    public void test() throws Exception {
        liquiBase.update(null);
    }

    @Test
    public void generateStatement() {
        EnableConstraintChange change = new EnableConstraintChange();

        change.setSchemaName("SCHEMA_NAME");
        change.setTableName("TABLE_NAME");

        change.setConstraintName("CONSTRAINT");

        SqlStatement[] statements = change.generateStatements(new OracleDatabase());
        assertEquals(1, statements.length);
        EnableConstraintStatement statement = (EnableConstraintStatement) statements[0];

        assertEquals("SCHEMA_NAME", statement.getSchemaName());
        assertEquals("TABLE_NAME", statement.getTableName());

        assertEquals("CONSTRAINT", statement.getConstraintName());
    }

    @Test
    public void getConfirmationMessage() {
        EnableConstraintChange change = new EnableConstraintChange();

        change.setTableName("TABLE_NAME");
        change.setConstraintName("CONSTRAINT");

        assertEquals("Constraint " + change.getConstraintName() + " ENABLED in " + change.getTableName(), change
                .getConfirmationMessage());
    }

    @Test
    public void getChangeMetaData() {
        EnableConstraintChange enableCheckChange = new EnableConstraintChange();

        assertEquals("enableConstraint", ChangeFactory.getInstance().getChangeMetaData(enableCheckChange).getName());
        assertEquals("constraint enable", ChangeFactory.getInstance().getChangeMetaData(enableCheckChange).getDescription());
        assertEquals(ChangeMetaData.PRIORITY_DEFAULT, ChangeFactory.getInstance().getChangeMetaData(enableCheckChange).getPriority());
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

        expectedQuery.add("ALTER TABLE test ENABLE CONSTRAINT tom_check");

        ChangeSet changeSet = changeSets.get(2);
        Change change = changeSet.getChanges().get(0);
        Sql[] sql = SqlGeneratorFactory.getInstance().generateSql(change.generateStatements(database)[0], database);

        assertEquals(expectedQuery.get(0), sql[0].toSql());
    }
}

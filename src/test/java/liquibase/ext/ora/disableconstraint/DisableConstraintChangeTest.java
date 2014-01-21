package liquibase.ext.ora.disableconstraint;

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

public class DisableConstraintChangeTest extends BaseTestCase {

    @Before
    public void setUp() throws Exception {
        changeLogFile = "liquibase/ext/ora/disableconstraint/changelog.test.xml";
        connectToDB();
        cleanDB();
    }

    @Test
    public void test() throws Exception {
        liquiBase.update(null);
    }

    @Test
    public void generateStatement() {
        DisableConstraintChange change = new DisableConstraintChange();

        change.setSchemaName("SCHEMA_NAME");
        change.setTableName("TABLE_NAME");

        change.setConstraintName("CONSTRAINT");

        SqlStatement[] statements = change.generateStatements(new OracleDatabase());
        assertEquals(1, statements.length);
        DisableConstraintStatement statement = (DisableConstraintStatement) statements[0];

        assertEquals("SCHEMA_NAME", statement.getSchemaName());
        assertEquals("TABLE_NAME", statement.getTableName());

        assertEquals("CONSTRAINT", statement.getConstraintName());
    }

    @Test
    public void getConfirmationMessage() {
        DisableConstraintChange change = new DisableConstraintChange();

        change.setTableName("TABLE_NAME");
        change.setConstraintName("CONSTRAINT");

        assertEquals("constraint " + change.getConstraintName() + " DISABLED in " + change.getTableName(), change
                .getConfirmationMessage());
    }

    @Test
    public void getChangeMetaData() {
        DisableConstraintChange disableCheckChange = new DisableConstraintChange();

        assertEquals("disableConstraint", ChangeFactory.getInstance().getChangeMetaData(disableCheckChange).getName());
        assertEquals("Disable constraint", ChangeFactory.getInstance().getChangeMetaData(disableCheckChange).getDescription());
        assertEquals(ChangeMetaData.PRIORITY_DEFAULT, ChangeFactory.getInstance().getChangeMetaData(disableCheckChange).getPriority());
    }

    @Test
    public void parseAndGenerate() throws Exception {

        Database database = liquiBase.getDatabase();
        ResourceAccessor resourceAccessor = new ClassLoaderResourceAccessor();

        ChangeLogParameters changeLogParameters = new ChangeLogParameters();


        DatabaseChangeLog changeLog = ChangeLogParserFactory.getInstance().getParser(changeLogFile, resourceAccessor).parse(changeLogFile,
                changeLogParameters, resourceAccessor);

        database.checkDatabaseChangeLogTable(false, changeLog, null);
        ChangeLogIterator changeLogIterator = new ChangeLogIterator(changeLog);

        changeLog.validate(database);


        List<ChangeSet> changeSets = changeLog.getChangeSets();

        List<String> expectedQuery = new ArrayList<String>();

        expectedQuery.add("ALTER TABLE LIQUIBASE.test DISABLE CONSTRAINT tom_check");

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
}
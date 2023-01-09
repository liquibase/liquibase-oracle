package liquibase.ext.ora.comment;

import liquibase.Scope;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.resource.DirectoryResourceAccessor;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

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
import liquibase.resource.ResourceAccessor;
import liquibase.sql.Sql;
import liquibase.sqlgenerator.SqlGeneratorFactory;
import liquibase.statement.SqlStatement;

import static org.junit.Assert.assertEquals;

public class CommentOnChangeTest extends BaseTestCase {
    @Before
    public void setUp() throws Exception {
        changeLogFile = "liquibase/ext/ora/comment/changelog.test.xml";
        connectToDB();
        cleanDB();
    }

    @Test
    public void test() throws Exception {
        if (connection == null) {
            return;
        }
        liquiBase.update("null");

    }

    @Test
    public void generateStatement() {
        CommentOnChange change = new CommentOnChange();

        change.setSchemaName("SCHEMA NAME");
        change.setTableName("TABLE NAME");
        change.setColumnName("COLUMN NAME");
        change.setComment("My comment with 'quotes'");

        SqlStatement[] statements = change.generateStatements(new OracleDatabase());
        assertEquals(1, statements.length);
        CommentOnStatement statement = (CommentOnStatement) statements[0];

        assertEquals("SCHEMA NAME", statement.getSchemaName());
        assertEquals("TABLE NAME", statement.getTableName());
        assertEquals("COLUMN NAME", statement.getColumnName());
        assertEquals("My comment with 'quotes'", statement.getComment());
    }

    @Test
    public void getConfirmationMessage() {
        CommentOnChange change = new CommentOnChange();

        change.setTableName("TABLE_NAME");
        change.setComment("COMMENT");

        assertEquals("Comment has been added to " + change.getTableName(), change.getConfirmationMessage());
    }

    @Test
    public void getChangeMetaData() {
        CommentOnChange enableCheckChange = new CommentOnChange();

        assertEquals("commentOn", Scope.getCurrentScope().getSingleton(ChangeFactory.class).getChangeMetaData(enableCheckChange).getName());
        assertEquals("Create or replace a comment on a table or a column", Scope.getCurrentScope().getSingleton(ChangeFactory.class).getChangeMetaData(enableCheckChange).getDescription());
        assertEquals(ChangeMetaData.PRIORITY_DEFAULT + 200, Scope.getCurrentScope().getSingleton(ChangeFactory.class).getChangeMetaData(enableCheckChange).getPriority());
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

        assertExpectedQuery( changeFrom( changeSets, "column-comment"), "COMMENT ON COLUMN LBUSER.for_comment.id IS 'My comment ''quoted'''" );
        assertExpectedQuery( changeFrom( changeSets, "table-comment"), "COMMENT ON TABLE LBUSER.for_comment IS 'My TABLE comment ''quoted'''" );
        assertExpectedQuery( changeFrom( changeSets, "view-comment"), "COMMENT ON TABLE LBUSER.for_comment_view IS 'My VIEW comment'" );
    }

    private void assertExpectedQuery(Change change, String expectedSql) {
        Database database = liquiBase.getDatabase();
        Sql[] sqlStatements = SqlGeneratorFactory.getInstance().generateSql(change.generateStatements(database)[0], database);
        assertEquals(1, sqlStatements.length);
        assertEquals(expectedSql, sqlStatements[0].toSql());
    }

    private Change changeFrom(List<ChangeSet> changeSets, String changeSetId) {
        for (ChangeSet changeSet : changeSets) {
            if ( changeSetId.equals(changeSet.getId())){
                List<Change> changes = changeSet.getChanges();
                assertEquals(1, changes.size());
                return changes.get(0);
            }
        }
        throw new IllegalStateException("No change set with id " + changeSetId + " found");
    }

}

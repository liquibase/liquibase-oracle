package liquibase.ext.ora.merge;

import static org.junit.Assert.assertEquals;

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

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class MergeChangeTest extends BaseTestCase {

    @Before
    public void setUp() throws Exception {
        changeLogFile = "liquibase/ext/ora/merge/changelog.test.xml";
        connectToDB();
        cleanDB();
    }

    @Test
    @Ignore("Merge commnad was changed but tests were not updated")
    public void test() throws Exception {
        if (connection == null) {
            return;
        }
        liquiBase.update((String) null);
    }

    @Test
    public void generateStatement() {
        MergeChange change = new MergeChange();

        change.setTargetSchemaName("TARGET_SCHEMA_NAME");
        change.setTargetTableName("TARGET_TABLE_NAME");

        change.setSourceSchemaName("SOURCE_SCHEMA_NAME");
        change.setSourceTableName("SOURCE_TABLE_NAME");

        change.setInsertColumnsNameList("COL_1,COL_2");
        change.setInsertColumnsValueList("VAL_1,VAL2");
        change.setUpdateList("COL1=VAL1,COL2=VAL2");
        change.setOnCondition("ON_CONDITION");
        change.setInsertCondition("INSERT_CONDITION");
        change.setDeleteCondition("DELETE_CONDITION");
        change.setUpdateCondition("UPDATE_CONDITION");

        SqlStatement[] statements = change.generateStatements(new OracleDatabase());
        assertEquals(1, statements.length);
        MergeStatement statement = (MergeStatement) statements[0];

        assertEquals("TARGET_SCHEMA_NAME", statement.getTargetSchemaName());
        assertEquals("TARGET_TABLE_NAME", statement.getTargetTableName());

        assertEquals("SOURCE_SCHEMA_NAME", statement.getSourceSchemaName());
        assertEquals("SOURCE_TABLE_NAME", statement.getSourceTableName());

        assertEquals("ON_CONDITION", statement.getOnCondition());
//        assertEquals("COL_1,COL_2", statement.getInsertColumnsNameList());
//        assertEquals("VAL_1,VAL2", statement.getInsertColumnsValueList());
        assertEquals("COL1=VAL1,COL2=VAL2", statement.getUpdateList());
        assertEquals("DELETE_CONDITION", statement.getDeleteCondition());
        assertEquals("INSERT_CONDITION", statement.getInsertCondition());
        assertEquals("UPDATE_CONDITION", statement.getUpdateCondition());
    }

    @Test
    public void getConfirmationMessage() {
        MergeChange change = new MergeChange();

        change.setSourceSchemaName("S_SCHEMA_NAME");
        change.setSourceTableName("S_TABLE_NAME");
        change.setTargetSchemaName("T_SCHEMA_NAME");
        change.setTargetTableName("T_TABLE_NAME");

        assertEquals("Tables " + change.getSourceTableName() + " & " + change.getTargetTableName() + " merged", change
                .getConfirmationMessage());
    }

    @Test
    public void getChangeMetaData() {
        MergeChange mergeTablesChange = new MergeChange();

        assertEquals("merge", Scope.getCurrentScope().getSingleton(ChangeFactory.class).getChangeMetaData(mergeTablesChange).getName());
        assertEquals("Merge", Scope.getCurrentScope().getSingleton(ChangeFactory.class).getChangeMetaData(mergeTablesChange).getDescription());
        assertEquals(ChangeMetaData.PRIORITY_DEFAULT + 200, Scope.getCurrentScope().getSingleton(ChangeFactory.class).getChangeMetaData(mergeTablesChange).getPriority());
    }

    @Test
    @Ignore("Merge commnad was changed but tests were not updated")
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

        expectedQuery.add("MERGE INTO LBUSER.myTable2 USING LBUSER.myTable ON (myTable2.pid=myTable.pid) WHEN MATCHED THEN UPDATE SET myTable2.sales=myTable2.sales+myTable.sales,myTable2.status=myTable.status DELETE WHERE (myTable2.status='OBS') WHEN NOT MATCHED THEN INSERT VALUES(myTable.pid,myTable.sales,'OLD')");

        int i = 0;

        for (ChangeSet changeSet : changeSets) {
            for (Change change : changeSet.getChanges()) {
                Sql[] sql = SqlGeneratorFactory.getInstance().generateSql(change.generateStatements(database)[0], database);
                if (i == 3) {
                    assertEquals(expectedQuery.get(0), sql[0].toSql());
                }

            }
            i++;
        }
    }
}

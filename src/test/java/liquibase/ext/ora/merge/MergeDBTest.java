package liquibase.ext.ora.merge;

import liquibase.ext.ora.testing.BaseTestCase;

import org.dbunit.Assertion;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class MergeDBTest extends BaseTestCase {

    private IDataSet loadedDataSet;
    private final String TABLE_NAME = "myTable2";

    protected IDatabaseConnection getConnection() throws Exception {
        return new DatabaseConnection(connection);
    }

    protected IDataSet getDataSet() throws Exception {
        loadedDataSet = new FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream(
                "liquibase/ext/ora/merge/input.xml"));
        return loadedDataSet;
    }

    @Before
    public void setUp() throws Exception {
        changeLogFile = "liquibase/ext/ora/merge/changelog.test.xml";
        connectToDB();
        cleanDB();
    }

    @Test
    @Ignore("Merge commnad was changed but tests were not updated")
    public void testCompare() throws Exception {
        if (connection == null) {
            return;
        }
        QueryDataSet actualDataSet = new QueryDataSet(getConnection());

        liquiBase.update((String) null);

        actualDataSet.addTable(TABLE_NAME, "SELECT * FROM " + TABLE_NAME);
        loadedDataSet = getDataSet();

        Assertion.assertEquals(loadedDataSet, actualDataSet);
    }
}

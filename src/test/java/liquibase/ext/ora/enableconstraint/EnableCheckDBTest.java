package liquibase.ext.ora.enableconstraint;

import liquibase.ext.ora.test.BaseTest;

import org.dbunit.Assertion;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.junit.Before;
import org.junit.Test;

public class EnableCheckDBTest extends BaseTest {

    private IDataSet loadedDataSet;
    private final String TABLE_NAME = "USER_CONSTRAINTS";

    protected IDatabaseConnection getConnection() throws Exception {
        return new DatabaseConnection(connection);
    }

    protected IDataSet getDataSet() throws Exception {
        loadedDataSet = new FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream(
                "liquibase/ext/ora/enableconstraint/input.xml"));
        return loadedDataSet;
    }

    @Before
    public void setUp() throws Exception {
        changeLogFile = "liquibase/ext/ora/enableconstraint/changelog.test.xml";
        connectToDB();
        cleanDB();
    }

    @Test
    public void testCompare() throws Exception {
        QueryDataSet actualDataSet = new QueryDataSet(getConnection());

        liquiBase.update(null);

        actualDataSet.addTable(TABLE_NAME, "select status from " + TABLE_NAME + " where constraint_name='TOM_CHECK'");
        loadedDataSet = getDataSet();

        Assertion.assertEquals(loadedDataSet, actualDataSet);
    }
}

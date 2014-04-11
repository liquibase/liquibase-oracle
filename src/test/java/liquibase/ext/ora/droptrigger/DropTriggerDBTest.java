package liquibase.ext.ora.droptrigger;

import liquibase.Contexts;
import liquibase.ext.ora.testing.BaseTestCase;

import org.dbunit.Assertion;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.junit.Before;
import org.junit.Test;

public class DropTriggerDBTest extends BaseTestCase {

    private IDataSet loadedDataSet;
    private final String TABLE_NAME = "USER_TRIGGERS";

    @Before
    public void setUp() throws Exception {
        changeLogFile = "liquibase/ext/ora/droptrigger/changelog.test.xml";
        connectToDB();
        cleanDB();
    }

    protected IDatabaseConnection getConnection() throws Exception {
        return new DatabaseConnection(connection);
    }

    protected IDataSet getDataSet() throws Exception {
        loadedDataSet = new FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream(
                "liquibase/ext/ora/droptrigger/input.xml"));
        return loadedDataSet;
    }

    @Test
    public void testCompare() throws Exception {
        QueryDataSet actualDataSet = new QueryDataSet(getConnection());

        liquiBase.update(new Contexts());
        actualDataSet.addTable(TABLE_NAME, "SELECT TRIGGER_NAME from " + TABLE_NAME + " WHERE table_name = 'TRIGGERTEST'");
        loadedDataSet = getDataSet();

        Assertion.assertEquals(loadedDataSet, actualDataSet);
    }
}

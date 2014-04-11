package liquibase.ext.ora.encapsulateTableWithView;

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

public class EncapsulateTableWithViewDBTest extends BaseTestCase {

    private IDataSet loadedDataSet;
    private final String TABLE_NAME = "person";

    protected IDatabaseConnection getConnection() throws Exception {
        return new DatabaseConnection(connection);
    }

    protected IDataSet getDataSet() throws Exception {
        loadedDataSet = new FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream(
                "liquibase/ext/ora/encapsulateTableWithView/input.xml"));
        return loadedDataSet;
    }

    protected IDataSet getAfterRollbackDataSet() throws Exception {
        loadedDataSet = new FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream(
                "liquibase/ext/ora/encapsulateTableWithView/afterRollbackInput.xml"));
        return loadedDataSet;
    }

    @Before
    public void setUp() throws Exception {
        changeLogFile = "liquibase/ext/ora/encapsulateTableWithView/changelog.test.xml";
        connectToDB();
        cleanDB();
        liquiBase.update(new Contexts());
    }

    @Test
    public void updateTest() throws Exception {
        QueryDataSet actualDataSet = new QueryDataSet(getConnection());
        actualDataSet.addTable("T" + TABLE_NAME);
        actualDataSet.addTable(TABLE_NAME);

        loadedDataSet = getDataSet();

        Assertion.assertEquals(loadedDataSet, actualDataSet);
    }

    @Test
    public void rollbackTest() throws Exception {
        liquiBase.rollback(1, new Contexts());

        QueryDataSet actualDataSet = new QueryDataSet(getConnection());
        actualDataSet.addTable(TABLE_NAME);

        loadedDataSet = getAfterRollbackDataSet();

        Assertion.assertEquals(loadedDataSet, actualDataSet);
    }
}

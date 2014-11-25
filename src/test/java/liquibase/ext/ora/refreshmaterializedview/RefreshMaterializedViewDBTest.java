package liquibase.ext.ora.refreshmaterializedview;

import liquibase.Contexts;
import liquibase.ext.ora.testing.BaseTestCase;

import org.dbunit.Assertion;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Before;
import org.junit.Test;

public class RefreshMaterializedViewDBTest extends BaseTestCase {

    @Before
    public void setUp() throws Exception {
        changeLogFile = "liquibase/ext/ora/refreshmaterializedview/changelog.test.xml";
        connectToDB();
        cleanDB();
    }

    protected IDatabaseConnection getConnection() throws Exception {
        return new DatabaseConnection(connection);
    }

    protected IDataSet getDataSet() throws Exception {
    	return new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResourceAsStream(
                "liquibase/ext/ora/refreshmaterializedview/input.xml"));
    }

    @Test
    public void testRefreshWorked() throws Exception {
        if (connection == null) {
            return;
        }

        QueryDataSet actualDataSet = new QueryDataSet(getConnection());

        liquiBase.update(new Contexts());
        actualDataSet.addTable("TABLE1_MVIEW", "SELECT * from TABLE1_MVIEW");
        IDataSet loadedDataSet = getDataSet();

        Assertion.assertEquals(loadedDataSet, actualDataSet);
    }

}

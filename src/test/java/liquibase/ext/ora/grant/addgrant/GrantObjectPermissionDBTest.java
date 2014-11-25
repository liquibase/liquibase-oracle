package liquibase.ext.ora.grant.addgrant;

import liquibase.ext.ora.testing.BaseTestCase;

import org.dbunit.Assertion;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.junit.Before;
import org.junit.Test;

public class GrantObjectPermissionDBTest extends BaseTestCase {

    private IDataSet loadedDataSet;

    @Before
    public void setUp() throws Exception {
        changeLogFile = "liquibase/ext/ora/grant/addgrant/changelog.test.xml";
        connectToDB();
        cleanDB();
    }

    protected IDatabaseConnection getConnection() throws Exception {
        return new DatabaseConnection(connection);
    }

    protected IDataSet getDataSet() throws Exception {
        loadedDataSet = new FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream(
                "liquibase/ext/ora/grant/addgrant/input.xml"));

        return loadedDataSet;
    }

    @Test
    public void testCompare() throws Exception {
        QueryDataSet actualDataSet = new QueryDataSet(getConnection());

        liquiBase.update((String) null);
        actualDataSet.addTable("USER_TAB_PRIVS_MADE", "select PRIVILEGE from USER_TAB_PRIVS_MADE WHERE TABLE_NAME = 'ADDGRANT' ORDER BY 1");
        loadedDataSet = getDataSet();

        Assertion.assertEquals(loadedDataSet, actualDataSet);
    }

}

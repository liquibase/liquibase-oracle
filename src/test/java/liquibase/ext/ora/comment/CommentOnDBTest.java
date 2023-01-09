package liquibase.ext.ora.comment;

import org.dbunit.Assertion;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import liquibase.ext.ora.testing.BaseTestCase;

public class CommentOnDBTest extends BaseTestCase {

    private IDataSet loadedDataSet;
    private final String TABLE_NAME = "FOR_COMMENT";

    protected IDatabaseConnection getConnection() throws Exception {
        return new DatabaseConnection(connection);
    }

    protected IDataSet getDataSet() throws Exception {
        loadedDataSet = new FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream(
                "liquibase/ext/ora/comment/input.xml"));
        return loadedDataSet;
    }

    @Before
    public void setUp() throws Exception {
        changeLogFile = "liquibase/ext/ora/comment/changelog.test.xml";
        connectToDB();
        cleanDB();
    }

    @Test
    @Ignore("Not working")
    public void testCompare() throws Exception {
        if (connection == null) {
            return;
        }

        QueryDataSet actualDataSet = new QueryDataSet(getConnection());

        liquiBase.update((String) null);
        actualDataSet.addTable("USER_COL_COMMENTS", "SELECT * FROM USER_COL_COMMENTS where TABLE_NAME='" + TABLE_NAME + "' and comments is not null");
        actualDataSet.addTable("USER_TAB_COMMENTS", "SELECT * FROM USER_TAB_COMMENTS where TABLE_NAME='" + TABLE_NAME + "' and comments is not null");
        loadedDataSet = getDataSet();

        Assertion.assertEquals(loadedDataSet, actualDataSet);
    }
}

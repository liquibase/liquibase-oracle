package liquibase.ext.ora.testing;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Driver;
import java.util.Properties;
import java.util.logging.Logger;

import liquibase.Liquibase;
import liquibase.database.DatabaseConnection;
import liquibase.database.DatabaseFactory;
import liquibase.database.OfflineConnection;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

/*
 * Class used by tests to set up connection and clean database.
 */
public class BaseTestCase {
    private static final Class<BaseTestCase> THIS_CLASS = BaseTestCase.class;
    private static final Logger LOGGER = Logger.getLogger(THIS_CLASS.getName());
    private static final String TESTS_PROPERTIES_FILE_NAME = "tests.properties";

    private static String url;
    private static Driver driver;
    private static Properties info;
    protected static Connection connection;
    protected static DatabaseConnection jdbcConnection;
    protected static Liquibase liquiBase;
    protected static String changeLogFile;

    public static void connectToDB() throws Exception {
        if (connection == null) {
            info = new Properties();

            final FileInputStream fileInputStream = getTestsProperties(TESTS_PROPERTIES_FILE_NAME);
            info.load(fileInputStream);

            url = info.getProperty("url");
            try {
                driver = (Driver) Class.forName(DatabaseFactory.getInstance().findDefaultDriver(url), true,
                        Thread.currentThread().getContextClassLoader()).newInstance();

                connection = driver.connect(url, info);

                if (connection == null) {
                    throw new DatabaseException("Connection could not be created to " + url + " with driver "
                            + driver.getClass().getName() + ".  Possibly the wrong driver for the given database URL");
                }

                jdbcConnection = new JdbcConnection(connection);

            } catch (ClassNotFoundException e) {
                jdbcConnection = new OfflineConnection("offline:oracle?catalog=LBUSER", new ClassLoaderResourceAccessor());
            }


        }
    }

    private static FileInputStream getTestsProperties(final String testsProperties) throws FileNotFoundException {
        FileInputStream fileInputStream = null;

        final URL url = THIS_CLASS.getClassLoader().getResource(testsProperties);
        if (url != null) {
            fileInputStream = new FileInputStream(url.getFile());
        } else {
            LOGGER.info("no file '" + testsProperties + "' found in resources directory");
        }

        if (fileInputStream == null) {
            final String path = "src/test/resources/" + testsProperties;
            fileInputStream = new FileInputStream(path);
        }

        return fileInputStream;
    }

    public static void cleanDB() throws Exception {
        liquiBase = new Liquibase(changeLogFile, new ClassLoaderResourceAccessor(), jdbcConnection);
        if (connection != null) {
            liquiBase.dropAll();
        }
    }
}

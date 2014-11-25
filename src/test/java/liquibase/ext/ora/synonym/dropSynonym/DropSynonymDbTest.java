package liquibase.ext.ora.synonym.dropSynonym;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.ResultSet;
import java.sql.SQLException;

import liquibase.Contexts;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.ext.ora.synonym.BaseSynonymTest;
import liquibase.ext.ora.testing.BaseTestCase;

import org.junit.Before;
import org.junit.Test;

public class DropSynonymDbTest extends BaseSynonymTest {

	@Before
	public void setUp() throws Exception {
		changeLogFile = "liquibase/ext/ora/synonym/dropSynonym/changelog.text.xml";

		if (connection == null) {
			return;
		}

		connectToDB();
		cleanDB();
	}

	@Test
	public void testCompare() throws Exception {
		if (connection == null) {
			return;
		}


		((JdbcConnection) jdbcConnection).createStatement().executeQuery("CREATE OR REPLACE SYNONYM new_synonym FOR object");
		assertTrue(synonymExists());

		liquiBase.update(new Contexts());
		assertFalse(synonymExists());

	}
}

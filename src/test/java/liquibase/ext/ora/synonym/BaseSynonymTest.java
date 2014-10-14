package liquibase.ext.ora.synonym;

import java.sql.ResultSet;
import java.sql.SQLException;

import liquibase.exception.DatabaseException;
import liquibase.ext.ora.testing.BaseTestCase;

public abstract class BaseSynonymTest extends BaseTestCase {
	private static final String SYNONYM_CHECK = "SELECT synonym_name FROM user_synonyms "
			+ "WHERE synonym_name = 'NEW_SYNONYM'";

	protected boolean synonymExists() throws SQLException, DatabaseException {
		ResultSet result = jdbcConnection.createStatement().executeQuery(SYNONYM_CHECK);
		return result.next();
	}

}

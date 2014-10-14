package liquibase.ext.ora.synonym.createSynonym;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import liquibase.ext.ora.synonym.BaseSynonymTest;

import org.junit.Before;
import org.junit.Test;

public class CreateSynonymDbTest extends BaseSynonymTest {

	@Before
	public void setUp() throws Exception {
		changeLogFile = "liquibase/ext/ora/synonym/createSynonym/changelog.text.xml";
		connectToDB();
		cleanDB();
	}

	@Test
	public void testCompare() throws Exception {
		assertFalse(synonymExists());

		liquiBase.update(null);
		assertTrue(synonymExists());

		liquiBase.rollback(1, null);
		assertFalse(synonymExists());
	}

}

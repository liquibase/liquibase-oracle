package liquibase.ext.ora.synonym.createSynonym;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.ext.ora.synonym.BaseSynonymTest;

import org.junit.Before;
import org.junit.Test;

public class CreateSynonymDbTest extends BaseSynonymTest {

	@Before
	public void setUp() throws Exception {
		changeLogFile = "liquibase/ext/ora/synonym/createSynonym/changelog.text.xml";

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

		assertFalse(synonymExists());

		liquiBase.update(new Contexts());
		assertTrue(synonymExists());

		liquiBase.rollback(1, new Contexts(), new LabelExpression());
		assertFalse(synonymExists());
	}

}

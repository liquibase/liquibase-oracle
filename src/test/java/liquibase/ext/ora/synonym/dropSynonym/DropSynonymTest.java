package liquibase.ext.ora.synonym.dropSynonym;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.change.Change;
import liquibase.change.ChangeFactory;
import liquibase.change.ChangeMetaData;
import liquibase.changelog.ChangeLogParameters;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.database.Database;
import liquibase.database.core.OracleDatabase;
import liquibase.ext.ora.dropSynonym.DropSynonymChange;
import liquibase.ext.ora.dropSynonym.DropSynonymStatement;
import liquibase.ext.ora.testing.BaseTestCase;
import liquibase.parser.ChangeLogParserFactory;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.resource.ResourceAccessor;
import liquibase.sql.Sql;
import liquibase.sqlgenerator.SqlGeneratorFactory;
import liquibase.statement.SqlStatement;

import org.junit.Before;
import org.junit.Test;

public class DropSynonymTest extends BaseTestCase {
	private static final String SYNONYM_NAME = "synonym_name";
	private static final String SYNONYM_SCHEMA = "synonym_schema";

	@Before
	public void setUp() throws Exception {
		changeLogFile = "liquibase/ext/ora/synonym/dropSynonym/changelog.text.xml";
		connectToDB();
		cleanDB();
	}

	@Test
	public void getChangeMetaData() {
		DropSynonymChange change = new DropSynonymChange();
		assertEquals("dropSynonym", ChangeFactory.getInstance().getChangeMetaData(change).getName());
		assertEquals("Drop synonym", ChangeFactory.getInstance().getChangeMetaData(change).getDescription());
		assertEquals(ChangeMetaData.PRIORITY_DEFAULT, ChangeFactory.getInstance().getChangeMetaData(change).getPriority());
	}

	@Test
	public void getConfirmationMessage() {
		DropSynonymChange change = new DropSynonymChange();
		change.setSynonymName("new_synonym");
		assertEquals("Synonym new_synonym dropped", change.getConfirmationMessage());
	}

	@Test
	public void generateStatement() {
		DropSynonymChange change = new DropSynonymChange();

		change.setPublic(true);
		change.setSynonymSchemaName(SYNONYM_SCHEMA);
		change.setSynonymName(SYNONYM_NAME);
		change.setForce(true);

		Database database = new OracleDatabase();
		SqlStatement[] sqlStatements = change.generateStatements(database);

		assertEquals(1, sqlStatements.length);
		assertTrue(sqlStatements[0] instanceof DropSynonymStatement);

		DropSynonymStatement statement = (DropSynonymStatement) sqlStatements[0];

		assertTrue(statement.isForce());
		assertTrue(statement.isPublic());
		assertEquals(SYNONYM_NAME, statement.getSynonymName());
		assertEquals(SYNONYM_SCHEMA, statement.getSynonymSchemaName());
	}

	@Test
	public void parseAndGenerate() throws Exception {
		Database database = liquiBase.getDatabase();
		ResourceAccessor resourceAccessor = new ClassLoaderResourceAccessor();

		ChangeLogParameters changeLogParameters = new ChangeLogParameters();

		DatabaseChangeLog changeLog = ChangeLogParserFactory.getInstance().getParser(changeLogFile, resourceAccessor).parse(changeLogFile,
				changeLogParameters, resourceAccessor);

        liquiBase.checkLiquibaseTables(false, changeLog, new Contexts(), new LabelExpression());
		changeLog.validate(database);

		List<ChangeSet> changeSets = changeLog.getChangeSets();
		Change change = changeSets.get(0).getChanges().get(0);
		Sql[] sql = SqlGeneratorFactory.getInstance().generateSql(change.generateStatements(database)[0], database);

		String expected = "DROP SYNONYM new_synonym";
		assertEquals(expected, sql[0].toSql());
	}
}

package liquibase.ext.ora.synonym.createSynonym;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Scope;
import liquibase.change.Change;
import liquibase.change.ChangeFactory;
import liquibase.change.ChangeMetaData;
import liquibase.changelog.ChangeLogParameters;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.database.Database;
import liquibase.database.core.OracleDatabase;
import liquibase.ext.ora.createSynonym.CreateSynonymChange;
import liquibase.ext.ora.createSynonym.CreateSynonymStatement;
import liquibase.ext.ora.testing.BaseTestCase;
import liquibase.parser.ChangeLogParserFactory;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.resource.ResourceAccessor;
import liquibase.sql.Sql;
import liquibase.sqlgenerator.SqlGeneratorFactory;
import liquibase.statement.SqlStatement;

import org.junit.Before;
import org.junit.Test;

public class CreateSynonymTest extends BaseTestCase {
	private static final String OBJECT_SCHEMA = "object_schema";
	private static final String OBJECT_NAME = "object";
	private static final String SYNONYM_NAME = "synonym_name";
	private static final String SYNONYM_SCHEMA = "synonym_schema";

	@Before
	public void setUp() throws Exception {
		changeLogFile = "liquibase/ext/ora/synonym/createSynonym/changelog.text.xml";
		connectToDB();
		cleanDB();
	}

	@Test
	public void getChangeMetaData() {
		CreateSynonymChange createSynonymChange = new CreateSynonymChange();
		assertEquals("createSynonym", Scope.getCurrentScope().getSingleton(ChangeFactory.class).getChangeMetaData(createSynonymChange).getName());
		assertEquals("Create synonym", Scope.getCurrentScope().getSingleton(ChangeFactory.class).getChangeMetaData(createSynonymChange).getDescription());
		assertEquals(ChangeMetaData.PRIORITY_DEFAULT + 200, Scope.getCurrentScope().getSingleton(ChangeFactory.class).getChangeMetaData(createSynonymChange).getPriority());
	}

	@Test
	public void getConfirmationMessage() {
		CreateSynonymChange change = new CreateSynonymChange();
		change.setSynonymName("new_synonym");
		assertEquals("Synonym new_synonym created", change.getConfirmationMessage());
	}

	@Test
	public void generateStatement() {

		CreateSynonymChange change = new CreateSynonymChange();
		change.setReplace(true);
		change.setPublic(true);

		change.setSynonymSchemaName(SYNONYM_SCHEMA);
		change.setSynonymName(SYNONYM_NAME);
		change.setObjectSchemaName(OBJECT_SCHEMA);
		change.setObjectName(OBJECT_NAME);

		Database database = new OracleDatabase();
		SqlStatement[] sqlStatements = change.generateStatements(database);

		assertEquals(1, sqlStatements.length);
		assertTrue(sqlStatements[0] instanceof CreateSynonymStatement);

		CreateSynonymStatement statement = (CreateSynonymStatement) sqlStatements[0];

		assertTrue(statement.isReplace());
		assertTrue(statement.isPublic());

		assertEquals(OBJECT_NAME, statement.getObjectName());
		assertEquals(OBJECT_SCHEMA, statement.getObjectSchemaName());

		assertEquals(SYNONYM_NAME, statement.getSynonymName());
		assertEquals(SYNONYM_SCHEMA, statement.getSynonymSchemaName());
	}

	@Test
	public void parseAndGenerate() throws Exception {
		if (connection == null) {
			return;
		}

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

		String expected = "CREATE SYNONYM new_synonym FOR object";
		assertEquals(expected, sql[0].toSql());
	}

	@Test
	public void test() throws Exception {
		if (connection == null) {
			return;
		}
		liquiBase.update(new Contexts());
		liquiBase.rollback(1, new Contexts(), new LabelExpression());
	}
}

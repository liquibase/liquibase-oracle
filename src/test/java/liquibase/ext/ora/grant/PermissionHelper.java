package liquibase.ext.ora.grant;

import liquibase.database.core.OracleDatabase;
import liquibase.ext.ora.grant.addgrant.GrantObjectPermissionChange;
import liquibase.ext.ora.grant.revokegrant.RevokeObjectPermissionChange;
import liquibase.statement.SqlStatement;

import static org.junit.Assert.assertEquals;

/**
 * @author Gillsu George Thekkekara Puthenparampil <gillsu.george@gmail.com>
 */
public class PermissionHelper {
    public static final String SCHEMA_NAME = "SCHEMA_NAME";
    public static final String TABLE_NAME = "TABLE_NAME";
    public static final String RECIPIENT_USER = "RECIPIENT_USER";

    public static <T extends AbstractObjectPermissionStatement> T createObjectPermissionStatement(AbstractObjectPermissionChange change) {
        change = fillObjectPermissionChange(change);
        if ( change instanceof GrantObjectPermissionChange ) {
            ((GrantObjectPermissionChange) change).setGrantOption(true);
        }

        SqlStatement[] statements = change.generateStatements(new OracleDatabase());
        assertEquals(1, statements.length);

        SqlStatement statement = statements[0];

        return (T) statement;
    }

    public static GrantObjectPermissionChange createGrantObjectPermissionChangeWithAllPrivileges() {
        GrantObjectPermissionChange change = fillObjectPermissionChange(new GrantObjectPermissionChange());

        change.setGrantOption(true);

        return change;
    }

    public static RevokeObjectPermissionChange createRevokeObjectPermissionChangeWithAllPrivileges() {
        return fillObjectPermissionChange(new RevokeObjectPermissionChange());
    }

    private static <T extends AbstractObjectPermissionChange> T fillObjectPermissionChange(T change) {
        change.setSchemaName(SCHEMA_NAME);
        change.setObjectName(TABLE_NAME);
        change.setRecipientList(RECIPIENT_USER);
        change.setSelect(true);
        change.setUpdate(true);
        change.setInsert(true);
        change.setDelete(true);
        change.setExecute(true);
        change.setIndex(true);
        change.setReferences(true);

        return change;
    }
}

package liquibase.ext.ora;

import liquibase.change.AbstractChange;
import liquibase.database.Database;
import liquibase.database.core.OracleDatabase;

/**
 * Base class for Oracle changes.
 */
public abstract class AbstractOracleChange extends AbstractChange {

    /**
     * Only Oracle is supported for this extension changes
     */
    @Override
    public boolean supports(Database database) {
        return database instanceof OracleDatabase;
    }
}

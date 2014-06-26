package liquibase.ext.ora.grant;

import java.util.ArrayList;
import java.util.List;

import liquibase.database.Database;
import liquibase.database.core.OracleDatabase;
import liquibase.exception.ValidationErrors;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;

import org.apache.commons.lang.StringUtils;

public abstract class AbstractObjectPermissionGenerator<T extends AbstractObjectPermissionStatement> extends
		AbstractSqlGenerator<T> {

	protected String getPermissionList( AbstractObjectPermissionStatement statement ) {
        List<String> permissions = new ArrayList<String>(5);
        if ( statement.getSelect() ) {
        	permissions.add( "SELECT" );
        }
        if ( statement.getUpdate() ) {
        	permissions.add( "UPDATE" );
        }
        if ( statement.getInsert() ) {
        	permissions.add( "INSERT" );
        }
        if ( statement.getDelete() ) {
        	permissions.add( "DELETE" );
        }
        if ( statement.getExecute() ) {
        	permissions.add( "EXECUTE" );
        }
        return StringUtils.join(permissions, ',');
	}

    @Override
	public boolean supports(AbstractObjectPermissionStatement statement, Database database) {
        return database instanceof OracleDatabase;
    }

    @Override
	public ValidationErrors validate(AbstractObjectPermissionStatement statement,
                                     Database database, SqlGeneratorChain sqlGeneratorChain) {
        ValidationErrors validationErrors = new ValidationErrors();
        validationErrors.checkRequiredField("tableName", statement.getObjectName());
        validationErrors.checkRequiredField("recipientList", statement.getRecipientList());
        if ( !statement.getSelect()
        		&& !statement.getUpdate()
        		&& !statement.getInsert()
        		&& !statement.getDelete()
        		&& !statement.getExecute()
        		) {
        	validationErrors.addError("You must specify at least one permission type.");
        }
        return validationErrors;
    }

}

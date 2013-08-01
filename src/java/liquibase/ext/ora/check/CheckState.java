package liquibase.ext.ora.check;

import liquibase.statement.AbstractSqlStatement;

public class CheckState extends AbstractSqlStatement {


    private Boolean deferrable;
    private Boolean initiallyDeferred;
    private Boolean disable;
    private Boolean validate;
    private Boolean rely;

    public CheckState(Boolean deferrable, Boolean initiallyDeferred, Boolean disable, Boolean validate, Boolean rely) {
        this.deferrable = deferrable;
        this.disable = disable;
        this.initiallyDeferred = initiallyDeferred;
        this.rely = rely;
        this.validate = validate;
    }


    public CheckState() {
    }


    public void setDeferrable(Boolean deferrable) {
        this.deferrable = deferrable;
    }

    public Boolean getDeferrable() {
        return deferrable;
    }

    public void setInitiallyDeferred(Boolean init) {
        this.initiallyDeferred = init;
    }

    public Boolean getInitiallyDeferred() {
        return initiallyDeferred;
    }

    public void setDisable(Boolean disable) {
        this.disable = disable;
    }

    public Boolean getDisable() {
        return disable;
    }

    public void setValidate(Boolean validate) {
        this.validate = validate;
    }

    public Boolean getValidate() {
        return validate;
    }

    public void setRely(Boolean rely) {
        this.rely = rely;
    }

    public Boolean getRely() {
        return rely;
    }
}

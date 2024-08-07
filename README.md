liquibase-oracle [![Build and Test Extension](https://github.com/liquibase/liquibase-oracle/actions/workflows/build.yml/badge.svg)](https://github.com/liquibase/liquibase-oracle/actions/workflows/build.yml)
================

This extension adds Oracle-specific change tags to Liquibase. More details on these can be found
in the Liquibase Confluence site, in the [Oracle Extensions page](https://liquibase.jira.com/wiki/spaces/CONTRIB/pages/3112975/Oracle+Extensions).

# Oracle Extensions Overview

This repository contains specific extensions for **Liquibase** related to the **Oracle** database. These extensions allow you to work with Oracle-specific features during schema migration.

## Key Features

1. **Oracle Check Constraints**:
    - The following tags, not supported by Liquibase Core, were added:
        - `AddCheck`
        - `AddDeferredPrimaryKey`
        - `RenameTrigger`
        - `CreateMaterializedView`
        - `CreateTrigger`
        - `DisableConstraint`
        - `DropCheck`
        - `DropTrigger`
        - `EnableConstraint`
        - `EncapsulateTableWithView`
        - `LongUpdate`
        - `Merge`
        - `Truncate`
        - `DropMaterializedView`
        - `EnableTrigger`
        - `DisableTrigger`
        - `SplitTable`

## Usage

To use these extensions:
1. Include the `oracle-extensions.jar` file in your classpath.
2. Add the `ora` namespace to your XML root node:

```xml
<databaseChangeLog
    xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:ora="http://www.liquibase.org/xml/ns/dbchangelog-ext"
    xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-2.0.xsd
    http://www.liquibase.org/xml/ns/dbchangelog-ext http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-ext.xsd">
</databaseChangeLog>
```

## Available Commands/Tags

### Add Check

Adds a check constraint to an existing table.

Sample usage:

```xml
<ora:addCheck tableName="person"
    condition="id between 0 and 5"
    disable="true"
    deferrable="true"
    initiallyDeferred="true"/>
```

Certainly! Here's the content converted from HTML to Markdown:

---

## Available Attributes

| Attribute        | Description                                                                   |
|------------------|-------------------------------------------------------------------------------|
| `tableName`      | Name of the table to add the check constraint to (required)                   |
| `schemaName`     | Name of the table schema                                                      |
| `tablespace`     | Specify the tablespace in which the check constraint is to be created         |
| `condition`      | True/false expression (required)                                              |
| `constraintName` | Name of the check constraint                                                  |
| `deferrable`     | Indicates whether the check is deferrable                                     |
| `initiallyDeferred` | Indicates whether the check is initially deferred                          |
| `disable`        | Indicates whether the check is disabled                                       |
| `validate`       | Indicates whether the check is validated                                      |
| `rely`           | Indicates whether the check is relied upon                                    |

**Automatic Rollback Support:** YES

---

## Add Deferred Primary Key

Adds a deferred primary key out of an existing column or set of columns.

**Sample:**

```xml
<ora:addDeferredPrimaryKey
    tableName="AddDeferredPrimaryKeyTest"
    columnNames="id"
    deferrable="true"
    initiallyDeferred="true"
    constraintName="PK_AddDeferredPrimaryKeyTest"/>
```

**Available Attributes:**

| Attribute           | Description                                                                                                                                                                                          |
|---------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `tableName`         | Name of the table to create the primary key on (required)                                                                                                                                            |
| `schemaName`        | Name of the table schema                                                                                                                                                                             |
| `columnNames`       | Name of the column(s) (comma separated if multiple) to create the primary key on (required)                                                                                                          |
| `constraintName`    | Name of the primary key constraint (required)                                                                                                                                                        |
| `deferrable`        | Set DEFERRABLE (`deferrable="true"`) to indicate that in subsequent transactions you can use the SET CONSTRAINT clause to defer checking of this constraint until after the transaction is committed |
| `initiallyDeferred` | Set INITIALLY DEFERRED (`initiallyDeferred="true"`) to indicate that Oracle should check this constraint at the end of subsequent transactions                                                       |

**Automatic Rollback Support:** YES

---

Certainly! Here's the content converted from HTML to Markdown:

---

## RenameTrigger

Can disable, enable, or rename a trigger.

**Sample:**

```xml
<ora:renameTrigger triggerName="myTrigger" newName="RenamedMyTrigger"/>
```

**Available Attributes:**

| Attribute      | Description                           |
|----------------|---------------------------------------|
| `triggerName`  | Name of the trigger (required)         |
| `schemaName`   | Name of the schema                    |
| `newName`      | New name of the trigger (required)     |

**Automatic Rollback Support:** NO

---

## CreateMaterializedView

Creates a new materialized view.

**Sample:**

```xml
<ora:createMaterializedView subquery="select * from Table1" viewName="myView"/>
```

**Available Attributes:**

| Attribute          | Description                                                                                   |
|--------------------|-----------------------------------------------------------------------------------------------|
| `viewName`         | Name of the view (required)                                                                    |
| `subquery`         | Oracle Database executes this subquery and places the results in the materialized view (required) |
| `columnAliases`    | You can specify a column alias for each column of the materialized view                        |
| `objectType`       | The `objectType` clause lets you explicitly create an object materialized view of type `objectType` |
| `reducedPrecision` | Specify to authorize the loss of precision that will result if the precision of the table or materialized view columns do not exactly match the precision returned by the subquery, or to require that the precision of the table or materialized view columns match exactly the precision returned by the subquery, or the create operation will fail |
| `usingIndex`       | Specify "no" to suppress the creation of the default index                                     |
| `tableSpace`       | Specify the tablespace in which the materialized view is to be created                          |
| `forUpdate`        | Specify `FOR UPDATE` to allow a subquery, primary key, object, or rowid materialized view to be updated |
| `queryRewrite`     | Specify "enable" to enable the materialized view for query rewrite or "disable" to disable it  |

**Automatic Rollback Support:** NO

---

Certainly! Here's the content converted from HTML to Markdown:

---

## CreateTrigger

Creates a new trigger.

**Sample:**

```xml
<ora:createTrigger
    afterBeforeInsteadOf="before"
    procedure="DECLARE v_username varchar2(10); BEGIN SELECT pierwsza INTO v_username FROM TriggerTest; :new.created_by := v_username; END;"
    triggerName="myTrigger"
    tableName="TriggerTest"
    insert="true"
    forEachRow="true"/>
```

**Available Attributes:**

| Attribute            | Description                                                                                   |
|----------------------|-----------------------------------------------------------------------------------------------|
| `triggerName`        | Name of the trigger (required)                                                                |
| `afterBeforeInsteadOf` | Specify "before" to cause the database to fire the trigger before executing the triggering event. Specify "after" to cause the database to fire the trigger after executing the triggering event. Specify "insteadOf" to cause Oracle Database to fire the trigger instead of executing the triggering event (required) |
| `procedure`          | Specify the PL/SQL block that Oracle Database executes to fire the trigger or call a stored procedure rather than specifying the trigger code inline as a PL/SQL block (required) |
| `schemaName`         | Name of the schema                                                                            |
| `replace`            | Re-create the trigger if it already exists                                                     |
| `delete`             | Specify DELETE if you want the database to fire the trigger whenever a DELETE statement removes a row from the table or removes an element from a nested table |
| `insert`             | Specify INSERT if you want the database to fire the trigger whenever an INSERT statement adds a row to a table or adds an element to a nested table |
| `update`             | Specify UPDATE if you want the database to fire the trigger whenever an UPDATE statement changes a value in any column of the table or nested table |
| `updateOf`           | Specify if you want the database to fire the trigger whenever an UPDATE statement changes a value in one of the columns specified in `columnNames` |
| `tableName`          | Name of the table                                                                             |
| `columnNames`        | Name of columns required for `updateOf`                                                        |
| `whenCondition`      | Specify the trigger condition, which is an SQL condition that must be satisfied for the database to fire the trigger |
| `nestedTableColumn`  | Specify the `nested_table_column` of a view upon which the trigger is being defined |
| `viewName`           | Name of the view                                                                              |
| `forEachRow`         | Specify to designate that the trigger is a row-level trigger (required)                         |

**Automatic Rollback Support:** NO

---

Certainly! Here's the content converted from HTML to Markdown:

---

## Disable Constraint

Disables an existing constraint.

**Sample:**

```xml
<ora:disableConstraint tableName="test" constraintName="tom_check"/>
```

**Available Attributes:**

| Attribute        | Description                           |
|------------------|---------------------------------------|
| `constraintName` | Name of the constraint to disable (required) |
| `tableName`      | Name of the table to disable the constraint for (required) |
| `schemaName`     | Name of the table schema              |
| `tablespace`     | Specify the tablespace in which the constraint is to be disabled |

**Automatic Rollback Support:** YES

---

## Drop Check

Drops a check constraint from an existing table.

**Sample:**

```xml
<ora:dropCheck tableName="test" constraintName="tom_check"/>
```

**Available Attributes:**

| Attribute        | Description                           |
|------------------|---------------------------------------|
| `constraintName` | Name of the constraint to drop (required) |
| `tableName`      | Name of the table to drop the check constraint for (required) |
| `schemaName`     | Name of the table schema              |
| `tablespace`     | Specify the tablespace in which the check constraint is to be dropped |

**Automatic Rollback Support:** NO

---

## Drop Trigger

Removing the specific trigger.

**Sample:**

```xml
<ora:dropTrigger triggerName="myTrigger"/>
```

**Available Attributes:**

| Attribute      | Description                           |
|----------------|---------------------------------------|
| `triggerName`  | Name of the trigger (required)         |
| `schemaName`   | Name of the schema                    |

**Automatic Rollback Support:** NO

---

## Enable Constraint

Enables an existing constraint.

**Sample:**

```xml
<ora:enableConstraint tableName="test" constraintName="tom_check"/>
```

**Available Attributes:**

| Attribute        | Description                           |
|------------------|---------------------------------------|
| `constraintName` | Name of the constraint to enable (required) |
| `tableName`      | Name of the table to enable the constraint for (required) |
| `schemaName`     | Name of the table schema              |
| `tablespace`     | Specify the tablespace in which the constraint is to be enabled |

**Automatic Rollback Support:** YES

---

## Encapsulate Table With View

Renames table named 'tableName' to 'TtableName' and creates view named 'tableName'.

**Sample:**

```xml
<ora:encapsulateTableWithView tableName="test"/>
```

**Available Attributes:**

| Attribute        | Description                           |
|------------------|---------------------------------------|
| `tableName`      | Name of the table to encapsulate (required) |
| `schemaName`     | Name of the table schema              |

**Automatic Rollback Support:** YES

---

## Long Update

Performs long-running update in time intervals

**Sample:**

```xml
<ora:longUpdate commitInterval="5" sleepSeconds="1" updateSql="UPDATE LongUpdateTest SET name='checked' where name='test'" />
```

**Available Attributes:**

| Attribute        | Description                                     |
|------------------|-------------------------------------------------|
| `commitInterval` | Number of updated rows in one commit (required) |
| `sleepSeconds`   | Time between commits (required)                 |
| `updateSql`      | Update procedure [required]                     |

**Automatic Rollback Support:** NO

---

## Merge

Selects rows from source table or view for update or insertion into a target table or view. You can specify conditions to determine whether to update or insert into the target table or view.
(from Oracle Database SQL Reference)

**Sample:**

```xml
<ora:merge targetTableName="myTable2 m"
sourceTableName="myTable d"
onCondition="m.pid=d.pid"
insertColumnsValueList="d.pid,d.sales,'OLD'"
updateList="m.sales=m.sales+d.sales,m.status=d.status"
deleteCondition="m.status='OBS'"
/>
```

**Available Attributes:**

Certainly! Here's the content converted from HTML to Markdown:

---

## Available Attributes

| Attribute              | Description                                                                                   |
|------------------------|-----------------------------------------------------------------------------------------------|
| `sourceTableName`      | Name of the source table (required)                                                            |
| `sourceSchemaName`     | Name of the source table schema                                                                |
| `targetTableName`      | Name of the target table (required)                                                            |
| `targetSchemaName`     | Name of the target table schema                                                                |
| `onCondition`          | OnCondition clause (required)                                                                  |
| `updateCondition`      | UpdateCondition clause for the update statement                                                |
| `insertCondition`      | InsertCondition clause for the insert statement                                                |
| `deleteCondition`      | DeleteCondition clause for the delete statement                                                |
| `updateList`           | List of 'name=value' pairs separated by ',' for the update statement                            |
| `insertColumnsNameList` | List of column names for the insert statement                                                  |
| `insertColumnsValueList` | List of column values for the insert statement                                                 |

**Automatic Rollback Support:** NO

---

## Truncate

Truncates all data from an existing table or cluster.

**Sample:**

```xml
<ora:truncate tableName="truncatetest" purgeMaterializedViewLog="true" reuseStorage="true"/>
```

**Available Attributes:**

| Attribute                  | Description                                                                                                                            |
|----------------------------|----------------------------------------------------------------------------------------------------------------------------------------|
| `tableName`                | Name of the table to truncate (required)                                                                                               |
| `clusterName`              | Name of the cluster to truncate                                                                                                        |
 | `schemaName`               | Name of the table/cluster schema                                                                                                      |
| `purgeMaterializedViewLog` | Whether a materialized view log defined on the table is to be purged when the table is truncated (from Oracle Database SQL Reference). |
| `reuseStorage`             | Retain the space from the deleted rows allocated to the table or cluster (from Oracle Database SQL Reference).                         |

**Automatic Rollback Support:** NO

---

## Drop Materialized View

Drops a materialized view.

**Sample:**

```xml
<ora:dropMaterializedView viewName="myView"/>
```

**Available Attributes:**

| Attribute          | Description                                                                                   |
|--------------------|-----------------------------------------------------------------------------------------------|
| `viewName`         | Name of the view to drop (required)                                                            |
| `schemaName`       | Name of the view schema                                                                        |

**Automatic Rollback Support:** NO

---

## Enable Trigger

Enables a trigger.

**Sample:**

```xml
<ora:enableTrigger triggerName="myTrigger"/>
```

**Available Attributes:**

| Attribute      | Description                           |
|----------------|---------------------------------------|
| `triggerName`  | Name of the trigger (required)         |
| `schemaName`   | Name of the schema                    |

**Automatic Rollback Support:** NO

---

## Disable Trigger

Disables a trigger.

**Sample:**

```xml
<ora:disableTrigger triggerName="myTrigger"/>
```

**Available Attributes:**

| Attribute      | Description                           |
|----------------|---------------------------------------|
| `triggerName`  | Name of the trigger (required)         |
| `schemaName`   | Name of the schema                    |

**Automatic Rollback Support:** NO

---

## SplitTable

The `SplitTable` operation allows you to split a table into two separate tables, using a surrogate key. Here's how it works:

1. **Sample Initial Table Creation:**

   ```xml
   <changeSet author="Tomek" id="0">
       <createTable tableName="TABLE1">
           <column name="one" type="integer"></column>
           <column name="two" type="varchar(10)"></column>
           <column name="three" type="number"></column>
           <column name="four" type="number"></column>
       </createTable>
   </changeSet>
   ```

   This creates an initial table named `TABLE1` with columns `one`, `two`, `three`, and `four`.

2. **Splitting the Table:**

   ```xml
   <changeSet author="tomek" id="1" context="transition">
       <ora:splitTable
           primaryKeyColumnName="three_id"
           splitTableName="TABLE1"
           newTableName="TABLE2"
           columnNameList="three,four"/>
   </changeSet>
   ```

** Available attributes **

| Attribute              | Description                                                                                   |
|------------------------|-----------------------------------------------------------------------------------------------|
| `splitTableName`       | Name of the split table (required)                                                            |
| `splitTableSchemaName` | Name of the split table schema                                                                |
| `newTableName`         | Name of the new table (required)                                                              |
| `newTableSchemaName`   | Name of the new table schema                                                                  |
| `columnNameList`       | List of column names to move to the second table (required)                                   |
| `primaryKeyColumnName` | Name of the primary key column in the new table (only surrogate key) (required)              |

**Automatic Rollback Support:** No

**Rolling Upgrade Support:** YES

---
splitTable tag supports rolling upgrades through changeSet contexts. There are 3 available contexts:

* basic - no context, refeactoring without rolling upgrade
* transition - move database to transition state (two version of aplication work on the same database)
* resulting - move databese to resulting (final) state from transition state

**NOTE**
"rolling upgrade" - the process of incrementally taking down parts of the system for upgrade, without affecting the overall functionality. (http://en.wikipedia.org/wiki/Downtime)
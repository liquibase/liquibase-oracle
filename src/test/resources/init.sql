
ALTER SESSION SET CONTAINER=lbuser;

grant
   create session,
   create table,
   create trigger,
   create view,
   create any materialized view,
   create ANY synonym,
   ALTER ANY SNAPSHOT,
   dba
to lbuser;

GRANT ALL PRIVILEGES TO lbuser;


grant
   execute on dbms_lock
   to lbuser;

GRANT SELECT ON SYS.DBA_RECYCLEBIN TO LBUSER;
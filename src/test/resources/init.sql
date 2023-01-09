
ALTER SESSION SET CONTAINER=lbuser;

GRANT ALL PRIVILEGES TO lbuser;

grant
   execute on dbms_lock
   to lbuser;

GRANT SELECT ON SYS.DBA_RECYCLEBIN TO LBUSER;

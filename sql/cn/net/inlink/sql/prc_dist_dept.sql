-- Create table
create table PRC_DIST_DEPT
(
  flag       VARCHAR2(20) not null,
  department VARCHAR2(20)
)
tablespace ODSDATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
-- Create/Recreate primary, unique and foreign key constraints 
alter table PRC_DIST_DEPT
  add primary key (FLAG)
  using index 
  tablespace ODSDATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

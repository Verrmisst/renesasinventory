-- Create table
create table COMPANY_STATISTICS
(
  department VARCHAR2(50),
  qty        NUMBER
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

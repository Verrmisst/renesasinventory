-- Create table
create table FB_STATISTICS_FINAL
(
  locationcode VARCHAR2(50),
  department   VARCHAR2(50),
  flag         VARCHAR2(50),
  qty          NUMBER
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

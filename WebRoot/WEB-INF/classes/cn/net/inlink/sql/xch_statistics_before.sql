-- Create table
create table XCH_STATISTICS_BEFORE
(
  lotname   VARCHAR2(30),
  modelbody VARCHAR2(50),
  modelrepo VARCHAR2(20),
  qty       INTEGER,
  flag      VARCHAR2(3)
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
-- Add comments to the columns 
comment on column XCH_STATISTICS_BEFORE.modelbody
  is 'ÐÍÃû';
comment on column XCH_STATISTICS_BEFORE.modelrepo
  is '²Ö»ù';
comment on column XCH_STATISTICS_BEFORE.flag
  is '×´Ì¬';

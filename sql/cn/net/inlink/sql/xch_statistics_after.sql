-- Create table
create table XCH_STATISTICS_AFTER
(
  pkgid    VARCHAR2(20),
  baleqty  NUMBER(9),
  boxqty   NUMBER(3),
  orderno  VARCHAR2(10),
  branchno NUMBER(4),
  flag     VARCHAR2(3)
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
comment on column XCH_STATISTICS_AFTER.pkgid
  is '箱号';
comment on column XCH_STATISTICS_AFTER.baleqty
  is '捆包数量
';
comment on column XCH_STATISTICS_AFTER.boxqty
  is '箱数量
';
comment on column XCH_STATISTICS_AFTER.orderno
  is '订单号
';
comment on column XCH_STATISTICS_AFTER.branchno
  is '分纳号
';
comment on column XCH_STATISTICS_AFTER.flag
  is '状态';

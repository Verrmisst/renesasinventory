-- Create table
create table PRC_INVENTORY_RULE
(
  locationcode  VARCHAR2(32),
  operationname VARCHAR2(32),
  pkg           VARCHAR2(32),
  familymcode   VARCHAR2(64),
  product       VARCHAR2(32),
  waitdecide    VARCHAR2(32),
  department    VARCHAR2(32),
  factory       VARCHAR2(32),
  responser     VARCHAR2(32),
  inventorier   VARCHAR2(32),
  telephone     VARCHAR2(32)
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
comment on column PRC_INVENTORY_RULE.locationcode
  is '生产线
';
comment on column PRC_INVENTORY_RULE.operationname
  is '作业工程
';
comment on column PRC_INVENTORY_RULE.pkg
  is 'PKG
';
comment on column PRC_INVENTORY_RULE.familymcode
  is '机能
';
comment on column PRC_INVENTORY_RULE.product
  is '型名
';
comment on column PRC_INVENTORY_RULE.waitdecide
  is '待定
';
comment on column PRC_INVENTORY_RULE.department
  is '科室
';
comment on column PRC_INVENTORY_RULE.factory
  is '工厂
';
comment on column PRC_INVENTORY_RULE.responser
  is '反馈者
';
comment on column PRC_INVENTORY_RULE.inventorier
  is '盘点担当
';
comment on column PRC_INVENTORY_RULE.telephone
  is '联系电话
';

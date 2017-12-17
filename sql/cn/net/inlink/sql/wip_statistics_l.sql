-- Create table
create table WIP_STATISTICS_L
(
  lotname       VARCHAR2(50),
  product       VARCHAR2(60),
  仓基            VARCHAR2(120),
  prevlot       VARCHAR2(50),
  locationcode  VARCHAR2(6),
  operation     VARCHAR2(50),
  qty           NUMBER,
  pkg           VARCHAR2(12),
  familyl       VARCHAR2(50),
  familymcode   VARCHAR2(50),
  department    VARCHAR2(50),
  flag          VARCHAR2(20),
  shipmentorder VARCHAR2(50),
  costcode      VARCHAR2(50)
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
comment on column WIP_STATISTICS_L.locationcode
  is '生产线';
comment on column WIP_STATISTICS_L.familymcode
  is '机能';
comment on column WIP_STATISTICS_L.department
  is '科室';
comment on column WIP_STATISTICS_L.flag
  is '科室标记';

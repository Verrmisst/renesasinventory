-- Create table
create table PRC_WIP_DISTDEPART
(
  flag       VARCHAR2(20),
  department VARCHAR2(20),
  uploadadd  VARCHAR2(20)
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

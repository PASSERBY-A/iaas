ALTER TABLE T_HOST  DROP PRIMARY KEY CASCADE;

DROP TABLE T_HOST CASCADE CONSTRAINTS;

CREATE TABLE T_HOST 
(
   PK_HOST_ID           NUMBER(10)                     NOT NULL PRIMARY KEY,
   MANUFACTURER         VARCHAR2(200)                  NULL,
   PRODUCTNAME          VARCHAR2(200)                  NULL,
   SERIALNUMBER         VARCHAR2(200)                  NOT NULL,
   SERVERNAME           VARCHAR2(200)                  NULL,
   CPUCOUNT             INT                            NULL,
   CPUCORES             INT                            NULL,
   CPUTYPE              VARCHAR2(200)                  NULL,
   MEMORY               INT                            NULL,
   STATUS               INT                            NULL,
   SAVESTATUS			INT							   NULL,
   TYPE                 INT                            NULL,
   DESCRIPT             VARCHAR2(200)                  NULL,
   NIC                  INT                            NULL,
   BAY_INDEX            INT                            NULL,
   CREATED_BY           VARCHAR(60)                    NOT NULL,
   CREATED_ON           TIMESTAMP DEFAULT SYSTIMESTAMP NOT NULL,
   MODIFIED_BY          VARCHAR(60)                    NULL,
   MODIFIED_ON          TIMESTAMP DEFAULT SYSTIMESTAMP NOT NULL,
   HOSTNAME             VARCHAR2(200)                  NULL,
   RESOURCEPOOLID       VARCHAR2(200)                  NULL
);

COMMENT ON TABLE T_HOST IS 
'������Ϣ';

COMMENT ON COLUMN T_HOST.MANUFACTURER IS 
'����';

COMMENT ON COLUMN T_HOST.SERVERNAME IS 
'����������';

COMMENT ON COLUMN T_HOST.CPUCOUNT IS 
'CPU����';

COMMENT ON COLUMN T_HOST.CPUCORES IS 
'CPU����';


comment on column t_host.memory is 
'�ڴ棬��λMB';

comment on column t_host.saveStatus is 
'0��δ����
1��������';

comment on column t_host.status is 
'״̬:
1:��ʼ��
2���鵵�������浽�ʲ���
3������
4��ɾ��

';

comment on column t_host.type is '�������ͣ�x86����ʽ������--1;x86��Ƭ������--2;hpС�ͻ�--3';

comment on column t_host.nic is 
'��������';

comment on column t_host.hostname is 
'�������ƻ�IP';

comment on column t_host.resourcepoolid is 
' һ����Դ��ID';


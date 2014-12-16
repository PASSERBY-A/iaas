
ALTER TABLE T_HPVM  DROP PRIMARY KEY CASCADE;

DROP TABLE T_HPVM CASCADE CONSTRAINTS;

CREATE TABLE T_HPVM 
(
   PK_HPVM_ID           NUMBER                         NOT NULL PRIMARY KEY,
   VMNAME               VARCHAR2(200)                  NOT NULL,
   UUID                 VARCHAR2(200)                  NULL,
   SERIALNUMBER         VARCHAR2(200)                  NOT NULL,
   OS                   VARCHAR2(200)                  NULL,
   DEPRECATED           NUMBER(1)                      NULL,
   VMSTATUS             VARCHAR2(200)                  NULL,
   HOSTNAME             VARCHAR2(200)                  NULL,
   IP                   VARCHAR2(200)                  NULL,
   PATTERNPATH          VARCHAR2(200)                  NULL,
   CPUCOUNT             VARCHAR2(200)                  NULL,
   MEMORY               VARCHAR2(200)                  NULL,
   PHYSICALDEVICE       VARCHAR2(200)                  NULL,
   VMVERSION            VARCHAR2(200)                  NULL,
   CREATED_BY           VARCHAR(60)                    NOT NULL,
   CREATED_ON           TIMESTAMP DEFAULT SYSTIMESTAMP NOT NULL,
   MODIFIED_BY          VARCHAR(60)                    NULL,
   MODIFIED_ON          TIMESTAMP DEFAULT SYSTIMESTAMP NULL
);

COMMENT ON TABLE T_HPVM IS 
'HP���';

COMMENT ON COLUMN T_HPVM.DEPRECATED IS 
'����Ƿ��ʱ
1:����ʱ
0:��ǰ���ã�δ��ʱ';

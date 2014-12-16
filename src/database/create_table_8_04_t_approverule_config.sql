ALTER TABLE T_APPROVERULE_CONFIG
 DROP PRIMARY KEY CASCADE;
DROP TABLE T_APPROVERULE_CONFIG CASCADE CONSTRAINTS;
CREATE TABLE T_APPROVERULE_CONFIG(
       PK_APPROVERULE_CONFIG_ID     NUMBER              NOT NULL,
       WORKORDER_TYPE               INT                 NOT NULL,
       RESOURCEPOOL_ID              VARCHAR2(200)       NOT NULL,
       APPROVE_TYPE                 INT                 NOT NULL,
       NEG_APPROVE_DURA             INT                 NULL,
       LEVEL_FIR_APPROVE_ROLE       VARCHAR2(200)       NOT NULL,
       LEVEL_SEC_APPROVE_ROLE       VARCHAR2(200)       NULL,
       DESCRIPT                     VARCHAR2(200)       NULL,
       CREATED_BY                   VARCHAR2(200)       NOT NULL,
       CREATED_ON                   TIMESTAMP(6)        DEFAULT SYSTIMESTAMP NOT NULL,
       MODIFIED_BY                  VARCHAR2(200)       NULL,
       MODIFIED_ON                  TIMESTAMP(6)        DEFAULT SYSTIMESTAMP
);
ALTER TABLE T_APPROVERULE_CONFIG  ADD CONSTRAINT T_APPROVERULE_CONFIG_PK PRIMARY KEY
(
PK_APPROVERULE_CONFIG_ID
)
 ENABLE
;

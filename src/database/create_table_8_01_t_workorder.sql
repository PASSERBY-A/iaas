ALTER TABLE T_WORKORDER
 DROP PRIMARY KEY CASCADE;
DROP TABLE T_WORKORDER CASCADE CONSTRAINTS;
CREATE TABLE T_WORKORDER(
       PK_WORKORDER_ID              NUMBER              NOT NULL,
       WORKORDER_TYPE               INT                 NOT NULL,
       ACCOUNT_ID                   VARCHAR2(200)       NOT NULL,
       DOMAIN_ID                    VARCHAR2(200)       NOT NULL,
       APPLIER_ID                   VARCHAR2(200)       NOT NULL,
       APPLIER_NAME                 VARCHAR2(200)       NOT NULL,
       APPROVER_ID                  VARCHAR2(200)       NULL,
       APPROVER_NAME                VARCHAR2(200)       NULL,
       APPLIER_ABSOLUTE_DOMAIN      VARCHAR2(200)       NOT NULL,
       APPLIER_RELATIVE_DOMAIN      VARCHAR2(200)       NOT NULL,
       STATUS                       INT                 NOT NULL,
       DESCRIPT                     VARCHAR2(200)       NULL,
	   APPROVE_DESC					VARCHAR2(400)		NULL,
	   APPROVE_RESULT				INT				    NULL,
       ACCOUNT                      VARCHAR2(200)       NULL, 
	   ERROR_CODE					VARCHAR2(200)		NULL,
	   ERROR_TEXT					VARCHAR2(4000)		NULL,
       CREATED_BY                   VARCHAR2(200)       NOT NULL,
       CREATED_ON                   TIMESTAMP(6)        DEFAULT SYSTIMESTAMP NOT NULL,
       MODIFIED_BY                  VARCHAR2(200)       NULL,
       MODIFIED_ON                  TIMESTAMP(6)        DEFAULT SYSTIMESTAMP
);
ALTER TABLE T_WORKORDER  ADD CONSTRAINT T_WORKORDER_PK PRIMARY KEY
(
PK_WORKORDER_ID
)
 ENABLE
;

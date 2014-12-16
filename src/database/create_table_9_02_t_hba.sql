
ALTER TABLE T_HBA  DROP PRIMARY KEY CASCADE;

DROP TABLE T_HBA CASCADE CONSTRAINTS;

CREATE TABLE T_HBA 
(
   PK_HBA_ID            NUMBER                         NOT NULL PRIMARY KEY,
   TYPE                 VARCHAR2(200)                  NULL,
   WWN                  VARCHAR2(200)                  NULL,
   FK_HOST_SN           VARCHAR2(200)                  NOT NULL,
   CREATED_BY           VARCHAR(60)                    NOT NULL,
   CREATED_ON           TIMESTAMP DEFAULT SYSTIMESTAMP NOT NULL,
   MODIFIED_BY          VARCHAR(60)                    NULL,
   MODIFIED_ON          TIMESTAMP DEFAULT SYSTIMESTAMP NOT NULL
);

COMMENT ON TABLE T_HBA IS 
'HBA��Ϣ';

COMMENT ON COLUMN T_HBA.FK_HOST_SN IS 
'����ID';
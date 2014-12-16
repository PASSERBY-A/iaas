
ALTER TABLE T_EQUIPMENTEVENT  DROP PRIMARY KEY CASCADE;

DROP TABLE T_EQUIPMENTEVENT CASCADE CONSTRAINTS;

CREATE TABLE T_EQUIPMENTEVENT 
(
   PK_EQUIEVENT_ID      NUMBER                         NOT NULL PRIMARY KEY,
   SERIALNUMBER         VARCHAR2(200)                  NOT NULL,
   EVENTTYPE            VARCHAR2(200)                  NOT NULL,
   EVENTSTATUS          INT                            NOT NULL,
   EQUIPMENTTYPE		INT							   NULL,
   CREATED_BY           VARCHAR(60)                    NOT NULL,
   CREATED_ON           TIMESTAMP DEFAULT SYSTIMESTAMP NOT NULL,
   MODIFIED_BY          VARCHAR(60)                    NULL,
   MODIFIED_ON          TIMESTAMP DEFAULT SYSTIMESTAMP NULL,
   DESCRIPT             VARCHAR2(200)                  NULL
);

COMMENT ON TABLE T_EQUIPMENTEVENT IS 
'�豸�¼�';

COMMENT ON COLUMN T_EQUIPMENTEVENT.EVENTTYPE IS 
'1.����������
2.�Ƴ�������
3.�仯λ��
4������״̬�仯';
COMMENT ON COLUMN T_EQUIPMENTEVENT.EVENTSTATUS IS 
'�¼�״̬��
1-----��������
2-----��������
3-----�رգ�';
comment on column T_EQUIPMENTEVENT.EQUIPMENTTYPE is 'x86����ʽ������--1;x86��Ƭ������--2;hpС�ͻ�--3;x86����-4';
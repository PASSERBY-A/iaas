0. ��װoracle client�ˣ�����sqlplus���
1. �༭build.sql, ����DB_LINSTER �޸�Ϊ�㱾�����õ�linster db ��alias
	DEFINE DB_SCHEMA = iaas2dba
	DEFINE DB_SCHEMA_PASSWORD = iaas2dba
	DEFINE DB_LINSTER = local_iaas
2. ����cmd��cd ��ǰĿ¼
3. ���sqlplus sys/{password}@{linster_db_alias} as sysdba
4. SQL> @build
5. build���̽�������־�ļ�_log_create_schema.log��_log_createSchemaObjects.log����һ��build��ʱ����־��drop����ʱ����ʾ�Ҳ�����������������ʾ
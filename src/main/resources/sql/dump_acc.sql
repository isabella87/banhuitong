REM Generated at 2018-04-09 15:23:44
REM PRODUCT VERSION: Oracle Database 11g Enterprise Edition 11.2.0.3.0 64bit Production
REM USER: ACC

REM Create common procedures
CREATE OR REPLACE PROCEDURE "DROP_TABLE_IF_EXISTS" (V_TABLE_NAME VARCHAR2) IS
BEGIN
  EXECUTE IMMEDIATE 'DROP TABLE ' || V_TABLE_NAME;
EXCEPTION
  WHEN OTHERS THEN
    IF SQLCODE <> -0942 THEN
      RAISE;
    END IF;
END DROP_TABLE_IF_EXISTS;
/
CREATE OR REPLACE PROCEDURE "DROP_SEQUENCE_IF_EXISTS" (V_SEQUENCE_NAME VARCHAR2) IS
BEGIN
  EXECUTE IMMEDIATE 'DROP SEQUENCE ' || V_SEQUENCE_NAME;
EXCEPTION
  WHEN OTHERS THEN
    IF SQLCODE <> -2289 THEN
      RAISE;
    END IF;
END DROP_SEQUENCE_IF_EXISTS;
/

REM Drop tables
CALL DROP_TABLE_IF_EXISTS('ACC_ORIGIN_RUNNING');
CALL DROP_TABLE_IF_EXISTS('ACC_JX_USER');
CALL DROP_TABLE_IF_EXISTS('ACC_CORP_INFO');
CALL DROP_TABLE_IF_EXISTS('TS_ORIGIN_HISTORY');
CALL DROP_TABLE_IF_EXISTS('SYS_TABLE_INFO');
CALL DROP_TABLE_IF_EXISTS('SYS_MODULE_INFO');
CALL DROP_TABLE_IF_EXISTS('MSG_SMS_YM');
CALL DROP_TABLE_IF_EXISTS('MSG_REG_USER');
CALL DROP_TABLE_IF_EXISTS('MSG_LOST_PWD');
CALL DROP_TABLE_IF_EXISTS('MSG_CHANGE_MOBILE');
CALL DROP_TABLE_IF_EXISTS('CP_USER');
CALL DROP_TABLE_IF_EXISTS('CP_SEQ');
CALL DROP_TABLE_IF_EXISTS('ACC_USER_REG_PWD_HISTORY');
CALL DROP_TABLE_IF_EXISTS('ACC_USER_REG');
CALL DROP_TABLE_IF_EXISTS('ACC_USER_OPERATION_LOG');
CALL DROP_TABLE_IF_EXISTS('ACC_PERSON_INFO');
CALL DROP_TABLE_IF_EXISTS('ACC_MESSAGE');
CALL DROP_TABLE_IF_EXISTS('ACC_INNER_USER_REG');
CALL DROP_TABLE_IF_EXISTS('ACC_FRIENDS_PATH');
CALL DROP_TABLE_IF_EXISTS('ACC_FRIENDS');
/

REM Create Table ACC_FRIENDS
CREATE TABLE "ACC_FRIENDS" (
	"A_ID" NUMBER NOT NULL,
	"D_ID" NUMBER NOT NULL,
	"BALANCE" NUMBER NULL
);
REM Column comments ACC_FRIENDS
COMMENT ON COLUMN "ACC_FRIENDS"."A_ID" IS '���Ƚڵ�';
COMMENT ON COLUMN "ACC_FRIENDS"."D_ID" IS '����ڵ�';
COMMENT ON COLUMN "ACC_FRIENDS"."BALANCE" IS '�˻����';
REM Primary key ACC_FRIENDS
ALTER TABLE "ACC_FRIENDS" ADD CONSTRAINT "ACC_FRIENDS_PK" PRIMARY KEY (D_ID) USING INDEX;
/

REM Create Table ACC_FRIENDS_PATH
CREATE TABLE "ACC_FRIENDS_PATH" (
	"A_ID" NUMBER NOT NULL,
	"D_ID" NUMBER NULL,
	"DEPTH" NUMBER NULL
);
REM Column comments ACC_FRIENDS_PATH
COMMENT ON COLUMN "ACC_FRIENDS_PATH"."A_ID" IS '���Ƚڵ�';
COMMENT ON COLUMN "ACC_FRIENDS_PATH"."D_ID" IS '����ڵ�';
COMMENT ON COLUMN "ACC_FRIENDS_PATH"."DEPTH" IS '��';
REM Primary key ACC_FRIENDS_PATH
ALTER TABLE "ACC_FRIENDS_PATH" ADD CONSTRAINT "ACC_FRIENDS_PATH_PK" PRIMARY KEY (A_ID) USING INDEX;
/

REM Create Table ACC_INNER_USER_REG
CREATE TABLE "ACC_INNER_USER_REG" (
	"AU_ID" NUMBER NULL
);
REM Column comments ACC_INNER_USER_REG
/

REM Create Table ACC_MESSAGE
CREATE TABLE "ACC_MESSAGE" (
	"AM_ID" NUMBER NOT NULL,
	"CONTENT" VARCHAR2(4000 CHAR) NOT NULL,
	"CREATE_TIME" DATE NOT NULL,
	"CREATOR" VARCHAR2(1280 CHAR) NOT NULL,
	"TITLE" VARCHAR2(1280 CHAR) NOT NULL,
	"BRIEF" VARCHAR2(4000 CHAR) NOT NULL,
	"AU_ID" NUMBER NOT NULL,
	"READ_TIME" DATE NULL,
	"TYPE" NUMBER DEFAULT 1 NULL
);
REM Column comments ACC_MESSAGE
COMMENT ON COLUMN "ACC_MESSAGE"."AM_ID" IS '����';
COMMENT ON COLUMN "ACC_MESSAGE"."CONTENT" IS '��Ϣ����';
COMMENT ON COLUMN "ACC_MESSAGE"."CREATE_TIME" IS '��Ϣ����ʱ��';
COMMENT ON COLUMN "ACC_MESSAGE"."CREATOR" IS '������';
COMMENT ON COLUMN "ACC_MESSAGE"."TITLE" IS '����';
COMMENT ON COLUMN "ACC_MESSAGE"."BRIEF" IS 'ժҪ';
COMMENT ON COLUMN "ACC_MESSAGE"."READ_TIME" IS '�Ѷ�ʱ��';
COMMENT ON COLUMN "ACC_MESSAGE"."TYPE" IS '��Ϣ���ͣ�1����ͨ��Ϣ��2�������Э��ǩ����Ϣ����';
REM Primary key ACC_MESSAGE
ALTER TABLE "ACC_MESSAGE" ADD CONSTRAINT "ACC_MESSAGE_PK" PRIMARY KEY (AM_ID) USING INDEX;
/

REM Create Table ACC_PERSON_INFO
CREATE TABLE "ACC_PERSON_INFO" (
	"AU_ID" NUMBER NOT NULL,
	"ADDRESS" VARCHAR2(2000 CHAR) NULL,
	"HOME_PHONE" VARCHAR2(2000 CHAR) NULL,
	"POSTAL_CODE" VARCHAR2(2000 CHAR) NULL,
	"POSITION" VARCHAR2(2000 CHAR) NULL,
	"COMPANY" VARCHAR2(2000 CHAR) NULL,
	"COMPANY_TYPE" VARCHAR2(2000 CHAR) NULL,
	"QQ_NUMBER" VARCHAR2(2000 CHAR) NULL,
	"UPDATE_TIME" DATE NULL,
	"CREATE_TIME" DATE NULL,
	"REAL_NAME" VARCHAR2(2000 CHAR) NULL,
	"ID_CARD" VARCHAR2(2000 CHAR) NULL,
	"EMAIL" VARCHAR2(2000 CHAR) NULL,
	"ORG_CODE" VARCHAR2(2000 CHAR) NULL
);
REM Column comments ACC_PERSON_INFO
COMMENT ON COLUMN "ACC_PERSON_INFO"."AU_ID" IS '����';
COMMENT ON COLUMN "ACC_PERSON_INFO"."ADDRESS" IS 'ͨѶ��ַ';
COMMENT ON COLUMN "ACC_PERSON_INFO"."HOME_PHONE" IS '��ͥ�绰';
COMMENT ON COLUMN "ACC_PERSON_INFO"."POSTAL_CODE" IS '��������';
COMMENT ON COLUMN "ACC_PERSON_INFO"."POSITION" IS 'ְ��';
COMMENT ON COLUMN "ACC_PERSON_INFO"."COMPANY" IS '������λ';
COMMENT ON COLUMN "ACC_PERSON_INFO"."COMPANY_TYPE" IS '��λ����';
COMMENT ON COLUMN "ACC_PERSON_INFO"."QQ_NUMBER" IS 'qq����';
COMMENT ON COLUMN "ACC_PERSON_INFO"."UPDATE_TIME" IS '�޸�ʱ��';
COMMENT ON COLUMN "ACC_PERSON_INFO"."CREATE_TIME" IS '����ʱ��';
COMMENT ON COLUMN "ACC_PERSON_INFO"."REAL_NAME" IS '��ʵ����';
COMMENT ON COLUMN "ACC_PERSON_INFO"."ID_CARD" IS '����֤';
COMMENT ON COLUMN "ACC_PERSON_INFO"."EMAIL" IS '����';
COMMENT ON COLUMN "ACC_PERSON_INFO"."ORG_CODE" IS '������ҵ���';
REM Primary key ACC_PERSON_INFO
ALTER TABLE "ACC_PERSON_INFO" ADD CONSTRAINT "ACC_PERSON_INFO_PK" PRIMARY KEY (AU_ID) USING INDEX;
/

REM Create Table ACC_USER_OPERATION_LOG
CREATE TABLE "ACC_USER_OPERATION_LOG" (
	"AU_ID" NUMBER NOT NULL,
	"OP_TIME" DATE NOT NULL,
	"OP_TYPE" NUMBER NULL,
	"OP_RESULT" NUMBER NULL,
	"IP" VARCHAR2(160 CHAR) NULL,
	"REMARK" VARCHAR2(4000 CHAR) NULL
);
REM Column comments ACC_USER_OPERATION_LOG
COMMENT ON COLUMN "ACC_USER_OPERATION_LOG"."AU_ID" IS '�����û�id';
COMMENT ON COLUMN "ACC_USER_OPERATION_LOG"."OP_TIME" IS '����ʱ��';
COMMENT ON COLUMN "ACC_USER_OPERATION_LOG"."OP_TYPE" IS '�������ͣ�1ע�᣻2��¼��3�ǳ���4������5�����������룻6�����ֻ��ţ�7����������8�����Ƽ��룻9��ֵ��10���֣�11����ծȨת�ã�12����ծȨת�ã�13Ͷ�ʣ�14ծȨȨ�룻';
COMMENT ON COLUMN "ACC_USER_OPERATION_LOG"."OP_RESULT" IS '0ʧ�ܣ�1�ɹ�';
COMMENT ON COLUMN "ACC_USER_OPERATION_LOG"."IP" IS '��������ip';
COMMENT ON COLUMN "ACC_USER_OPERATION_LOG"."REMARK" IS '��ע';
REM Primary key ACC_USER_OPERATION_LOG
ALTER TABLE "ACC_USER_OPERATION_LOG" ADD CONSTRAINT "ACC_USER_OPERATION_LOG_PK" PRIMARY KEY (AU_ID,OP_TIME) USING INDEX;
/

REM Create Table ACC_USER_REG
CREATE TABLE "ACC_USER_REG" (
	"AU_ID" NUMBER NOT NULL,
	"LOGIN_NAME" VARCHAR2(640 CHAR) NOT NULL,
	"PASSWORD" VARCHAR2(3200 CHAR) NOT NULL,
	"USER_TYPE" NUMBER(3) NOT NULL,
	"RECOMMEND_MOBILE" VARCHAR2(1280 CHAR) NULL,
	"STATUS" NUMBER(3) NOT NULL,
	"REG_TIME" DATE NOT NULL,
	"AUDITOR" VARCHAR2(4000 CHAR) NULL,
	"AUDIT_TIME" DATE NULL,
	"ALLOW_INVEST" NUMBER DEFAULT 0 NULL,
	"ALLOW_BORROW" NUMBER DEFAULT 0 NULL,
	"MOBILE" VARCHAR2(80 CHAR) NULL,
	"REC_INFO_MODI_COUNT" NUMBER(2) DEFAULT 0 NULL,
	"OLD_INVEST_COUNT" NUMBER DEFAULT 0 NULL,
	"SRC" NUMBER(2) DEFAULT 0 NOT NULL,
	"LOCKED" NUMBER DEFAULT 0 NOT NULL,
	"LAST_LOGIN_FAILED_DATE" DATE NULL,
	"LOGIN_FAILED_COUNT" NUMBER DEFAULT 0 NULL,
	"GESTURE_PWD" VARCHAR2(1600) NULL,
	"GESTURE_PWD_STATUS" NUMBER NULL,
	"UUID" VARCHAR2(100) NULL
);
REM Column comments ACC_USER_REG
COMMENT ON COLUMN "ACC_USER_REG"."AU_ID" IS '����';
COMMENT ON COLUMN "ACC_USER_REG"."LOGIN_NAME" IS '�û���';
COMMENT ON COLUMN "ACC_USER_REG"."PASSWORD" IS '����';
COMMENT ON COLUMN "ACC_USER_REG"."USER_TYPE" IS '�û����ͣ�1����,2������';
COMMENT ON COLUMN "ACC_USER_REG"."RECOMMEND_MOBILE" IS '�Ƽ����ֻ���';
COMMENT ON COLUMN "ACC_USER_REG"."STATUS" IS '�û�״̬��0�����״̬����״ֻ̬�����ڻ�������2�����ע�᣻98������99ͣ�ã�';
COMMENT ON COLUMN "ACC_USER_REG"."REG_TIME" IS 'ע��ʱ��';
COMMENT ON COLUMN "ACC_USER_REG"."AUDITOR" IS '�����id';
COMMENT ON COLUMN "ACC_USER_REG"."AUDIT_TIME" IS '���ʱ��';
COMMENT ON COLUMN "ACC_USER_REG"."ALLOW_INVEST" IS '�Ƿ�����Ͷ��';
COMMENT ON COLUMN "ACC_USER_REG"."ALLOW_BORROW" IS '�Ƿ��������';
COMMENT ON COLUMN "ACC_USER_REG"."MOBILE" IS '�ֻ�����';
COMMENT ON COLUMN "ACC_USER_REG"."REC_INFO_MODI_COUNT" IS '�Ƽ��루�ֻ��ţ�Ĭ���޸Ĵ���';
COMMENT ON COLUMN "ACC_USER_REG"."OLD_INVEST_COUNT" IS '��ϵͳ��Ͷ�ʴ���';
COMMENT ON COLUMN "ACC_USER_REG"."SRC" IS 'ע����Դ 0-δ֪ 1-PC 2-�ƶ���';
COMMENT ON COLUMN "ACC_USER_REG"."LOCKED" IS '0δ����1����';
COMMENT ON COLUMN "ACC_USER_REG"."LAST_LOGIN_FAILED_DATE" IS '���һ��ʧ�ܵ�¼ʱ��';
COMMENT ON COLUMN "ACC_USER_REG"."LOGIN_FAILED_COUNT" IS '���5����ʧ�ܵ�¼����';
COMMENT ON COLUMN "ACC_USER_REG"."GESTURE_PWD" IS '��������';
COMMENT ON COLUMN "ACC_USER_REG"."GESTURE_PWD_STATUS" IS '��������״̬��0δ���ã�1������δ���ã�2�����ã�98 ������99 ͣ�ã�';
COMMENT ON COLUMN "ACC_USER_REG"."UUID" IS '�ֻ�Ψһ��ʶ��';
REM Primary key ACC_USER_REG
ALTER TABLE "ACC_USER_REG" ADD CONSTRAINT "ACC_USER_REG_PK" PRIMARY KEY (AU_ID) USING INDEX;
/

REM Create Table ACC_USER_REG_PWD_HISTORY
CREATE TABLE "ACC_USER_REG_PWD_HISTORY" (
	"ID" NUMBER NOT NULL,
	"OPERATOR" NUMBER NULL,
	"OPERATE_TIME" DATE NULL,
	"SOURCE" NUMBER NULL,
	"VCODE" VARCHAR2(1280 CHAR) NULL
);
REM Column comments ACC_USER_REG_PWD_HISTORY
COMMENT ON COLUMN "ACC_USER_REG_PWD_HISTORY"."OPERATOR" IS 'ע���au_id��0����̨���룩';
COMMENT ON COLUMN "ACC_USER_REG_PWD_HISTORY"."OPERATE_TIME" IS '����ʱ��';
COMMENT ON COLUMN "ACC_USER_REG_PWD_HISTORY"."SOURCE" IS '��Դ��1�ֻ���֤�뷽ʽ��9��̨�ֶ����뷽ʽ';
COMMENT ON COLUMN "ACC_USER_REG_PWD_HISTORY"."VCODE" IS '��֤��';
REM Primary key ACC_USER_REG_PWD_HISTORY
ALTER TABLE "ACC_USER_REG_PWD_HISTORY" ADD CONSTRAINT "ACC_USER_REG_PWD_HISTORY_PK" PRIMARY KEY (ID) USING INDEX;
/

REM Create Table CP_SEQ
CREATE TABLE "CP_SEQ" (
	"DAY" VARCHAR2(8) NULL,
	"SEQ" NUMBER(8) NULL
);
REM Column comments CP_SEQ
/

REM Create Table CP_USER
CREATE TABLE "CP_USER" (
	"CU_ID" NUMBER NULL,
	"USER_ID" VARCHAR2(200) NULL,
	"DATEPOINT" TIMESTAMP(6) NULL,
	"USER_TYPE" NUMBER NULL,
	"ROLE_TYPE" NUMBER NULL
);
REM Column comments CP_USER
/

REM Create Table MSG_CHANGE_MOBILE
CREATE TABLE "MSG_CHANGE_MOBILE" (
	"M_ID" NUMBER NOT NULL,
	"MOBILE" VARCHAR2(3200 CHAR) NOT NULL,
	"V_CODE" VARCHAR2(320 CHAR) NOT NULL,
	"DATEPOINT" DATE NOT NULL,
	"STATUS" NUMBER DEFAULT 0 NOT NULL
);
REM Column comments MSG_CHANGE_MOBILE
COMMENT ON COLUMN "MSG_CHANGE_MOBILE"."M_ID" IS 'id��';
COMMENT ON COLUMN "MSG_CHANGE_MOBILE"."MOBILE" IS '�ֻ���';
COMMENT ON COLUMN "MSG_CHANGE_MOBILE"."V_CODE" IS '��֤��';
COMMENT ON COLUMN "MSG_CHANGE_MOBILE"."DATEPOINT" IS '����ʱ��';
COMMENT ON COLUMN "MSG_CHANGE_MOBILE"."STATUS" IS '0��δ��֤��������֤��ʹ�ù���';
REM Primary key MSG_CHANGE_MOBILE
ALTER TABLE "MSG_CHANGE_MOBILE" ADD CONSTRAINT "MSG_CHANGE_MOBILE_PK" PRIMARY KEY (M_ID) USING INDEX;
/

REM Create Table MSG_LOST_PWD
CREATE TABLE "MSG_LOST_PWD" (
	"M_ID" NUMBER NOT NULL,
	"MOBILE" VARCHAR2(3200 CHAR) NOT NULL,
	"V_CODE" VARCHAR2(320 CHAR) NOT NULL,
	"DATEPOINT" DATE NOT NULL,
	"STATUS" NUMBER DEFAULT 0 NOT NULL
);
REM Column comments MSG_LOST_PWD
COMMENT ON COLUMN "MSG_LOST_PWD"."M_ID" IS '����';
COMMENT ON COLUMN "MSG_LOST_PWD"."MOBILE" IS '�ֻ���';
COMMENT ON COLUMN "MSG_LOST_PWD"."V_CODE" IS '������';
COMMENT ON COLUMN "MSG_LOST_PWD"."DATEPOINT" IS '����ʱ��';
COMMENT ON COLUMN "MSG_LOST_PWD"."STATUS" IS '״̬��0δ��֤��1����֤����ʹ�ã�';
REM Primary key MSG_LOST_PWD
ALTER TABLE "MSG_LOST_PWD" ADD CONSTRAINT "MSG_LOST_PWD_PK" PRIMARY KEY (M_ID) USING INDEX;
/

REM Create Table MSG_REG_USER
CREATE TABLE "MSG_REG_USER" (
	"M_ID" NUMBER NOT NULL,
	"MOBILE" VARCHAR2(4000 CHAR) NOT NULL,
	"V_CODE" VARCHAR2(640 CHAR) NOT NULL,
	"DATEPOINT" DATE NOT NULL,
	"STATUS" NUMBER DEFAULT 0 NOT NULL
);
REM Column comments MSG_REG_USER
COMMENT ON COLUMN "MSG_REG_USER"."M_ID" IS 'id��';
COMMENT ON COLUMN "MSG_REG_USER"."MOBILE" IS '�ֻ�����';
COMMENT ON COLUMN "MSG_REG_USER"."V_CODE" IS '��֤��';
COMMENT ON COLUMN "MSG_REG_USER"."DATEPOINT" IS '����ʱ��';
COMMENT ON COLUMN "MSG_REG_USER"."STATUS" IS '0��δ��֤��1������֤����ʹ�ù���';
REM Primary key MSG_REG_USER
ALTER TABLE "MSG_REG_USER" ADD CONSTRAINT "MSG_REG_USER_PK" PRIMARY KEY (M_ID) USING INDEX;
/

REM Create Table MSG_SMS_YM
CREATE TABLE "MSG_SMS_YM" (
	"MOBILE" VARCHAR2(4000 CHAR) NOT NULL,
	"CONTENT" VARCHAR2(4000 CHAR) NOT NULL,
	"T_CODE" NUMBER NOT NULL,
	"P_ID" NUMBER NOT NULL,
	"STATUS" NUMBER DEFAULT 0 NOT NULL,
	"RETRY_COUNT" NUMBER DEFAULT 0 NOT NULL,
	"S_CODE" NUMBER DEFAULT 0 NOT NULL,
	"ERROR" VARCHAR2(4000 CHAR) NULL,
	"DATEPOINT" DATE NOT NULL,
	"DATEPOINT2" DATE NULL,
	"MS_ID" NUMBER NOT NULL
);
REM Column comments MSG_SMS_YM
COMMENT ON COLUMN "MSG_SMS_YM"."MOBILE" IS '�ֻ�����';
COMMENT ON COLUMN "MSG_SMS_YM"."CONTENT" IS '����';
COMMENT ON COLUMN "MSG_SMS_YM"."T_CODE" IS 'ҵ������������ͣ�1 - �û�ע��  ,2 - ��ʧ���� ,3 - �޸��ֻ���4 - ��ͬǩ��';
COMMENT ON COLUMN "MSG_SMS_YM"."P_ID" IS '������ҵ���������ID';
COMMENT ON COLUMN "MSG_SMS_YM"."DATEPOINT" IS '�����¼��ʱ��';
REM Primary key MSG_SMS_YM
ALTER TABLE "MSG_SMS_YM" ADD CONSTRAINT "MSG_SMS_YM_PK" PRIMARY KEY (MS_ID) USING INDEX;
/

REM Create Table SYS_MODULE_INFO
CREATE TABLE "SYS_MODULE_INFO" (
	"SMI_ID" NUMBER NOT NULL,
	"MODULE_NAME" VARCHAR2(80 CHAR) NOT NULL,
	"MODULE_NO" NUMBER NOT NULL,
	"MUDULE_SHORT_NAME" VARCHAR2(40 CHAR) NOT NULL,
	"MUDULE_DESC" VARCHAR2(400 CHAR) NOT NULL,
	"CREATOR" VARCHAR2(80 CHAR) NOT NULL,
	"CREATE_TIME" DATE NOT NULL
);
COMMENT ON TABLE "SYS_MODULE_INFO" IS 'ϵͳģ����Ϣ��';
REM Column comments SYS_MODULE_INFO
COMMENT ON COLUMN "SYS_MODULE_INFO"."SMI_ID" IS '����';
COMMENT ON COLUMN "SYS_MODULE_INFO"."MODULE_NAME" IS '����ģ������';
COMMENT ON COLUMN "SYS_MODULE_INFO"."MODULE_NO" IS 'ģ�����';
COMMENT ON COLUMN "SYS_MODULE_INFO"."MUDULE_SHORT_NAME" IS 'ģ������ƣ���Ӧģ����Ը�ֵΪǰ׺��';
COMMENT ON COLUMN "SYS_MODULE_INFO"."MUDULE_DESC" IS 'ģ�����';
COMMENT ON COLUMN "SYS_MODULE_INFO"."CREATOR" IS '������';
COMMENT ON COLUMN "SYS_MODULE_INFO"."CREATE_TIME" IS '����ʱ��';
REM Primary key SYS_MODULE_INFO
ALTER TABLE "SYS_MODULE_INFO" ADD CONSTRAINT "SYS_MODULE_INFO_PK" PRIMARY KEY (SMI_ID) USING INDEX;
/

REM Create Table SYS_TABLE_INFO
REM Depend on table(s)
REM [SYS_MODULE_INFO]
CREATE TABLE "SYS_TABLE_INFO" (
	"STI_ID" NUMBER NOT NULL,
	"TABLE_NAME" VARCHAR2(80 CHAR) NOT NULL,
	"SMI_ID" NUMBER DEFAULT 0 NOT NULL,
	"TABLE_COMMENTS" VARCHAR2(400 CHAR) NULL,
	"REMARK" VARCHAR2(400 CHAR) NULL,
	"CREATOR" VARCHAR2(20) NOT NULL,
	"CREATE_TIME" DATE NOT NULL
);
COMMENT ON TABLE "SYS_TABLE_INFO" IS 'ϵͳ������Ϣ��';
REM Column comments SYS_TABLE_INFO
COMMENT ON COLUMN "SYS_TABLE_INFO"."STI_ID" IS '����';
COMMENT ON COLUMN "SYS_TABLE_INFO"."TABLE_NAME" IS '����';
COMMENT ON COLUMN "SYS_TABLE_INFO"."SMI_ID" IS 'ϵͳģ������';
COMMENT ON COLUMN "SYS_TABLE_INFO"."TABLE_COMMENTS" IS '��ע�ͣ����е���Ϣ�����Ը�����ע�ͣ�Ҳ�ɺ�̨¼�룩';
COMMENT ON COLUMN "SYS_TABLE_INFO"."REMARK" IS '��ע���Ա��������ݵļ�Ҫ���ܣ����߱����߼��ڵļ�����';
COMMENT ON COLUMN "SYS_TABLE_INFO"."CREATOR" IS '������';
COMMENT ON COLUMN "SYS_TABLE_INFO"."CREATE_TIME" IS '����ʱ��';
REM Primary key SYS_TABLE_INFO
ALTER TABLE "SYS_TABLE_INFO" ADD CONSTRAINT "SYS_TABLE_INFO_PK" PRIMARY KEY (STI_ID) USING INDEX;
REM Foreign keys SYS_TABLE_INFO
ALTER TABLE "SYS_TABLE_INFO" ADD CONSTRAINT "SYS_TABLE_INFO_FK1" FOREIGN KEY (SMI_ID) REFERENCES "SYS_MODULE_INFO" (SMI_ID) ON DELETE CASCADE;
/

REM Create Table TS_ORIGIN_HISTORY
CREATE TABLE "TS_ORIGIN_HISTORY" (
	"DATEPOINT" VARCHAR2(32 CHAR) NOT NULL,
	"RETRY_COUNT" NUMBER DEFAULT 0 NOT NULL,
	"RETCODE" VARCHAR2(160 CHAR) NULL,
	"RETMSG" VARCHAR2(160 CHAR) NULL,
	"DONE" NUMBER DEFAULT 0 NOT NULL
);
REM Column comments TS_ORIGIN_HISTORY
COMMENT ON COLUMN "TS_ORIGIN_HISTORY"."DATEPOINT" IS '����';
COMMENT ON COLUMN "TS_ORIGIN_HISTORY"."RETRY_COUNT" IS '���Դ���';
COMMENT ON COLUMN "TS_ORIGIN_HISTORY"."DONE" IS '�Ƿ�������';
REM Primary key TS_ORIGIN_HISTORY
ALTER TABLE "TS_ORIGIN_HISTORY" ADD CONSTRAINT "TS_ORIGIN_HISTORY_PK" PRIMARY KEY (DATEPOINT) USING INDEX;
/

REM Create Table ACC_CORP_INFO
REM Depend on table(s)
REM [ACC_USER_REG]
CREATE TABLE "ACC_CORP_INFO" (
	"AU_ID" NUMBER NOT NULL,
	"ORG_NAME" VARCHAR2(2000 CHAR) NULL,
	"BUSS_LIC" VARCHAR2(2000 CHAR) NULL,
	"TAX_LIC" VARCHAR2(2000 CHAR) NULL,
	"ORG_CODE_NO" VARCHAR2(2000 CHAR) NULL,
	"LAW_NAME" VARCHAR2(2000 CHAR) NULL,
	"LAW_ID_CARD" VARCHAR2(2000 CHAR) NULL,
	"ACC_USER_NAME" VARCHAR2(2000 CHAR) NULL,
	"ACCOUNT" VARCHAR2(2000 CHAR) NULL,
	"ACC_BANK" VARCHAR2(2000 CHAR) NULL,
	"CREATE_TIME" DATE NULL,
	"UPDATE_TIME" DATE NULL,
	"REAL_NAME" VARCHAR2(2000 CHAR) NULL,
	"POSITION" VARCHAR2(2000 CHAR) NULL,
	"ID_CARD" VARCHAR2(2000 CHAR) NULL,
	"COMPANY" VARCHAR2(2000 CHAR) NULL,
	"COMPANY_TYPE" VARCHAR2(2000 CHAR) NULL,
	"ADDRESS" VARCHAR2(2000 CHAR) NULL,
	"POSTAL_CODE" VARCHAR2(2000 CHAR) NULL,
	"HOME_PHONE" VARCHAR2(2000 CHAR) NULL,
	"QQ_NUMBER" VARCHAR2(2000 CHAR) NULL,
	"EMAIL" VARCHAR2(2000 CHAR) NULL
);
REM Column comments ACC_CORP_INFO
COMMENT ON COLUMN "ACC_CORP_INFO"."ORG_NAME" IS '��������';
COMMENT ON COLUMN "ACC_CORP_INFO"."BUSS_LIC" IS 'Ӫҵִ�պ�';
COMMENT ON COLUMN "ACC_CORP_INFO"."TAX_LIC" IS '˰��Ǽ�֤��';
COMMENT ON COLUMN "ACC_CORP_INFO"."ORG_CODE_NO" IS '��֯��������֤��';
COMMENT ON COLUMN "ACC_CORP_INFO"."LAW_NAME" IS '��������';
COMMENT ON COLUMN "ACC_CORP_INFO"."LAW_ID_CARD" IS '��������֤��';
COMMENT ON COLUMN "ACC_CORP_INFO"."ACC_USER_NAME" IS '�����˻�����';
COMMENT ON COLUMN "ACC_CORP_INFO"."ACCOUNT" IS '�����˺�';
COMMENT ON COLUMN "ACC_CORP_INFO"."ACC_BANK" IS '�����˻�������';
COMMENT ON COLUMN "ACC_CORP_INFO"."CREATE_TIME" IS '����ʱ��';
COMMENT ON COLUMN "ACC_CORP_INFO"."UPDATE_TIME" IS '����ʱ��';
COMMENT ON COLUMN "ACC_CORP_INFO"."REAL_NAME" IS '����';
COMMENT ON COLUMN "ACC_CORP_INFO"."POSITION" IS 'ְ��';
COMMENT ON COLUMN "ACC_CORP_INFO"."ID_CARD" IS '����֤';
COMMENT ON COLUMN "ACC_CORP_INFO"."COMPANY" IS '������λ';
COMMENT ON COLUMN "ACC_CORP_INFO"."COMPANY_TYPE" IS '��λ����';
COMMENT ON COLUMN "ACC_CORP_INFO"."ADDRESS" IS 'ͨѶ��ַ';
COMMENT ON COLUMN "ACC_CORP_INFO"."POSTAL_CODE" IS '��������';
COMMENT ON COLUMN "ACC_CORP_INFO"."HOME_PHONE" IS '��ͥ�绰';
COMMENT ON COLUMN "ACC_CORP_INFO"."QQ_NUMBER" IS 'qq����';
COMMENT ON COLUMN "ACC_CORP_INFO"."EMAIL" IS '����';
REM Primary key ACC_CORP_INFO
ALTER TABLE "ACC_CORP_INFO" ADD CONSTRAINT "ACC_CORP_INFO_PK" PRIMARY KEY (AU_ID) USING INDEX;
REM Foreign keys ACC_CORP_INFO
ALTER TABLE "ACC_CORP_INFO" ADD CONSTRAINT "ACC_CORP_INFO_DELETE_FK1" FOREIGN KEY (AU_ID) REFERENCES "ACC_USER_REG" (AU_ID) ON DELETE CASCADE;
/

REM Create Table ACC_JX_USER
REM Depend on table(s)
REM [ACC_USER_REG]
CREATE TABLE "ACC_JX_USER" (
	"AU_ID" NUMBER NOT NULL,
	"USER_ID" VARCHAR2(2000 CHAR) NULL,
	"RECARD" VARCHAR2(2000 CHAR) NULL,
	"NAME" VARCHAR2(1920 CHAR) NULL,
	"ID_CARD" VARCHAR2(576 CHAR) NULL,
	"MOBILE" VARCHAR2(384 CHAR) NULL,
	"DATEPOINT" DATE NULL,
	"ID_TYPE" VARCHAR2(80 CHAR) NULL,
	"AUTO_TENDER_AUTH" NUMBER NULL,
	"MAX_AUTO_TENDER_AMT" NUMBER NULL,
	"PWD_SET" NUMBER(1) DEFAULT 0 NULL,
	"IDENTITY" NUMBER NULL,
	"PAY_AUTH" NUMBER(1) DEFAULT 0 NULL,
	"REPAY_AUTH" NUMBER(1) DEFAULT 0 NULL
);
REM Column comments ACC_JX_USER
COMMENT ON COLUMN "ACC_JX_USER"."USER_ID" IS '���ŵ����˻�';
COMMENT ON COLUMN "ACC_JX_USER"."RECARD" IS '�󶨿���';
COMMENT ON COLUMN "ACC_JX_USER"."NAME" IS '�ֿ�������';
COMMENT ON COLUMN "ACC_JX_USER"."ID_CARD" IS '����֤��';
COMMENT ON COLUMN "ACC_JX_USER"."MOBILE" IS '�ֻ���';
COMMENT ON COLUMN "ACC_JX_USER"."ID_TYPE" IS '01(18λ����֤���룩��02��15λ����֤���룩��20����֯�������룩��25��������úţ�';
COMMENT ON COLUMN "ACC_JX_USER"."PWD_SET" IS '�Ƿ��������п�����.0��ʾδ����,1������';
COMMENT ON COLUMN "ACC_JX_USER"."IDENTITY" IS '1�������ɫ 2������ɫ 3��������ɫ';
COMMENT ON COLUMN "ACC_JX_USER"."PAY_AUTH" IS '�Ƿ����ýɷ���Ȩ��0��δ���ã� 1�� ������';
COMMENT ON COLUMN "ACC_JX_USER"."REPAY_AUTH" IS '�Ƿ����û�����Ȩ��0�� Ϊ���ã� 1�� ������';
REM Primary key ACC_JX_USER
ALTER TABLE "ACC_JX_USER" ADD CONSTRAINT "ACC_JX_USER_PK" PRIMARY KEY (AU_ID) USING INDEX;
REM Foreign keys ACC_JX_USER
ALTER TABLE "ACC_JX_USER" ADD CONSTRAINT "ACC_JX_USER_DELETE_FK1" FOREIGN KEY (AU_ID) REFERENCES "ACC_USER_REG" (AU_ID) ON DELETE CASCADE;
/

REM Create Table ACC_ORIGIN_RUNNING
REM Depend on table(s)
REM [ACC_USER_REG]
CREATE TABLE "ACC_ORIGIN_RUNNING" (
	"BANK" NUMBER(4) NULL,
	"AU_ID" NUMBER NULL,
	"CARDNBR" VARCHAR2(76 CHAR) NULL,
	"AMOUNT" NUMBER(17,2) NULL,
	"CRFLAG" VARCHAR2(4 CHAR) NULL,
	"DATEPOINT" DATE NULL,
	"TRANNO" NUMBER(20) NOT NULL,
	"ORI_TRANNO" NUMBER(20) NULL,
	"TRANSTYPE" NUMBER(4) NULL,
	"DESLINE" VARCHAR2(168 CHAR) NULL,
	"CURR_BAL" NUMBER(17,2) NULL,
	"FORCARDNBR" VARCHAR2(76 CHAR) NULL,
	"FOR_AU_ID" NUMBER NULL,
	"REVIND" NUMBER NULL
);
REM Column comments ACC_ORIGIN_RUNNING
COMMENT ON COLUMN "ACC_ORIGIN_RUNNING"."BANK" IS '���к�';
COMMENT ON COLUMN "ACC_ORIGIN_RUNNING"."AU_ID" IS '�û�au_id';
COMMENT ON COLUMN "ACC_ORIGIN_RUNNING"."CARDNBR" IS '�����˺�';
COMMENT ON COLUMN "ACC_ORIGIN_RUNNING"."AMOUNT" IS '���׽��';
COMMENT ON COLUMN "ACC_ORIGIN_RUNNING"."CRFLAG" IS '���׽�����';
COMMENT ON COLUMN "ACC_ORIGIN_RUNNING"."DATEPOINT" IS '��������';
COMMENT ON COLUMN "ACC_ORIGIN_RUNNING"."TRANNO" IS '������ˮ��';
COMMENT ON COLUMN "ACC_ORIGIN_RUNNING"."ORI_TRANNO" IS '�ý���Ϊ�����ѽ���ʱ��������ˮ��Ϊԭ���׵���ˮ��';
COMMENT ON COLUMN "ACC_ORIGIN_RUNNING"."TRANSTYPE" IS '��������';
COMMENT ON COLUMN "ACC_ORIGIN_RUNNING"."DESLINE" IS '��������';
COMMENT ON COLUMN "ACC_ORIGIN_RUNNING"."CURR_BAL" IS '���׺����';
COMMENT ON COLUMN "ACC_ORIGIN_RUNNING"."FORCARDNBR" IS '���ֽ����ʺ�';
COMMENT ON COLUMN "ACC_ORIGIN_RUNNING"."FOR_AU_ID" IS '�����û�au_iud';
COMMENT ON COLUMN "ACC_ORIGIN_RUNNING"."REVIND" IS '������������־(2-�ѳ���/����
�ջ�1-��������)';
REM Primary key ACC_ORIGIN_RUNNING
ALTER TABLE "ACC_ORIGIN_RUNNING" ADD CONSTRAINT "ACC_ORIGIN_RUNNING_PK" PRIMARY KEY (TRANNO) USING INDEX;
REM Foreign keys ACC_ORIGIN_RUNNING
ALTER TABLE "ACC_ORIGIN_RUNNING" ADD CONSTRAINT "ACC_ORIGIN_RUNNING_DELETE_FK1" FOREIGN KEY (AU_ID) REFERENCES "ACC_USER_REG" (AU_ID) ON DELETE CASCADE;
/

REM Drop sequences
CALL DROP_SEQUENCE_IF_EXISTS('TS_ID');
CALL DROP_SEQUENCE_IF_EXISTS('MSG_SMS_YM_ID');
CALL DROP_SEQUENCE_IF_EXISTS('MSG_SMS_MW_ID');
CALL DROP_SEQUENCE_IF_EXISTS('MSG_ID');
CALL DROP_SEQUENCE_IF_EXISTS('LOG_ID');
CALL DROP_SEQUENCE_IF_EXISTS('AU_ID');
CALL DROP_SEQUENCE_IF_EXISTS('AM_ID');
/

REM Create sequence AM_ID
CREATE SEQUENCE "AM_ID" MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 ORDER;
/

REM Create sequence AU_ID
CREATE SEQUENCE "AU_ID" MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 ORDER;
/

REM Create sequence LOG_ID
CREATE SEQUENCE "LOG_ID" MINVALUE 1 MAXVALUE 99999999999999999999999 INCREMENT BY 1 ORDER;
/

REM Create sequence MSG_ID
CREATE SEQUENCE "MSG_ID" MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 ORDER;
/

REM Create sequence MSG_SMS_MW_ID
CREATE SEQUENCE "MSG_SMS_MW_ID" MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 ORDER;
/

REM Create sequence MSG_SMS_YM_ID
CREATE SEQUENCE "MSG_SMS_YM_ID" MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 ORDER;
/

REM Create sequence TS_ID
CREATE SEQUENCE "TS_ID" MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 ORDER;
/


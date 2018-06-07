--------------------------------------------------------
--  文件已创建 - 星期三-六月-06-2018   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Table MY_ROLE
--------------------------------------------------------

  CREATE TABLE "P2P"."MY_ROLE" 
   (	"R_NAME" VARCHAR2(800 CHAR), 
	"R_TITLE" VARCHAR2(1000 CHAR), 
	"R_DESCRIPTION" VARCHAR2(1000 CHAR), 
	"R_ENABLED" NUMBER DEFAULT 1
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "P2P_TS" ;
--------------------------------------------------------
--  DDL for Table MY_ROLE_PERM
--------------------------------------------------------

  CREATE TABLE "P2P"."MY_ROLE_PERM" 
   (	"R_NAME" VARCHAR2(800 CHAR), 
	"PERM_ID" NUMBER
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "P2P_TS" ;
--------------------------------------------------------
--  DDL for Table MY_USER
--------------------------------------------------------

  CREATE TABLE "P2P"."MY_USER" 
   (	"U_NAME" VARCHAR2(800 CHAR), 
	"U_PWD" VARCHAR2(800 CHAR), 
	"U_ENABLED" NUMBER DEFAULT 1, 
	"IS_LOCKED" NUMBER DEFAULT 0
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "P2P_TS" ;

   COMMENT ON COLUMN "P2P"."MY_USER"."IS_LOCKED" IS '0未锁，1已锁';
   COMMENT ON TABLE "P2P"."MY_USER"  IS '后台用户表';
--------------------------------------------------------
--  DDL for Table MY_USER_PROP
--------------------------------------------------------

  CREATE TABLE "P2P"."MY_USER_PROP" 
   (	"U_NAME" VARCHAR2(800 CHAR), 
	"P_KEY" VARCHAR2(800 CHAR), 
	"P_VALUE" VARCHAR2(4000 CHAR), 
	"P_TYPE" NUMBER DEFAULT 0
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "P2P_TS" ;

   COMMENT ON TABLE "P2P"."MY_USER_PROP"  IS '后台用户属性';
--------------------------------------------------------
--  DDL for Table MY_USER_ROLE
--------------------------------------------------------

  CREATE TABLE "P2P"."MY_USER_ROLE" 
   (	"U_NAME" VARCHAR2(800 CHAR), 
	"R_NAME" VARCHAR2(800 CHAR)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "P2P_TS" ;

   COMMENT ON TABLE "P2P"."MY_USER_ROLE"  IS '后台账户角色';
REM INSERTING into P2P.MY_ROLE
SET DEFINE OFF;
Insert into P2P.MY_ROLE (R_NAME,R_TITLE,R_DESCRIPTION,R_ENABLED) values ('项目经理','测试管理维护','发起项目',1);
Insert into P2P.MY_ROLE (R_NAME,R_TITLE,R_DESCRIPTION,R_ENABLED) values ('administrators','administrators','All administrators group',1);
Insert into P2P.MY_ROLE (R_NAME,R_TITLE,R_DESCRIPTION,R_ENABLED) values ('test1','测试1','测试',1);
Insert into P2P.MY_ROLE (R_NAME,R_TITLE,R_DESCRIPTION,R_ENABLED) values ('test2','测试账户','说明无',1);
Insert into P2P.MY_ROLE (R_NAME,R_TITLE,R_DESCRIPTION,R_ENABLED) values ('lhshi','测试账户lhshi','测试',1);
Insert into P2P.MY_ROLE (R_NAME,R_TITLE,R_DESCRIPTION,R_ENABLED) values ('超级测试角色','测试啊','无',1);
Insert into P2P.MY_ROLE (R_NAME,R_TITLE,R_DESCRIPTION,R_ENABLED) values ('lyang','测试lyang','无',1);
REM INSERTING into P2P.MY_ROLE_PERM
SET DEFINE OFF;
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',60117);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',60118);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',60119);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',60120);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',60121);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',60130);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',60131);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',60133);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',60134);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',60135);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',60136);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',60137);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',60150);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',60151);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',60160);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',60163);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',60171);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',80011);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',80012);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',80013);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',80014);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',80015);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',80021);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',80022);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',80023);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',80024);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',80025);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',80031);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',80032);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',80033);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',80034);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',80035);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',80041);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',80042);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',80043);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',80044);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',80045);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',80051);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',80052);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',80053);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',80054);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',80055);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',80061);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',80062);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',80063);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',80064);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',80065);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',80071);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',80072);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',80073);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',80074);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',80075);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',80081);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',80082);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',80083);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',80086);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',80087);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',80088);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',80091);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',80092);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',80093);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',80096);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',80097);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',80098);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',80101);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',80102);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',80106);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',80107);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',80108);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',80901);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',80902);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',80903);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',80904);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',10000);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',10001);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',10002);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',10003);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',10004);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',10005);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',10006);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',10007);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',10008);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',10009);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',10010);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',10011);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',10012);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',10013);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',10104);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',20401);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',20402);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',20403);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',20404);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',20405);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',20406);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',20407);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',20430);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',20432);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',20433);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',20434);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',20435);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',20436);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',20440);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',20441);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',20442);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',20443);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',20460);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',20461);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',20462);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',20463);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',20465);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',20466);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',20467);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',20468);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',20469);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',20470);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',20471);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',20472);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',20473);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',20474);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',20475);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',20477);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',20479);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',20480);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',20481);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',20482);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',20483);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',20484);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',20490);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',20491);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',20492);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',20493);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',20494);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',20501);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',20502);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',20503);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',20505);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',20506);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',20507);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',20610);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',20611);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',20612);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',20613);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',20614);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',20615);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',20630);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',20631);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',20632);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',20633);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',20640);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',20650);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',20651);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',20652);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',20701);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',20702);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',20703);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',20704);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',20931);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('项目经理',60097);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('项目经理',60098);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('项目经理',60099);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('项目经理',60100);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('项目经理',60101);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('项目经理',60102);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('项目经理',60110);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('项目经理',60111);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('项目经理',60112);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('项目经理',60113);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('项目经理',60114);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('项目经理',60115);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('项目经理',60116);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('项目经理',60117);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('项目经理',60118);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('项目经理',60119);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('项目经理',60120);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('项目经理',60121);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('项目经理',60130);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('项目经理',60131);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('项目经理',60133);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('项目经理',60134);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('项目经理',60135);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('项目经理',60136);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('项目经理',60137);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('项目经理',60150);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('项目经理',60151);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('项目经理',60171);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('项目经理',80086);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('项目经理',80087);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('项目经理',80088);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('项目经理',80091);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('项目经理',80092);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('项目经理',80093);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('项目经理',80096);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('项目经理',80097);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('项目经理',80098);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('项目经理',80101);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('项目经理',80102);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('项目经理',80106);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('项目经理',80107);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('项目经理',80108);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',80011);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',80012);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',80013);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',80014);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',80015);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',80021);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',80022);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',80023);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',80024);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',80025);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',80031);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',80032);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',80033);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',80034);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',80035);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',80041);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',80042);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',80043);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',80044);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',80045);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',80051);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',80052);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',80053);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',80054);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',80055);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',80061);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',80062);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',80063);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',80064);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',80065);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',80071);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',80072);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',80073);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',80074);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',80075);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',80081);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',80082);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',80083);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',80086);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',80087);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',80088);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',80091);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',80092);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',80093);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',80096);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',80097);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',80098);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',80101);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',80102);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',80106);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',80107);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',80108);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',80901);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',80902);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',80903);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',80904);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('项目经理',20483);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('项目经理',60001);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('项目经理',60002);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('项目经理',60003);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('项目经理',60010);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('项目经理',60011);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('项目经理',60012);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('项目经理',60013);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('项目经理',60014);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('项目经理',60015);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('项目经理',60016);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('项目经理',60017);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('项目经理',60018);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('项目经理',60019);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('项目经理',60020);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('项目经理',60021);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('项目经理',60060);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('项目经理',60061);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('项目经理',60062);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('项目经理',60063);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('项目经理',60064);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('项目经理',60065);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('项目经理',60066);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('项目经理',60067);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('项目经理',60069);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('项目经理',60080);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('项目经理',60087);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('项目经理',60088);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('项目经理',60089);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('项目经理',60090);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('项目经理',60091);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('项目经理',60092);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('项目经理',60093);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('项目经理',60094);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('项目经理',60095);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',10000);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',10001);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',10002);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',10003);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',10004);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',10005);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',10006);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',10007);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',10008);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',10009);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',10010);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',10011);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',10012);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',10013);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',10104);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',20401);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',20402);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',20403);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',20404);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',20405);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',20406);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',20407);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',20430);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',20432);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',20433);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',20434);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',20435);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',20436);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',20440);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',20441);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',20442);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',20443);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',20460);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',20461);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',20462);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',20463);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',20465);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',20466);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',20467);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',20468);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',20469);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',20470);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',20471);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',20472);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',20473);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',20474);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',20475);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',20477);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',20479);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',20480);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',20481);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',20482);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',20483);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',20484);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',20490);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',20491);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',20492);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',20493);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',20494);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',20501);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',20502);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',20503);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',20505);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',20506);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',20507);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',20610);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',20611);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',20612);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',20613);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',20614);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',20615);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',20630);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',20631);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',20632);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',20633);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',20640);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',20650);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',20651);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',20652);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',20701);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',20702);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',20703);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',20704);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',20931);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',60001);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',60002);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',60003);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',60010);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',60011);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',60012);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',60013);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',60014);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',60015);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',60016);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',60017);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',60018);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',60019);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',60020);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',60021);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',60060);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',60061);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',60062);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',60063);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',60064);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',60065);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',60066);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',60067);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',60069);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',60080);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',60087);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',60088);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',60089);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',60090);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',60091);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',60092);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',60093);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',60094);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',60095);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',60097);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',60098);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',60099);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',60100);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',60101);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',60102);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',60110);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',60111);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',60112);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',60113);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',60114);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',60115);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('administrators',60116);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',10000);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',10001);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',10002);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',10003);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',10004);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',10005);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',10006);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',10007);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',10008);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',10009);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',10010);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',10011);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',10012);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',10013);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',10104);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',20401);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',20402);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',20403);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',20404);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',20405);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',20406);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',20407);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',20430);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',20432);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',20433);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',20434);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',20435);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',20436);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',20440);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',20441);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',20442);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',20443);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',20460);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',20461);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',20462);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',20463);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',20465);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',20466);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',20467);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',20468);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',20469);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',20470);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',20471);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',20472);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',20473);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',20474);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',20475);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',20477);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',20479);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',20480);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',20481);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',20482);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',20483);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',20484);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',20490);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',20491);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',20492);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',20493);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',20494);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',20501);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',20502);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',20503);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',20505);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',20506);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',20507);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',20610);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',20611);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',20612);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',20613);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',20614);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',20615);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',20630);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',20631);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',20632);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',20633);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',20640);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',20650);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',20651);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',20652);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',20701);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',20702);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',20703);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',20704);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',20931);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',60001);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',60002);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',60003);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',60010);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',60011);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',60012);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',60013);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',60014);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',60015);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',60016);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',60017);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',60018);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',60019);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',60020);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',60021);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',60060);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',60061);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',60062);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',60063);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',60064);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',60065);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',60066);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',60067);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',60069);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',60080);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',60087);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',60088);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',60089);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',60090);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',60091);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',60092);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',60093);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',60094);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',60095);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',60097);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',60098);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',60099);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',60100);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',60101);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',60102);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',60110);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',60111);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',60112);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',60113);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',60115);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',60120);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',60121);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',60130);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',60131);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',60133);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',60134);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',60135);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',60136);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',60137);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',60150);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',60151);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',60160);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',60163);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('超级测试角色',60171);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',60001);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',60002);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',60003);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',60010);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',60011);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',60012);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',60013);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',60014);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',60015);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',60016);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',60017);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',60018);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',60019);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',60020);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',60021);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',60060);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',60061);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',60062);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',60063);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',60064);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',60065);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',60066);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',60067);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',60069);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',60160);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',60163);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',80011);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',80012);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',80013);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',80014);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',80015);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',80021);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',80022);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',80023);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',80024);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',80025);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',80031);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',80032);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',80033);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',80034);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',80035);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',80041);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',80042);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',80043);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',80044);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',80045);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',80051);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',80052);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',80053);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',80054);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',80055);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',80061);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',80062);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',80063);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',80064);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',80065);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',80071);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',80072);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',80073);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',80074);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',80075);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',80081);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',80082);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',80083);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',80086);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',80087);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',80088);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',80091);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',80092);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',80093);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',80096);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',80097);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',80098);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',80101);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',80102);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',80106);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',80107);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',80108);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',80901);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',80902);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',80903);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lhshi',80904);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',10000);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',10001);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',10002);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',10003);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',10004);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',10005);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',10006);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',10007);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',10008);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',10009);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',10010);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',10011);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',10012);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',10013);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',10104);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',20401);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',20402);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',20403);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',20404);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',20405);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',20406);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',20407);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',20430);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',20432);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',20433);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',20434);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',20435);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',20436);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',20440);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',20441);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',20442);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',20443);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',20460);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',20461);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',20462);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',20463);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',20465);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',20466);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',20467);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',20468);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',20469);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',20470);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',20471);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',20472);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',20473);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',20474);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',20475);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',20477);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',20479);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',20480);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',20481);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',20482);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',20483);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',20484);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',20490);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',20491);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',20492);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',20493);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',20494);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',20501);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',20502);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',20503);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',20505);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',20506);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',20507);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',20610);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',20611);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',20612);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',20613);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',20614);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',20615);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',20630);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',20631);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',20632);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',20633);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',20640);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',20650);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',20651);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',20652);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',20701);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',20702);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',20703);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',20704);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',20931);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',60001);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',60002);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',60003);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',60010);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',60011);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',60012);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',60013);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',60014);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',60015);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',60016);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',60017);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',60018);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',60019);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',60020);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',60021);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',60060);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',60061);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',60062);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',60063);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',60064);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',60065);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',60066);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',60067);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',60069);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',60080);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',60087);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',60088);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',60089);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',60090);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',60091);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',60092);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',60093);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',60094);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',60095);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',60097);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',60098);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',60099);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',60100);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',60101);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',60102);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',60110);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',60111);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',60112);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',60113);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',60115);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',60120);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',60121);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',60130);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',60131);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',60133);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',60134);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',60135);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',60136);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',60137);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',60150);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',60151);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',60160);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',60163);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',60171);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',80011);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',80012);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',80013);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',80014);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',80015);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',80021);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',80022);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',80023);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',80024);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',80025);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',80031);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',80032);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',80033);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',80034);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',80035);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',80041);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',80042);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',80043);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',80044);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',80045);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',80051);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',80052);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',80053);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',80054);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',80055);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',80061);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',80062);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',80063);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',80064);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',80065);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',80071);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',80072);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',80073);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',80074);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',80075);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',80081);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',80082);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',80083);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',80086);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',80087);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',80088);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',80091);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',80092);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',80093);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',80096);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',80097);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',80098);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',80101);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',80102);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',80106);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',80107);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',80108);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',80901);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',80902);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',80903);
Insert into P2P.MY_ROLE_PERM (R_NAME,PERM_ID) values ('lyang',80904);
REM INSERTING into P2P.MY_USER
SET DEFINE OFF;
Insert into P2P.MY_USER (U_NAME,U_PWD,U_ENABLED,IS_LOCKED) values ('admin','FDBz3p8RKcZXM4nDCr5VmXYE9Oo=',1,0);
Insert into P2P.MY_USER (U_NAME,U_PWD,U_ENABLED,IS_LOCKED) values ('1','FDBz3p8RKcZXM4nDCr5VmXYE9Oo=',1,0);
Insert into P2P.MY_USER (U_NAME,U_PWD,U_ENABLED,IS_LOCKED) values ('lhshi','FDBz3p8RKcZXM4nDCr5VmXYE9Oo=',1,0);
Insert into P2P.MY_USER (U_NAME,U_PWD,U_ENABLED,IS_LOCKED) values ('slh','SxAfQTclvXHiR7gIlpfNjJbnDd0=',1,0);
Insert into P2P.MY_USER (U_NAME,U_PWD,U_ENABLED,IS_LOCKED) values ('ly','FDBz3p8RKcZXM4nDCr5VmXYE9Oo=',1,0);
Insert into P2P.MY_USER (U_NAME,U_PWD,U_ENABLED,IS_LOCKED) values ('测试','FDBz3p8RKcZXM4nDCr5VmXYE9Oo=',1,0);
Insert into P2P.MY_USER (U_NAME,U_PWD,U_ENABLED,IS_LOCKED) values ('xxx','FDBz3p8RKcZXM4nDCr5VmXYE9Oo=',1,0);
Insert into P2P.MY_USER (U_NAME,U_PWD,U_ENABLED,IS_LOCKED) values ('yuanl','FDBz3p8RKcZXM4nDCr5VmXYE9Oo=',1,0);
Insert into P2P.MY_USER (U_NAME,U_PWD,U_ENABLED,IS_LOCKED) values ('lyh','+SaJ2RmEA/Sz60rbkevyTBA3S7Q=',1,0);
Insert into P2P.MY_USER (U_NAME,U_PWD,U_ENABLED,IS_LOCKED) values ('lyang','R1L/070LGCsBXifBmnNtuqIWr7M=',1,0);
REM INSERTING into P2P.MY_USER_PROP
SET DEFINE OFF;
Insert into P2P.MY_USER_PROP (U_NAME,P_KEY,P_VALUE,P_TYPE) values ('lhshi','mobile','18217699851',0);
Insert into P2P.MY_USER_PROP (U_NAME,P_KEY,P_VALUE,P_TYPE) values ('lhshi','email',null,0);
Insert into P2P.MY_USER_PROP (U_NAME,P_KEY,P_VALUE,P_TYPE) values ('lhshi','r_code',null,0);
Insert into P2P.MY_USER_PROP (U_NAME,P_KEY,P_VALUE,P_TYPE) values ('测试','email',null,0);
Insert into P2P.MY_USER_PROP (U_NAME,P_KEY,P_VALUE,P_TYPE) values ('测试','create_date','1527487773247',1);
Insert into P2P.MY_USER_PROP (U_NAME,P_KEY,P_VALUE,P_TYPE) values ('测试','last_modified_date','1527487773247',1);
Insert into P2P.MY_USER_PROP (U_NAME,P_KEY,P_VALUE,P_TYPE) values ('slh','mobile','18217699851',0);
Insert into P2P.MY_USER_PROP (U_NAME,P_KEY,P_VALUE,P_TYPE) values ('slh','email',null,0);
Insert into P2P.MY_USER_PROP (U_NAME,P_KEY,P_VALUE,P_TYPE) values ('slh','r_code',null,0);
Insert into P2P.MY_USER_PROP (U_NAME,P_KEY,P_VALUE,P_TYPE) values ('lhshi','last_modified_date','1527476571790',1);
Insert into P2P.MY_USER_PROP (U_NAME,P_KEY,P_VALUE,P_TYPE) values ('slh','last_modified_date','1527477846998',1);
Insert into P2P.MY_USER_PROP (U_NAME,P_KEY,P_VALUE,P_TYPE) values ('ly','mobile','15121082067',0);
Insert into P2P.MY_USER_PROP (U_NAME,P_KEY,P_VALUE,P_TYPE) values ('ly','email',null,0);
Insert into P2P.MY_USER_PROP (U_NAME,P_KEY,P_VALUE,P_TYPE) values ('ly','create_date','1527477927288',1);
Insert into P2P.MY_USER_PROP (U_NAME,P_KEY,P_VALUE,P_TYPE) values ('ly','last_modified_date','1527477927288',1);
Insert into P2P.MY_USER_PROP (U_NAME,P_KEY,P_VALUE,P_TYPE) values ('测试','mobile','12436578699',0);
Insert into P2P.MY_USER_PROP (U_NAME,P_KEY,P_VALUE,P_TYPE) values ('xxx','mobile','234',0);
Insert into P2P.MY_USER_PROP (U_NAME,P_KEY,P_VALUE,P_TYPE) values ('xxx','email','2134',0);
Insert into P2P.MY_USER_PROP (U_NAME,P_KEY,P_VALUE,P_TYPE) values ('xxx','create_date','1526956076929',1);
Insert into P2P.MY_USER_PROP (U_NAME,P_KEY,P_VALUE,P_TYPE) values ('xxx','last_modified_date','1526956076929',1);
Insert into P2P.MY_USER_PROP (U_NAME,P_KEY,P_VALUE,P_TYPE) values ('yuanl','mobile','15921692579',0);
Insert into P2P.MY_USER_PROP (U_NAME,P_KEY,P_VALUE,P_TYPE) values ('yuanl','email',null,0);
Insert into P2P.MY_USER_PROP (U_NAME,P_KEY,P_VALUE,P_TYPE) values ('yuanl','r_code',null,0);
Insert into P2P.MY_USER_PROP (U_NAME,P_KEY,P_VALUE,P_TYPE) values ('yuanl','last_modified_date','1526970788799',1);
Insert into P2P.MY_USER_PROP (U_NAME,P_KEY,P_VALUE,P_TYPE) values ('lyang','mobile','15121082067',0);
Insert into P2P.MY_USER_PROP (U_NAME,P_KEY,P_VALUE,P_TYPE) values ('lyang','email',null,0);
Insert into P2P.MY_USER_PROP (U_NAME,P_KEY,P_VALUE,P_TYPE) values ('lyang','r_code',null,0);
Insert into P2P.MY_USER_PROP (U_NAME,P_KEY,P_VALUE,P_TYPE) values ('lyh','mobile','18829040632',0);
Insert into P2P.MY_USER_PROP (U_NAME,P_KEY,P_VALUE,P_TYPE) values ('lyh','email','2637231102@qq.com',0);
Insert into P2P.MY_USER_PROP (U_NAME,P_KEY,P_VALUE,P_TYPE) values ('lyh','r_code',null,0);
Insert into P2P.MY_USER_PROP (U_NAME,P_KEY,P_VALUE,P_TYPE) values ('lyang','last_modified_date','1527484634610',1);
Insert into P2P.MY_USER_PROP (U_NAME,P_KEY,P_VALUE,P_TYPE) values ('lyh','create_date','1526363923649',1);
Insert into P2P.MY_USER_PROP (U_NAME,P_KEY,P_VALUE,P_TYPE) values ('admin','mobile','3333333',0);
Insert into P2P.MY_USER_PROP (U_NAME,P_KEY,P_VALUE,P_TYPE) values ('admin','email',null,0);
Insert into P2P.MY_USER_PROP (U_NAME,P_KEY,P_VALUE,P_TYPE) values ('admin','r_code',null,0);
Insert into P2P.MY_USER_PROP (U_NAME,P_KEY,P_VALUE,P_TYPE) values ('admin','last_modified_date','1526880192696',1);
REM INSERTING into P2P.MY_USER_ROLE
SET DEFINE OFF;
Insert into P2P.MY_USER_ROLE (U_NAME,R_NAME) values ('1','administrators');
Insert into P2P.MY_USER_ROLE (U_NAME,R_NAME) values ('admin','administrators');
Insert into P2P.MY_USER_ROLE (U_NAME,R_NAME) values ('ly','administrators');
Insert into P2P.MY_USER_ROLE (U_NAME,R_NAME) values ('lyang','lyang');
Insert into P2P.MY_USER_ROLE (U_NAME,R_NAME) values ('lyh','administrators');
Insert into P2P.MY_USER_ROLE (U_NAME,R_NAME) values ('lyh','超级测试角色');
Insert into P2P.MY_USER_ROLE (U_NAME,R_NAME) values ('slh','administrators');
Insert into P2P.MY_USER_ROLE (U_NAME,R_NAME) values ('yuanl','administrators');
Insert into P2P.MY_USER_ROLE (U_NAME,R_NAME) values ('测试','administrators');
--------------------------------------------------------
--  DDL for Index MY_ROLE_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "P2P"."MY_ROLE_PK" ON "P2P"."MY_ROLE" ("R_NAME") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "P2P_TS" ;
--------------------------------------------------------
--  DDL for Index MY_ROLE_PERM_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "P2P"."MY_ROLE_PERM_PK" ON "P2P"."MY_ROLE_PERM" ("R_NAME", "PERM_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "P2P_TS" ;
--------------------------------------------------------
--  DDL for Index MY_USER_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "P2P"."MY_USER_PK" ON "P2P"."MY_USER" ("U_NAME") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "P2P_TS" ;
--------------------------------------------------------
--  DDL for Index CCC
--------------------------------------------------------

  CREATE INDEX "P2P"."CCC" ON "P2P"."MY_USER_PROP" ("P_KEY") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "P2P_TS" ;
--------------------------------------------------------
--  DDL for Index MY_USER_PROP_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "P2P"."MY_USER_PROP_PK" ON "P2P"."MY_USER_PROP" ("U_NAME", "P_KEY") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "P2P_TS" ;
--------------------------------------------------------
--  DDL for Index MY_USER_ROLE_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "P2P"."MY_USER_ROLE_PK" ON "P2P"."MY_USER_ROLE" ("U_NAME", "R_NAME") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "P2P_TS" ;
--------------------------------------------------------
--  Constraints for Table MY_ROLE
--------------------------------------------------------

  ALTER TABLE "P2P"."MY_ROLE" ADD CONSTRAINT "MY_ROLE_PK" PRIMARY KEY ("R_NAME")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "P2P_TS"  ENABLE;
  ALTER TABLE "P2P"."MY_ROLE" MODIFY ("R_NAME" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table MY_ROLE_PERM
--------------------------------------------------------

  ALTER TABLE "P2P"."MY_ROLE_PERM" ADD CONSTRAINT "MY_ROLE_PERM_PK" PRIMARY KEY ("R_NAME", "PERM_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "P2P_TS"  ENABLE;
  ALTER TABLE "P2P"."MY_ROLE_PERM" MODIFY ("PERM_ID" NOT NULL ENABLE);
  ALTER TABLE "P2P"."MY_ROLE_PERM" MODIFY ("R_NAME" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table MY_USER
--------------------------------------------------------

  ALTER TABLE "P2P"."MY_USER" ADD CONSTRAINT "MY_USER_PK" PRIMARY KEY ("U_NAME")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "P2P_TS"  ENABLE;
  ALTER TABLE "P2P"."MY_USER" MODIFY ("U_NAME" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table MY_USER_PROP
--------------------------------------------------------

  ALTER TABLE "P2P"."MY_USER_PROP" ADD CONSTRAINT "AAA" CHECK (P_TYPE <> 100) ENABLE;
  ALTER TABLE "P2P"."MY_USER_PROP" MODIFY ("P_KEY" NOT NULL ENABLE);
  ALTER TABLE "P2P"."MY_USER_PROP" MODIFY ("U_NAME" NOT NULL ENABLE);
  ALTER TABLE "P2P"."MY_USER_PROP" ADD CONSTRAINT "MY_USER_PROP_PK" PRIMARY KEY ("U_NAME", "P_KEY")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "P2P_TS"  ENABLE;
--------------------------------------------------------
--  Constraints for Table MY_USER_ROLE
--------------------------------------------------------

  ALTER TABLE "P2P"."MY_USER_ROLE" MODIFY ("R_NAME" NOT NULL ENABLE);
  ALTER TABLE "P2P"."MY_USER_ROLE" MODIFY ("U_NAME" NOT NULL ENABLE);
  ALTER TABLE "P2P"."MY_USER_ROLE" ADD CONSTRAINT "MY_USER_ROLE_PK" PRIMARY KEY ("U_NAME", "R_NAME")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "P2P_TS"  ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table MY_ROLE_PERM
--------------------------------------------------------

  ALTER TABLE "P2P"."MY_ROLE_PERM" ADD CONSTRAINT "MY_ROLE_PERM_MY_ROLE_FK1" FOREIGN KEY ("R_NAME")
	  REFERENCES "P2P"."MY_ROLE" ("R_NAME") ON DELETE CASCADE ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table MY_USER_PROP
--------------------------------------------------------

  ALTER TABLE "P2P"."MY_USER_PROP" ADD CONSTRAINT "MY_USER_PROP_MY_USER_FK1" FOREIGN KEY ("U_NAME")
	  REFERENCES "P2P"."MY_USER" ("U_NAME") ON DELETE CASCADE ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table MY_USER_ROLE
--------------------------------------------------------

  ALTER TABLE "P2P"."MY_USER_ROLE" ADD CONSTRAINT "MY_USER_ROLE_MY_ROLE_FK1" FOREIGN KEY ("R_NAME")
	  REFERENCES "P2P"."MY_ROLE" ("R_NAME") ON DELETE CASCADE ENABLE;
  ALTER TABLE "P2P"."MY_USER_ROLE" ADD CONSTRAINT "MY_USER_ROLE_MY_USER_FK1" FOREIGN KEY ("U_NAME")
	  REFERENCES "P2P"."MY_USER" ("U_NAME") ON DELETE CASCADE ENABLE;

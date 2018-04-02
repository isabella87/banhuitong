package com.bht.banhuitong.dbservice.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.bht.banhuitong.common.DateUtils;
/**
 * 数据库模型相关：包括创建表，等原子性操作
 * @author Administrator
 *
 */
public class DatabaseModelServiceImpl extends BaseDbService{
	private final static Logger logger = Logger.getLogger(DatabaseModelServiceImpl.class);

	/**
	 * 获取当前用户下所有表信息（表名，表类型，表注释，表所属空间名）
	 */
	private final static String allCurUserTablesInfoSql = " SELECT UT.TABLE_NAME,UT.TABLESPACE_NAME,UT.LAST_ANALYZED,UTC.COMMENTS,UTC.TABLE_TYPE FROM USER_TABLES UT JOIN USER_TAB_COMMENTS UTC ON UTC.TABLE_NAME = UT.TABLE_NAME ";
	/**
	 * 获取当前数据库系统下所有用户表信息(包含系统表)
	 */
	private final static String allDbaUserTableNameSql = " SELECT OWNER,TABLE_NAME,TABLESPACE_NAME,LAST_ANALYZED FROM DBA_TABLES ";
	/**
	 * 获取当前数据库系统下所有用户表信息(具体信息为：表所属用户名，表名，所属表空间名，最近分析的日期)
	 */
	private final static String allTableNameSql = " SELECT OWNER,TABLE_NAME,TABLESPACE_NAME,LAST_ANALYZED FROM ALL_TABLES ";
	
	/**
	 * 获取某表的字段的详细信息（字段名、字段类型、字段是否为空、字段长度、字段精度，字段描述等）
	 */
	private final static String tableColInfoSql = " SELECT DISTINCT(B.COLUMN_NAME) COLUMN_NAME ,\r\n" + 
			"    B.DATA_TYPE DATA_TYPE,   \r\n" + 
			"    B.DATA_LENGTH,         \r\n" + 
			"    B.DATA_PRECISION,\r\n" + 
			"    B.DATA_SCALE,\r\n" + 
			"    B.NULLABLE,\r\n" + 
			"    B.CHAR_LENGTH,\r\n" + 
			"    DBMS_LOB.SUBSTR(D.PRIMARY_KEY) PRIMARY_KEY,\r\n" + 
			"    A.COMMENTS COMMENTS      \r\n" + 
			" FROM USER_COL_COMMENTS A\r\n" + 
			"  JOIN USER_TAB_COLUMNS B ON A.COLUMN_NAME = B.COLUMN_NAME \r\n" + 
			"   JOIN (SELECT WM_CONCAT(COLUMN_NAME) PRIMARY_KEY,TABLE_NAME  FROM USER_CONS_COLUMNS WHERE POSITION > 0 GROUP BY TABLE_NAME) D ON B.TABLE_NAME = D.TABLE_NAME\r\n" +
			" WHERE A.TABLE_NAME = B.TABLE_NAME AND\r\n" + 
			"    A.TABLE_NAME = '";
	
	
	public Boolean createTable(String tableName ,Map<String, String> tableFieldsMap) {
		String sql = "";
		String primarykey = "";
		StringBuffer sb = new StringBuffer();
		sb.append("CREATE TABLE ").append(tableName);
		
		if(!tableFieldsMap.isEmpty()) {
			sb.append(" ( ");
			for(String key:tableFieldsMap.keySet()) {
				if(key.equals("PRIMARY_KEY")&&tableFieldsMap.get(key)!=null&&!tableFieldsMap.get(key).isEmpty()){
					primarykey = " CONSTRAINT \"" + tableName +"_PK\" PRIMARY KEY ("+tableFieldsMap.get(key)+")";
				}else{
					sb.append(key).append(" ").append(tableFieldsMap.get(key)).append(",");
				}
			}
			if(primarykey.isEmpty()) {
				
				sql = sb.toString().substring(0, sb.length()-1)+") ";
			}else {
				sql = sb.toString()+ primarykey +") ";
			}
		}
		logger.info("sql:"+sql);
		try {
			return queryUtil.executeUpdate(sql)==0? true:false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 更改表注释
	 * @param param
	 * @return
	 */
	public int updateTableCommentsInfo(Map<String,String> param) {
		String updateSql = "comment on TABLE " + param.get("TABLE_NAME")+" IS '"+param.get("COMMENTS")+"'";
		try {
			queryUtil.executeUpdate(updateSql);
			return 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
		
	}
	
	/**
	 * 更改表中列注释
	 * @param tableName
	 * @param param
	 * @return
	 */
	public int updateTableColumnCommentsInfo(String tableName,Map<String,String> param) {
		for(String column:param.keySet()) {
			
			String updateSql = "COMMENT ON COLUMN " + tableName +"."+column+" IS '"+param.get(column)+"'";
			try {
				
				queryUtil.executeUpdate(updateSql);
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return 1;
		
	}
	
	public int dropTable(Map<String,String> param) {
		String updateSql = "DROP TABLE " + param.get("TABLE_NAME");
		try {
			queryUtil.executeUpdate(updateSql);
			return 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
		
	}
	
	/**
	 * 查询当前数据库的所有表名称
	 * @return
	 */
	public List<Map<String, String>> queryCurDbUserTablesInfo() {
		try {
			return queryUtil.executeQuery(allCurUserTablesInfoSql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return EMPTY_LIST;
	}
	/**
	 * 查询指定表的详细列信息（包括：列名、列类型、列长度、列注释等等）
	 * @param tableName
	 * @return
	 */
	public List<Map<String,String>> queryTableColumnInfoByTableName(String tableName) {
		String sql = tableColInfoSql+tableName +"'" ;
		try {
			logger.info("queryTableColumnInfoByTableName ,sql:"+sql);
			return queryUtil.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return EMPTY_LIST;
	}
	
	public List<Map<String,String>> querySysModuleInfo(Map<String,String> param){
		
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT SMI_ID,MODULE_NAME,MODULE_NO,MUDULE_SHORT_NAME,MUDULE_DESC,CREATOR,CREATE_TIME FROM SYS_MODULE_INFO ");
		
			sb.append(" WHERE ");

			for(String key:param.keySet()) {
			if(key.equals("start-time")&&param.get(key)!=null&&!param.get(key).isEmpty()) {
				String strDate = DateUtils.toDateStr(new Date(Long.valueOf(param.get(key))));
				sb.append(" CREATE_TIME >= TO_DATE('").append(strDate).append("','yyyy-MM-dd') AND ");
			}else if(key.equals("end-time")&&param.get(key)!=null&&!param.get(key).isEmpty()) {
				String strDate = DateUtils.toDateStr(new Date(Long.valueOf(param.get(key))));
				sb.append(" CREATE_TIME <= TO_DATE('").append(strDate).append("','yyyy-MM-dd') AND ");
			}
		}

		sb.append(" 1=1 ORDER BY SMI_ID ASC ");
		
		String sql = sb.toString();
		logger.info("sql:"+sql);
		try {
			return queryUtil.executeQuery(sql,0,2000);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return EMPTY_LIST;
		
	}
	
public List<Map<String,String>> querySysTableInfo(Map<String,String> param){
		
	StringBuffer sb = new StringBuffer();
	sb.append(" SELECT STI_ID,TABLE_NAME,SMI_ID,TABLE_COMMENTS,REMARK,CREATOR,CREATE_TIME FROM SYS_TABLE_INFO ");
		
		sb.append(" WHERE ");

		for(String key:param.keySet()) {
		if(key.equals("start-time")&&param.get(key)!=null&&!param.get(key).isEmpty()) {
			String strDate = DateUtils.toDateStr(new Date(Long.valueOf(param.get(key))));
			sb.append(" CREATE_TIME >= TO_DATE('").append(strDate).append("','yyyy-MM-dd') AND ");
		}else if(key.equals("end-time")&&param.get(key)!=null&&!param.get(key).isEmpty()) {
			String strDate = DateUtils.toDateStr(new Date(Long.valueOf(param.get(key))));
			sb.append(" CREATE_TIME <= TO_DATE('").append(strDate).append("','yyyy-MM-dd') AND ");
		}else if(key.equals("SMI_ID")&&param.get(key)!=null&&!param.get(key).isEmpty()) {
			sb.append(" SMI_ID IN (").append(param.get(key)).append(") AND ");
		}
	}

	sb.append(" 1=1 ORDER BY SMI_ID ASC ");
	
	String sql = sb.toString();
	logger.info("sql:"+sql);
	try {
		return queryUtil.executeQuery(sql,0,2000);
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return EMPTY_LIST;
	
	}

	public int addSysModuleInfo(Map<String,String> param) {
		StringBuffer sysModuleInsertSb = new StringBuffer();
		sysModuleInsertSb.append(" INSERT INTO SYS_MODULE_INFO (SMI_ID,MODULE_NAME,MODULE_NO,MUDULE_SHORT_NAME,MUDULE_DESC,CREATOR,CREATE_TIME) ")
		.append(" SELECT SYS_ID.NEXTVAL,?,(SELECT MAX(MODULE_NO)+1 FROM SYS_MODULE_INFO),?,?,?,SYSDATE FROM DUAL ")
		.append(" WHERE NOT EXISTS(SELECT 1 FROM SYS_MODULE_INFO WHERE MUDULE_SHORT_NAME = ?) ");
		String insertSql = sysModuleInsertSb.toString();
		
		String[] params = {param.get("MODULE_NAME"),param.get("MUDULE_SHORT_NAME"),param.get("MUDULE_DESC"),"admin",param.get("MUDULE_SHORT_NAME")};
		
		try {
			return queryUtil.executeUpdate(insertSql, params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int updateSysModuleInfo(Map<String,String> param) {
		String updateSql = "UPDATE SYS_MODULE_INFO SET MODULE_NAME = ?, MUDULE_DESC = ? WHERE SMI_ID = ? ";
		String[] params = {param.get("MODULE_NAME"),param.get("MUDULE_DESC"),param.get("SMI_ID")};
		try {
			return queryUtil.executeUpdate(updateSql, params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public int delSysModuleInfo(Map<String,String> param) {
		String updateSql = "DELETE FROM SYS_MODULE_INFO  WHERE SMI_ID = ? ";
		String[] params = {param.get("SMI_ID")};
		try {
			return queryUtil.executeUpdate(updateSql, params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
			
	public int addSysTableInfo(Map<String, String> param) {
		String sysTableInsertSql = " INSERT INTO SYS_TABLE_INFO(STI_ID,TABLE_NAME,SMI_ID,TABLE_COMMENTS,CREATOR,CREATE_TIME)\r\n" + 
				" SELECT SYS_ID.NEXTVAL,?,(SELECT SMI_ID FROM SYS_MODULE_INFO WHERE MUDULE_SHORT_NAME = ?) ,?,?,SYSDATE FROM DUAL \r\n" + 
				" WHERE NOT EXISTS(SELECT 1 FROM SYS_TABLE_INFO WHERE TABLE_NAME = ?) ";
		

		String[] params = {param.get("TABLE_NAME"),param.get("TABLE_NAME").split("_")[0],param.get("TABLE_COMMENTS"),"admin",param.get("TABLE_NAME")};
		

		try {
			return queryUtil.executeUpdate(sysTableInsertSql, params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int updateSysTableInfo(Map<String, String> param) {
		String updateSql = "UPDATE SYS_TABLE_INFO SET TABLE_COMMENTS = ?,REMARK = ? WHERE STI_ID = ? ";
		String[] params = { param.get("TABLE_COMMENTS"), param.get("REMARK"), param.get("STI_ID") };
		try {
			return queryUtil.executeUpdate(updateSql, params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int delSysTableInfo(Map<String, String> param) {
		String updateSql = "DELETE FROM SYS_TABLE_INFO  WHERE STI_ID = ? ";
		String[] params = { param.get("STI_ID") };
		try {
			return queryUtil.executeUpdate(updateSql, params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
public boolean batchInsertAllTableInfotoModuleTable() {
	List<Map<String, String>> allTableItems = queryCurDbUserTablesInfo();
	Set<String> shortModuleNames = new HashSet<String>();
	
	for(Map<String, String> mapItem:allTableItems) {
		shortModuleNames.add(mapItem.get("TABLE_NAME").split("_")[0]);
	}
	String sysModuleInsertSql = "";
	StringBuffer sysModuleInsertSb = new StringBuffer();
	sysModuleInsertSb.append(" INSERT INTO SYS_MODULE_INFO (SMI_ID,MODULE_NAME,MODULE_NO,MUDULE_SHORT_NAME,MUDULE_DESC,CREATOR,CREATE_TIME) ")
	.append(" SELECT SYS_ID.NEXTVAL,?,(SELECT NVL(MAX(MODULE_NO),100000)+1 FROM SYS_MODULE_INFO),?,?,?,SYSDATE FROM DUAL ")
	.append(" WHERE NOT EXISTS(SELECT 1 FROM SYS_MODULE_INFO WHERE MUDULE_SHORT_NAME = ?) ");
	sysModuleInsertSql = sysModuleInsertSb.toString();
	for(String shortModuleName:shortModuleNames) {
		String[] params = {shortModuleName,shortModuleName,"desc","admin",shortModuleName};
		try {
			int flag = queryUtil.executeUpdate(sysModuleInsertSql, params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	String sysTableInsertSql = " INSERT INTO SYS_TABLE_INFO(STI_ID,TABLE_NAME,SMI_ID,TABLE_COMMENTS,CREATOR,CREATE_TIME)\r\n" + 
			" SELECT SYS_ID.NEXTVAL,?,(SELECT SMI_ID FROM SYS_MODULE_INFO WHERE MUDULE_SHORT_NAME = ?) ,?,?,SYSDATE FROM DUAL \r\n" + 
			" WHERE NOT EXISTS(SELECT 1 FROM SYS_TABLE_INFO WHERE TABLE_NAME = ?) ";
	
	for(Map<String, String> mapItem:allTableItems) {
		String[] params = {mapItem.get("TABLE_NAME"),mapItem.get("TABLE_NAME").split("_")[0],mapItem.get("COMMENTS"),"admin",mapItem.get("TABLE_NAME")};
		
		try {
			int flag = queryUtil.executeUpdate(sysTableInsertSql, params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	return true;
}

}

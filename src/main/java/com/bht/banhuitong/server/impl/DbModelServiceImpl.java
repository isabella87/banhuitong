package com.bht.banhuitong.server.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.bht.banhuitong.dbservice.impl.DatabaseModelServiceImpl;
import com.bht.banhuitong.server.DbModelService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class DbModelServiceImpl extends RemoteServiceServlet implements DbModelService {

	private final static Logger logger = Logger.getLogger(DbModelServiceImpl.class);

	
	/**
	 * s
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * tableName 创建表的表名；
	 * tableFieldsMap 表字段名及类型（例如：  name:varchar(40)）
	 */
	@Override
	public boolean createTable(String tableName ,Map<String, String> tableFieldsMap,Map<String, String> columnCommentsParamMap) {
		logger.info("tableName:"+tableName);
		DatabaseModelServiceImpl service = new DatabaseModelServiceImpl();
		/*List<Map<String, String>> alltableNames = service.queryCurDbUserTablesInfo();
		List<Map<String, String>> alltablecols = service.queryTableColumnInfoByTableName(tableName);*/

		/*for (Map<String, String> map : alltableNames) {
			logger.info("##########alltableNames##########");
			for (String key : map.keySet()) {
				logger.info(key + "=" + map.get(key));
			}
		}
		
		for (Map<String, String> map : alltablecols) {
			logger.info("#########alltablecols###########");
			for (String key : map.keySet()) {
				logger.info(key + "=" + map.get(key));
			}
		}
		*/
		
			logger.info("#########tableFieldsMap###########");
			for (String key : tableFieldsMap.keySet()) {
				logger.info(key + "=" + tableFieldsMap.get(key));
			}
		
		boolean flag = service.createTable(tableName, tableFieldsMap);
		
		service.updateTableColumnCommentsInfo(tableName, columnCommentsParamMap);
		
		return flag; 
	}
	
	@Override
	public List<Map<String, String>> queryCurDbUserTablesInfo(Map<String, String> paramMap) throws IllegalArgumentException {
		DatabaseModelServiceImpl service = new DatabaseModelServiceImpl();
		List<Map<String, String>> results = service.queryCurDbUserTablesInfo();
		
		logger.info("*****************queryCurDbUserTablesInfo  result:" + results);
		for (String key : paramMap.keySet()) {
			logger.info(key + ":" + paramMap.get(key));
		}
		for (Map<String, String> map : results) {
			logger.info("####################");
			for (String key : map.keySet()) {
				logger.info(key + "=" + map.get(key));
			}
		}
		return results;
	}
	
	@Override
	public List<Map<String, String>> queryTableColsInfo(Map<String, String> paramMap) throws IllegalArgumentException {
		
		String tableName = paramMap.get("TABLE_NAME");
		if(tableName == null ||tableName.isEmpty()) {
			throw new IllegalArgumentException("table name is null or empty!");
		}
		logger.info("####################!!!!!!!!!!!!!tableName:" + tableName);
		DatabaseModelServiceImpl service = new DatabaseModelServiceImpl();
		List<Map<String, String>> results = service.queryTableColumnInfoByTableName(tableName);
		
		logger.info("*****************queryTableColsInfo  result:" + results);
		for (String key : paramMap.keySet()) {
			logger.info(key + ":" + paramMap.get(key));
		}
		for (Map<String, String> map : results) {
			logger.info("####################");
			for (String key : map.keySet()) {
				logger.info(key + "=" + map.get(key));
			}
		}
		return results;
	}
	
	@Override
	public List<Map<String, String>> querySysModuleInfo(Map<String, String> paramMap) throws IllegalArgumentException {
		
		DatabaseModelServiceImpl service = new DatabaseModelServiceImpl();
		List<Map<String, String>> results = service.querySysModuleInfo(paramMap);
		
		logger.info("*****************querySysModuleInfo  result:" + results);
		for (String key : paramMap.keySet()) {
			logger.info(key + ":" + paramMap.get(key));
		}
		for (Map<String, String> map : results) {
			logger.info("####################");
			for (String key : map.keySet()) {
				logger.info(key + "=" + map.get(key));
			}
		}
		return results;
	}
	
	@Override
	public List<Map<String, String>> querySysTableInfo(Map<String, String> paramMap) throws IllegalArgumentException {
		
		DatabaseModelServiceImpl service = new DatabaseModelServiceImpl();
		List<Map<String, String>> results = service.querySysTableInfo(paramMap);
		
		logger.info("*****************querySysTableInfo  result:" + results);
		for (String key : paramMap.keySet()) {
			logger.info(key + ":" + paramMap.get(key));
		}
		for (Map<String, String> map : results) {
			logger.info("####################");
			for (String key : map.keySet()) {
				logger.info(key + "=" + map.get(key));
			}
		}
		return results;
	}

	@Override
	public boolean batchInsertAllTableInfotoModuleTable(Map<String, String> paramMap) {
		DatabaseModelServiceImpl service = new DatabaseModelServiceImpl();
		boolean result = service.batchInsertAllTableInfotoModuleTable();
		logger.info("*****************batchInsertAllTableInfotoModuleTable  result:" + result);
		return result;
	}

	@Override
	public int addSysModuleInfo(Map<String, String> paramMap) throws IllegalArgumentException {
		DatabaseModelServiceImpl service = new DatabaseModelServiceImpl();
		int result =  service.addSysModuleInfo(paramMap);
		logger.info("*****************addSysModuleInfo  result:" + result);
		return result;
	}

	@Override
	public int updateSysModuleInfo(Map<String, String> paramMap) throws IllegalArgumentException {
		DatabaseModelServiceImpl service = new DatabaseModelServiceImpl();
		int result =  service.updateSysModuleInfo(paramMap);
		logger.info("*****************updateSysModuleInfo  result:" + result);
		return result;
	}

	@Override
	public int delSysModuleInfo(Map<String, String> paramMap) throws IllegalArgumentException {
		DatabaseModelServiceImpl service = new DatabaseModelServiceImpl();
		int result =  service.delSysModuleInfo(paramMap);
		logger.info("*****************delSysModuleInfo  result:" + result);
		return result;
	}

	@Override
	public int addSysTableInfo(Map<String, String> paramMap) throws IllegalArgumentException {
		DatabaseModelServiceImpl service = new DatabaseModelServiceImpl();
		int result =  service.addSysTableInfo(paramMap);
		logger.info("*****************addSysTableInfo  result:" + result);
		return result;
	}

	@Override
	public int updateSysTableInfo(Map<String, String> paramMap) throws IllegalArgumentException {
		DatabaseModelServiceImpl service = new DatabaseModelServiceImpl();
		int result =  service.updateSysTableInfo(paramMap);
		logger.info("*****************updateSysTableInfo  result:" + result);
		return result;
	}

	@Override
	public int delSysTableInfo(Map<String, String> paramMap) throws IllegalArgumentException {
		DatabaseModelServiceImpl service = new DatabaseModelServiceImpl();
		int result =  service.delSysTableInfo(paramMap);
		logger.info("*****************delSysTableInfo  result:" + result);
		return result;
	}

	@Override
	public int updateTableInfo(Map<String, String> paramMap) throws IllegalArgumentException {
		DatabaseModelServiceImpl service = new DatabaseModelServiceImpl();
		int result =  service.updateTableCommentsInfo(paramMap);
		logger.info("*****************updateTableInfo  result:" + result);
		return result;
	}

	@Override
	public int dropTableInfo(Map<String, String> paramMap) throws IllegalArgumentException {
		DatabaseModelServiceImpl service = new DatabaseModelServiceImpl();
		int result =  service.dropTable(paramMap);
		logger.info("*****************dropTableInfo  result:" + result);
		return result;
	}
}

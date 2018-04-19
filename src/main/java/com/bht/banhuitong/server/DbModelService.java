package com.bht.banhuitong.server;

import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("action/dbmodel")
public interface DbModelService extends RemoteService {
	boolean createTable(String tableName, Map<String, String> tableFieldsMap,Map<String, String> columnCommentsParamMap);
	int updateTableInfo(Map<String,String> paramMap) throws IllegalArgumentException;
	int dropTableInfo(Map<String,String> paramMap) throws IllegalArgumentException;
	
	boolean batchInsertAllTableInfotoModuleTable(Map<String,String> paramMap);
	List<Map<String,String>> queryCurDbUserTablesInfo(Map<String,String> paramMap) throws IllegalArgumentException;
	List<Map<String,String>> queryTableColsInfo(Map<String,String> paramMap) throws IllegalArgumentException;
	
	List<Map<String,String>> querySysModuleInfo(Map<String,String> paramMap) throws IllegalArgumentException;
	int addSysModuleInfo(Map<String,String> paramMap) throws IllegalArgumentException;
	int updateSysModuleInfo(Map<String,String> paramMap) throws IllegalArgumentException;
	int delSysModuleInfo(Map<String,String> paramMap) throws IllegalArgumentException;
	
	
	List<Map<String,String>> querySysTableInfo(Map<String,String> paramMap) throws IllegalArgumentException;
	int addSysTableInfo(Map<String,String> paramMap) throws IllegalArgumentException;
	int updateSysTableInfo(Map<String,String> paramMap) throws IllegalArgumentException;
	int delSysTableInfo(Map<String,String> paramMap) throws IllegalArgumentException;

}

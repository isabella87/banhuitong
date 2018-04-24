package com.bht.banhuitong.server;

import java.util.List;
import java.util.Map;

import com.bht.banhuitong.BusinessAnnotation;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("action/dbmodel")
public interface DbModelService extends RemoteService {
	
	@BusinessAnnotation(recordLog= true)
	boolean createTable(String tableName, Map<String, String> tableFieldsMap,Map<String, String> columnCommentsParamMap);
	
	@BusinessAnnotation(recordLog= true)
	int updateTableInfo(Map<String,String> paramMap) throws IllegalArgumentException;
	
	@BusinessAnnotation(recordLog= true)
	int dropTableInfo(Map<String,String> paramMap) throws IllegalArgumentException;
	
	@BusinessAnnotation(recordLog= true)
	boolean batchInsertAllTableInfotoModuleTable(Map<String,String> paramMap);
	
	@BusinessAnnotation(serviceno=1,recordLog= true)
	List<Map<String,String>> queryCurDbUserTablesInfo(Map<String,String> paramMap) throws IllegalArgumentException;
	
	@BusinessAnnotation(serviceno=2,recordLog= true)
	List<Map<String,String>> queryTableColsInfo(Map<String,String> paramMap) throws IllegalArgumentException;
	
	@BusinessAnnotation(serviceno=3,recordLog= true)
	List<Map<String,String>> querySysModuleInfo(Map<String,String> paramMap) throws IllegalArgumentException;
	
	@BusinessAnnotation(recordLog= true)
	int addSysModuleInfo(Map<String,String> paramMap) throws IllegalArgumentException;
	
	@BusinessAnnotation(recordLog= true)
	int updateSysModuleInfo(Map<String,String> paramMap) throws IllegalArgumentException;
	
	@BusinessAnnotation(recordLog= true)
	int delSysModuleInfo(Map<String,String> paramMap) throws IllegalArgumentException;
	
	@BusinessAnnotation(serviceno=4,recordLog= true)
	List<Map<String,String>> querySysTableInfo(Map<String,String> paramMap) throws IllegalArgumentException;
	
	@BusinessAnnotation(recordLog= true)
	int addSysTableInfo(Map<String,String> paramMap) throws IllegalArgumentException;
	
	@BusinessAnnotation(recordLog= true)
	int updateSysTableInfo(Map<String,String> paramMap) throws IllegalArgumentException;
	
	@BusinessAnnotation(recordLog= true)
	int delSysTableInfo(Map<String,String> paramMap) throws IllegalArgumentException;

}

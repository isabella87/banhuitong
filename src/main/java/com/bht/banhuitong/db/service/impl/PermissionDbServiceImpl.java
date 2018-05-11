package com.bht.banhuitong.db.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
/**
 * 权限相关服务
 * @author Administrator
 *
 */
public class PermissionDbServiceImpl extends BaseDbService{
	private final static Logger logger = Logger.getLogger(PermissionDbServiceImpl.class);

	
	/**
	 * 用户是否是否存在对应权限制
	 * @param perm
	 * @param userId
	 * @return
	 */
	public List<Map<String,String>> havePerm(int perm, String userId){
		
		String sql = "SELECT MU.U_NAME,MR.R_NAME,MRP.PERM_ID FROM MY_USER MU\r\n" + 
				" JOIN MY_USER_ROLE MUR ON MU.U_NAME = MUR.U_NAME\r\n" + 
				" JOIN MY_ROLE MR ON MR.R_NAME = MUR.R_NAME AND MR.R_ENABLED = 1\r\n" + 
				" JOIN  MY_ROLE_PERM MRP ON MRP.R_NAME = MR.R_NAME AND MRP.PERM_ID ="
				+ perm + 
				" WHERE MU.U_ENABLED = 1 AND MU.U_NAME = '"
				+ userId +"'" ;
		
		try {
			return queryUtil.executeQuery(sql);
		} catch (SQLException e) {
			logger.error(e);
		}
		return null;
	}
	
}

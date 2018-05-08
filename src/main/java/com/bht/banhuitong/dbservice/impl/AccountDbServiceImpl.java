package com.bht.banhuitong.dbservice.impl;

import static java.util.Base64.getEncoder;
import static org.xx.armory.commons.CryptographicUtils.disturb;
import static org.xx.armory.commons.CryptographicUtils.hash;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.bht.banhuitong.dbservice.AccountDbService;
/**
 * 账户相关服务
 * @author Administrator
 *
 */
public class AccountDbServiceImpl extends BaseDbService implements AccountDbService{
	private final static Logger logger = Logger.getLogger(AccountDbServiceImpl.class);

	
	public List<Map<String,String>> getAccountInfoByLoginName(final String loginName){
		
		String sql = "select aur.login_name,aur.mobile ,nvl(api.real_name,aci.org_name) name from acc_user_reg aur \r\n" + 
				"left join acc_person_info api on api.au_id = aur.au_id\r\n" + 
				"left join acc_corp_info aci on aci.au_id = aur.au_id\r\n" + 
				"where aur.login_name = '" + loginName +"'";
		
		try {
			return queryUtil.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
public List<Map<String,String>> getAccountInfoByAuId(final long auId){
		
		String sql = "select aur.login_name,aur.mobile ,nvl(api.real_name,aci.org_name) name from acc_user_reg aur \r\n" + 
				"left join acc_person_info api on api.au_id = aur.au_id\r\n" + 
				"left join acc_corp_info aci on aci.au_id = aur.au_id\r\n" + 
				"where aur.au_id = " + auId +"";
		
		try {
			return queryUtil.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	public long checkLogin(final String loginName,final String pwd) {
		String sql = "select au_id from acc_user_reg where login_name='"+loginName+"' and password = '"+hashPassword(pwd) +"'";
		logger.info("sql:"+sql);
		try {
			List<Map<String,String>> returns = queryUtil.executeQuery(sql);
			for(Map<String,String> ret:returns) {
				logger.info("au_id:"+Long.valueOf(ret.get("AU_ID")));
				if(Long.valueOf(ret.get("AU_ID"))>0) {
					return Long.valueOf(ret.get("AU_ID"));
				};
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public String checkBgLogin(final String loginName,final String pwd) {
		String sql = "select u_name from my_user where u_enabled = 1 and is_locked = 0 and u_name='"+loginName+"' and u_pwd = '"+hashPassword(pwd) +"'";
		logger.info("sql:"+sql);
		try {
			List<Map<String,String>> returns = queryUtil.executeQuery(sql);
			for(Map<String,String> ret:returns) {
				logger.info("U_NAME:"+ret.get("U_NAME"));
				if(!ret.get("U_NAME").isEmpty()) {
					return ret.get("U_NAME");
				};
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
     * 根据指定的密码生成摘要。
     * <p>
     * <p>
     * 生成摘要的过程中使用了扰动因子。
     * </p>
     *
     * @param s
     *         密码。
     * @return 摘要结果。
     * @see java.util.Base64.Encoder#encodeToString(byte[])
     * @see org.xx.armory.commons.CryptographicUtils#disturb(String, String)
     * @see org.xx.armory.commons.CryptographicUtils#hash(String)
     */
    static String hashPassword(final String s) {
        final String salt = "abc"; //前台干扰因子
//        final String salt = "qtX7z";  //后台干扰因子

        return getEncoder().encodeToString(hash(disturb(s, salt)));
    }
}

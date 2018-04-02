package com.bht.banhuitong.dbservice.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.bht.banhuitong.common.DateUtils;
import com.bht.banhuitong.dbservice.PrjDbService;
/**
 * 项目相关服务
 * @author Administrator
 *
 */
public class PrjDbServiceImpl extends BaseDbService implements PrjDbService{
	private final static Logger logger = Logger.getLogger(PrjDbServiceImpl.class);

	@Override
	public List<Map<String,String>> queryPrjList(Map<String,String> param){
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT PI.P_ID, PI.ITEM_NAME, PI.ITEM_NO, PI.TYPE, PP.STATUS, PP.RATE / 100 RATE, ")
		.append(" 						   CASE ")
		.append(" 						    	WHEN PP.STATUS>=50 THEN PP.INVESTED_AMT ")
		.append(" 						    	ELSE PI.AMT ")
		.append(" 						   END AMT,")
		.append(" 				     	   PP.BORROW_DAYS, TRUNC(PI.AMT / 100 * PP.RATE / 365 * PP.BORROW_DAYS, 2) TOTAL_INTEREST, PI.CREATE_TIME, PI.CREATOR,")
		.append(" 				     	   PP.CAPITAL_REPAY_TIME, PI.VISIBLE, PP.FEE_RATE, PP.INVESTED_AMT, PI.TOP_TIME ,PA1.OP_TIME ON_LINE_TIME,PA2.OP_TIME LOAN_TIME,")
		.append(" 				     	   PP.OUT_PROXY,PP.IN_PROXY, PP.REMARK, PP.FINANCIER,")
		.append("               				CASE")
		.append("                  				WHEN (EXISTS(SELECT 1 FROM PRJ_INDEX_CONTRACT PIC1 WHERE PIC1.FILE_TYPE = 39 AND PIC1.P_ID = PP.P_ID) AND ")
		.append("                  				EXISTS(SELECT 1 FROM PRJ_INDEX_CONTRACT PIC2 WHERE PIC2.FILE_TYPE=42 AND PIC2.P_ID = PP.P_ID)) OR")
		.append("                  				EXISTS(SELECT 1 FROM PRJ_INDEX_CONTRACT PIC3 WHERE PIC3.FILE_TYPE=33 AND PIC3.P_ID = PP.P_ID) THEN 1")
		.append("                  			ELSE 0")
		.append("               	END SIGN_STATUS,PP.COST_FEE/100 COST_FEE,PP.SOLD_FEE/100 SOLD_FEE,")
		.append("               	PI.LOCKED_TIME")
		.append(" 				      FROM PRJ_LOAN PP ")
		.append(" 			    INNER JOIN PRJ_INDEX  PI ON PI.P_ID = PP.P_ID")
		.append(" 			    LEFT JOIN (SELECT OP_USER,FLAG,A_ORDER,OP_TIME,P_ID,COMMENTS FROM PRJ_ACTION PA WHERE  OP_TIME = ")
		.append("        	(SELECT MAX(OP_TIME) from PRJ_ACTION WHERE A_ORDER = 40 AND FLAG=1 AND P_ID =PA.P_ID GROUP BY P_ID,A_ORDER)) PA1 ON PA1.P_ID = PI.P_ID AND PA1.A_ORDER = 40")
		.append(" 			    LEFT JOIN (SELECT OP_USER,FLAG,A_ORDER,OP_TIME,P_ID,COMMENTS FROM PRJ_ACTION PA WHERE  OP_TIME = ")
		.append("        	(SELECT MAX(OP_TIME) from PRJ_ACTION WHERE A_ORDER = 80 AND FLAG=1 AND P_ID =PA.P_ID GROUP BY P_ID,A_ORDER)) PA2 ON PA2.P_ID = PI.P_ID AND PA2.A_ORDER = 80");
			
//		if(!param.isEmpty()) {
			sb.append(" WHERE ");
//		}
//		int i = 0;
		for(String key:param.keySet()) {
			if(key.equals("start-time")&&param.get(key)!=null&&!param.get(key).isEmpty()) {
//				i++;
				String strDate = DateUtils.toDateStr(new Date(Long.valueOf(param.get(key))));
				sb.append(" PI.CREATE_TIME >= TO_DATE('").append(strDate).append("','yyyy-MM-dd') AND ");
			}else if(key.equals("end-time")&&param.get(key)!=null&&!param.get(key).isEmpty()) {
//				i++;
				String strDate = DateUtils.toDateStr(new Date(Long.valueOf(param.get(key))));
				sb.append(" PI.CREATE_TIME <= TO_DATE('").append(strDate).append("','yyyy-MM-dd') AND ");
			}else if(key.equals("on-line-start-time")&&param.get(key)!=null&&!param.get(key).isEmpty()) {
//				i++;
				String strDate = DateUtils.toDateStr(new Date(Long.valueOf(param.get(key))));
				sb.append(" PA1.OP_TIME >= TO_DATE('").append(strDate).append("','yyyy-MM-dd') AND ");
			}else if(key.equals("on-line-end-time")&&param.get(key)!=null&&!param.get(key).isEmpty()) {
//				i++;
				String strDate = DateUtils.toDateStr(new Date(Long.valueOf(param.get(key))));
				sb.append(" PA1.OP_TIME <= TO_DATE('").append(strDate).append("','yyyy-MM-dd') AND ");
			}else if(key.equals("loan-start-time")&&param.get(key)!=null&&!param.get(key).isEmpty()) {
//				i++;
				String strDate = DateUtils.toDateStr(new Date(Long.valueOf(param.get(key))));
				sb.append(" PA2.OP_TIME >= TO_DATE('").append(strDate).append("','yyyy-MM-dd') AND ");
			}else if(key.equals("loan-end-time")&&param.get(key)!=null&&!param.get(key).isEmpty()) {
//				i++;
				String strDate = DateUtils.toDateStr(new Date(Long.valueOf(param.get(key))));
				sb.append(" PA2.OP_TIME <= TO_DATE('").append(strDate).append("','yyyy-MM-dd') AND ");
			}else if(key.equals("status")&&param.get(key)!=null&&!param.get(key).isEmpty()) {
//				i++;
				sb.append(" PP.STATUS = ").append(param.get(key)).append(" AND ");
			}else if(key.equals("item-no-key")&&param.get(key)!=null&&!param.get(key).isEmpty()) {
//				i++;
				sb.append(" UPPER(PI.ITEM_NO) LIKE UPPER( '%").append(param.get(key)).append("%')").append(" AND ");
			}else if(key.equals("item-name-key")&&param.get(key)!=null&&!param.get(key).isEmpty()) {
//				i++;
				sb.append(" UPPER(PI.ITEM_NAME) LIKE UPPER( '%").append(param.get(key)).append("%')").append(" AND ");
			}else if(key.equals("financier-key")&&param.get(key)!=null&&!param.get(key).isEmpty()) {
//				i++;
				sb.append(" UPPER(PP.FINANCIER) LIKE UPPER( '%").append(param.get(key)).append("%')").append(" AND ");
			}else if(key.equals("creator-key")&&param.get(key)!=null&&!param.get(key).isEmpty()) {
				sb.append(" UPPER(PI.CREATOR) LIKE UPPER( '%").append(param.get(key)).append("%')").append(" AND ");
			}else if(key.equals("loaner-name")&&param.get(key)!=null&&!param.get(key).isEmpty()) {
				sb.append(" PP.P_ID IN (SELECT COALESCE(P_ID,0) FROM PRJ_MGR_PERSON WHERE NAME LIKE ").append(param.get(key));
				sb.append(" UNION SELECT COALESCE(P_ID,0) FROM PRJ_MGR_ORG WHERE UPPER(ORG_NAME) LIKE UPPER('% ").append(param.get(key)).append("%'))").append(" AND ");
			}else if(key.equals("bondsman-name")&&param.get(key)!=null&&!param.get(key).isEmpty()) {
//				i++;
				sb.append(" PP.P_ID IN (SELECT COALESCE(P_ID,0) FROM PRJ_GUARANTEE_PERSON WHERE NAME LIKE ").append(param.get(key));
				sb.append(" UNION SELECT COALESCE(P_ID, 0) FROM PRJ_GUARANTEE_ORG WHERE UPPER(NAME) LIKE UPPER('% ").append(param.get(key)).append("%'))").append(" AND ");
			}else if(key.equals("type")&&param.get(key)!=null&&!param.get(key).isEmpty()) {
//				i++;
				sb.append(" PI.TYPE = ").append(param.get(key)).append(" AND ");
			}
		}

		sb.append(" 1=1 ORDER BY PI.TOP_TIME DESC NULLS LAST, PP.P_ID ");
		
		String sql = sb.toString();
		logger.info("sql:"+sql);
		try {
			return queryUtil.executeQuery(sql,0,2000);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return EMPTY_LIST;
		
	}
	
}

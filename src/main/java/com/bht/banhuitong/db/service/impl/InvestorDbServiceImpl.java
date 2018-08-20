package com.bht.banhuitong.db.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.bht.banhuitong.db.service.InvestorDbService;

public class InvestorDbServiceImpl extends BaseDbService implements InvestorDbService {

	private final static Logger logger = Logger.getLogger(InvestorDbServiceImpl.class);

	@Override
	public List<Map<String, String>> getInvestorSumAmtInfoList(Map<String,String> param) {
		String sql = "select aju.au_id,aju.name,sum(ti.amt) sum_amt,max(ti.datepoint) datepoint,\r\n" + 
				" case when max(aju.id_type) = '01' then '个人' else '机构' end id_type from p2p.ts_invest ti \r\n" + 
				" join p2p.acc_jx_user aju on aju.au_id = ti.au_id\r\n" + 
				" where ti.datepoint< sysdate and ti.datepoint2 > sysdate \r\n" + 
				" group by aju.au_id,aju.name";

		try {
			return queryUtil.executeQuery(sql);
		} catch (SQLException e) {
			logger.error(e);
			e.printStackTrace();
		}
		return null;
	}

}

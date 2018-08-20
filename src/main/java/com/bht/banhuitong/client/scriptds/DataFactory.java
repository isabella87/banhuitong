package com.bht.banhuitong.client.scriptds;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 对外提供报表需要的数据， 内连接后台服务端通过rpc服务获取后台数据
 * 
 * @author Administrator
 *
 */
public class DataFactory {

	public static List<Map<String, String>> investorSumAmtInfoList = new ArrayList<>();
	public static void setInvestorSumAmtInfoList(List<Map<String, String>> list) {
		investorSumAmtInfoList = list;
	}
	/**
	 * 返回数据转换成实体类，或者直接用map集合
	 */
	public List getInvestorSumAmtInfoList() 
	{
		List<InvestorInfo> result = new ArrayList<InvestorInfo>();
		for(Map<String,String> map:investorSumAmtInfoList) {
			InvestorInfo investorInfo = new InvestorInfo();
			investorInfo.setAuId(map.get("AU_ID"));
			investorInfo.setName(map.get("NAME"));
			investorInfo.setIdType(map.get("ID_TYPE"));
			investorInfo.setDatepoint(map.get("DATEPOINT"));
			investorInfo.setSumAmt(Double.valueOf(map.get("SUM_AMT")));
			result.add(investorInfo);
		}
		
/*
 * test data
 */
		InvestorInfo investorInfo = new InvestorInfo();
		investorInfo.setAuId("1");
		investorInfo.setName("slh");
		investorInfo.setIdType("个人");
		investorInfo.setDatepoint("2018-11-12 12:34:59");
//		investorInfo.setDatepoint(DateUtils.parseDate("2018-11-12 12:34:59"));
		investorInfo.setSumAmt(Double.valueOf(88888888.88));
		result.add(investorInfo);
		
		return result;
	}
	
	public static List getInvestorSumAmtInfoList(List<InvestorInfo> list) {
		
		return list;
	}
	
	public static void main(String[] args) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("AU_ID", "1");
		map.put("NAME", "shiliu");
		map.put("ID_TYPE", "个人");
		map.put("DATEPOINT", "2018-11-12 12:34:59");
		map.put("SUM_AMT", "1010");
		
		
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		list.add(map);
		
		DataFactory.setInvestorSumAmtInfoList(list);
		List lll = new DataFactory().getInvestorSumAmtInfoList();
		System.out.println(lll);
	}
}

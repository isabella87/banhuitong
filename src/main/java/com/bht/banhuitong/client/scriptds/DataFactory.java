package com.bht.banhuitong.client.scriptds;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 对外提供报表需要的数据， 内连接后台服务端通过rpc服务获取后台数据
 * 
 * 问题： 需要延迟加载的问题，或者公共数据线程不安全的问题
 * 解决方式：通过查询数据复写文件形式规避，数据源改成文件形式。
 * @author Administrator
 *
 */
public class DataFactory {

//	private List<Map<String, String>> investorSumAmtInfoList = Collections.synchronizedList(new ArrayList<Map<String, String>>());
	private List<Map<String, String>> investorSumAmtInfoList = new ArrayList<Map<String, String>>();
	
	public static volatile String strData;  //用以解决以上List数据线程不安全的临时方案(Collections.synchronizedList在gwt中不支持)。 其数据格式为“字段名1=值1,字段名2=值2},字段名1=值1,字段名2=值2”
	
	public void setInvestorSumAmtInfoList(List<Map<String, String>> list) {
		investorSumAmtInfoList = list;
	}
	
	private static DataFactory instance = null;	
	
	public DataFactory getLazyInstance() {
		if(null == instance ) {
			instance = new DataFactory();
		}
		return instance;
	}
	
	private List<Map<String, String>> getListDataByStr(){
		List<Map<String, String>> listData = new ArrayList<Map<String, String>>();
		if(null != strData) {
		for (String s : strData.split("},")) {
			Map<String, String> map = new HashMap<String, String>();
			for (String ss : s.split(",")) {
					String[] item = ss.split("=");
					String key = item[0].trim();
					String value = item[1].trim();
					map.put(key, value);
				}
			listData.add(map);
		}
		}
		return listData;
	}
	/**
	 * 返回数据转换成实体类，或者直接用map集合
	 */
	public List getInvestorSumAmtInfoList() 
	{
		List<InvestorInfo> result = new ArrayList<InvestorInfo>();
		for(Map<String,String> map:getListDataByStr()) {
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

	public void setStrData(String str) {
		strData = str;
	}
	
}

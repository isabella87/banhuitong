package com.bht.banhuitong.client.scriptds;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class InvestorInfo {

	private String auId;
	private String name;
	private String idType;
	private double sumAmt;
	private String datepoint;

	private List<InvestorInfo> list;
	
	public String getAuId() {
		return auId;
	}

	public void setAuId(String auId) {
		this.auId = auId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public double getSumAmt() {
		return sumAmt;
	}

	public void setSumAmt(double sumAmt) {
		this.sumAmt = sumAmt;
	}

	public String getDatepoint() {
		return datepoint;
	}

	public void setDatepoint(String datepoint) {
		this.datepoint = datepoint;
	}

	public List<InvestorInfo> getList() {
		return list;
	}

	public void setList(List<Map<String,String>> maps) {
		List<InvestorInfo> relist = new ArrayList<>();
		for(Map<String,String> map:maps) {
			InvestorInfo investorInfo = new InvestorInfo();
			investorInfo.setAuId(map.get("AU_ID"));
			investorInfo.setName(map.get("NAME"));
			investorInfo.setIdType(map.get("ID_TYPE"));
			investorInfo.setDatepoint(map.get("DATEPOINT"));
			investorInfo.setSumAmt(Double.valueOf(map.get("SUM_AMT")));
			relist.add(investorInfo);
		}
		this.list = relist;
	}
}

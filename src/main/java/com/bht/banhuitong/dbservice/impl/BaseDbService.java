package com.bht.banhuitong.dbservice.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.bht.banhuitong.db.QueryUtil;

public class BaseDbService{
	private static final String DB_ALIAS = "default";
	private static final String DB_TYPE = "oracle";
	protected final static QueryUtil queryUtil = QueryUtil.getInstance(DB_ALIAS, DB_TYPE);
	
	protected final static List<Map<String,String>> EMPTY_LIST = new ArrayList<Map<String,String>>();
	
	

	
}

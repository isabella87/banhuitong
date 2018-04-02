package com.bht.banhuitong.db;

import com.bht.banhuitong.common.IParamReader;

public class DBPool implements IParamReader {

	public void readParams() {
		DBInit.initDB();
	}

	public void destroyParams() {

	}
	
	public void reloadParam(){}

}

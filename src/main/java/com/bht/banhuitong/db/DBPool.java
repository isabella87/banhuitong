package com.bht.banhuitong.db;

import com.bht.banhuitong.common.IParamReader;

public class DBPool implements IParamReader {

	@Override
	public void readParams() {
		DBInit.initDB();
	}

	@Override
	public void destroyParams() {

	}
	
	@Override
	public void reloadParam(){}

}

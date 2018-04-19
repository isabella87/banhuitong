package com.bht.banhuitong;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

import com.bht.banhuitong.config.BaseConfigurator;
import com.bht.banhuitong.db.DBPool;

public class DownloadServlet extends HttpServlet {

	/**
	 * 初始化数据库环境
	 */
	private static final long serialVersionUID = 1L;
	private final static Logger logger = Logger.getLogger(DownloadServlet.class);
	
	public void init() throws ServletException{
		
		new DBPool().readParams();
		BaseConfigurator.configure();
		logger.info("init db completed!");
	}
}

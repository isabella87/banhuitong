package com.bht.banhuitong.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ProxoolDBManager implements IDBManager {
	private boolean autoCommit = false;

	private boolean oldAutoCommit;

	private String alias;

	public ProxoolDBManager(String dbAlias, boolean autoCommit) {
		this.alias = dbAlias;
		this.autoCommit = autoCommit;
		if (!alias.startsWith("proxool")) {
			alias = "proxool." + alias;
		}
	}

	@Override
	public void closeConnection(Connection conn) {
		try {
			conn.setAutoCommit(oldAutoCommit);
			conn.close();
		} catch (Exception e) {
		}
	}

	@Override
	public Connection getConnection() throws SQLException {
		Connection conn = null;
		try {
			// Class.forName("org.logicalcobwebs.proxool.ProxoolDriver");
			// Class.forName("net.sourceforge.jtds.jdbc.Driver");

			conn = DriverManager.getConnection(alias);
			//conn = DriverManager.getConnection(dburl);
			/*
			 * oldAutoCommit=conn.getAutoCommit(); conn.setAutoCommit(autoCommit);
			 */
			conn.setAutoCommit(autoCommit);
		} catch (SQLException e) {
			throw e;
		}
		return conn;
	}

	@Override
	public void beginTransaction(Connection conn) {
		;
	}

	@Override
	public void commit(Connection conn) {
		try {
			conn.commit();
		} catch (SQLException e) {
		}
	}

	@Override
	public void rollback(Connection conn) {
		try {
			conn.rollback();
		} catch (SQLException e) {
		}
	}
}

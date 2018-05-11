package com.bht.banhuitong.db;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.bht.banhuitong.common.StringUtils;

public class QueryUtil {
	/**
	 * Logger for this class
	 */
	private final static Logger logger = Logger.getLogger(QueryUtil.class);

	private static final int MAX_RECORDS = 100000;

	private IDBManager dbManager;

	private boolean autoCommit = true;

	private String dbAlias;

	private String dbType;

	public static QueryUtil getInstance(String dbAlias, String dbType) {
		return new QueryUtil(dbAlias, true, dbType);
	}

	private QueryUtil(String dbAlias, boolean autoCommit, String dbType) {
		this.dbAlias = dbAlias;
		this.autoCommit = autoCommit;
		dbManager = new ProxoolDBManager(this.dbAlias, autoCommit);
		this.dbType = dbType;
	}

	/*
	 * private Connection getConnection(){ if (thisconn==null){
	 * thisconn=dbManager.getConnection(); } return thisconn; }
	 */

	public Connection getConnection() throws SQLException {
		return dbManager.getConnection();
	}

	/*
	 * private void closeConnection(Connection conn){
	 * dbManager.closeConnection(conn); if (thisconn!=null) thisconn=null; }
	 */
	private void closeConnection(Connection conn) {
		dbManager.closeConnection(conn);
	}

	public void freeStmt(Connection conn, Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
			}
		}
		closeConnection(conn);
	}

	private void freeRecordSet(Connection conn, Statement stmt, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
			}
		}
		freeStmt(conn, stmt);
	}

	public void begin(Connection conn) {
		if (autoCommit)
			dbManager.beginTransaction(conn);
	}

	public void end(Connection conn) {
		if (autoCommit)
			dbManager.commit(conn);
	}

	public void rollback(Connection conn) {
		dbManager.rollback(conn);
	}

	private List<Map<String, String>> resultSet2Map(ResultSet rs, int records) throws SQLException {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		try {
			ResultSetMetaData md = rs.getMetaData();
			int colus = md.getColumnCount();
			String[] fieldNames = new String[colus];
			for (int i = 0; i < colus; i++) {
				fieldNames[i] = md.getColumnLabel(i + 1);
			}
			int count = 0;
			while (rs.next() && (++count <= records)) {
				Map<String, String> map = new LinkedHashMap<String, String>();
				for (int i = 0; i < colus; i++) {
					int type = md.getColumnType(i + 1);
					if (type == Types.DATE || type == Types.TIMESTAMP) {
						map.put(fieldNames[i].toUpperCase(), StringUtils.toString(rs.getTimestamp(i + 1)));
					} else {
						map.put(fieldNames[i].toUpperCase(), StringUtils.toString(rs.getString(i + 1)));
					}
				}
				list.add(map);
			}
		} catch (SQLException e) {
			throw e;
		}
		return list;
	}

	private List<Map<String, String>> resultSet2Map(ResultSet rs) throws SQLException {
		return resultSet2Map(rs, MAX_RECORDS);
	}

	/**
	 * 调用无参数的存储过程
	 * 
	 * @param proc
	 *            "call procedureName"
	 * @throws SQLException
	 */
	public void callProc(String proc) throws SQLException {
		writeLog(proc, null);
		Connection conn = this.getConnection();// dbManager.getConnection();
		CallableStatement stmt = null;
		try {
			stmt = conn.prepareCall(proc);
			begin(conn);
			stmt.execute();
			end(conn);
		} catch (SQLException e) {
			rollback(conn);
			throw e;
		} finally {
			/*
			 * stmt.close(); dbManager.closeConnection(conn);
			 */
			this.freeStmt(conn, stmt);
		}
	}

	/**
	 * 调用含参数的存储过程
	 * 
	 * @param proc
	 *            "call proceduerName{?,?}"
	 * @throws SQLException
	 */
	public void callProc(String proc, String[] params) throws SQLException {
		Connection conn = this.getConnection();// dbManager.getConnection();
		CallableStatement stmt = null;
		try {
			stmt = conn.prepareCall(proc);
			int index = 1;
			for (String param : params) {
				stmt.setString(index++, param);
			}
			begin(conn);
			stmt.execute();
			end(conn);
		} catch (SQLException e) {
			rollback(conn);
			throw e;
		} finally {
			/*
			 * stmt.close(); dbManager.closeConnection(conn);
			 */
			this.freeStmt(conn, stmt);
		}
	}

	/**
	 * 调用带单个返回值的含参数的存储过程
	 * 
	 * @param proc
	 *            "? = call proceduerName{?,?}"
	 * @param params
	 *            参数集合
	 * @return
	 * @throws SQLException
	 */
	public int callProcInt(String proc, String[] params) throws SQLException {
		writeLog(proc, params);
		int flag = -1;
		Connection conn = this.getConnection();// dbManager.getConnection();
		CallableStatement stmt = null;
		try {
			stmt = conn.prepareCall(proc);
			stmt.registerOutParameter(1, java.sql.Types.INTEGER);
			int index = 2;
			for (String param : params) {
				stmt.setString(index++, param);
			}
			begin(conn);
			stmt.execute();
			flag = stmt.getInt(1);
			end(conn);
		} catch (SQLException e) {
			rollback(conn);
			throw e;
		} finally {
			/*
			 * stmt.close(); dbManager.closeConnection(conn);
			 */
			this.freeStmt(conn, stmt);
		}
		return flag;
	}

	public Map<String, String> callProc(String proc, String[] inParams, String[] inParamsName, String[] outParams)
			throws SQLException {
		Map<String, String> map = new HashMap<String, String>();
		Connection conn = this.getConnection();
		CallableStatement stmt = null;
		try {
			stmt = conn.prepareCall(proc);
			int i = 0;
			for (String inParam : inParams) {
				stmt.setString(inParamsName[i], inParam);
				i++;
			}
			for (String outParam : outParams) {
				stmt.registerOutParameter(outParam, java.sql.Types.VARCHAR);
			}
			if (dbType.equalsIgnoreCase("ORACLE")) {
				stmt.registerOutParameter(1, java.sql.Types.VARCHAR);
			}

			begin(conn);
			stmt.execute();
			for (String outParam : outParams) {
				String key = outParam;
				String value = stmt.getString(key);
				map.put(key, value);
			}
			end(conn);
		} catch (SQLException e) {
			rollback(conn);
			throw e;
		} finally {
			this.freeStmt(conn, stmt);
		}
		return map;
	}

	public Map<String, String> callFun(String proc, String[] inParams, String[] inParamsName, String[] outParams)
			throws SQLException {
		Map<String, String> map = new HashMap<String, String>();
		Connection conn = this.getConnection();
		CallableStatement stmt = null;
		try {
			stmt = conn.prepareCall(proc);
			stmt.registerOutParameter(1, -10);
			int i = 0;
			for (String inParam : inParams) {
				stmt.setString(inParamsName[i], inParam);
				i++;
			}
			for (String outParam : outParams) {
				stmt.registerOutParameter(outParam, java.sql.Types.VARCHAR);
			}
			if (dbType.equalsIgnoreCase("ORACLE")) {
				stmt.registerOutParameter(1, java.sql.Types.VARCHAR);
			}

			begin(conn);
			stmt.execute();
			for (String outParam : outParams) {
				String key = outParam;
				String value = stmt.getString(key);
				map.put(key, value);
			}
			end(conn);
		} catch (SQLException e) {
			rollback(conn);
			throw e;
		} finally {
			this.freeStmt(conn, stmt);
		}
		return map;
	}

	public int executeUpdate(String sql) throws SQLException {
		writeLog(sql, null);
		logger.info("sql:" + sql);
		Connection conn = getConnection();
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			this.begin(conn);
			int result = stmt.executeUpdate(sql);
			this.end(conn);
			return result;
		} catch (SQLException e) {
			this.rollback(conn);
			throw e;
		} finally {
			this.freeStmt(conn, stmt);
		}
		// return -1;
	}

	public int executeUpdate(String sql, String[] params) throws SQLException {
		writeLog(sql, params);
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(sql);
			for (int i = 0; i < params.length; i++) {
				stmt.setString(i + 1, params[i]);
			}
			this.begin(conn);
			int result = stmt.executeUpdate();
			this.end(conn);
			return result;
		} catch (SQLException e) {
			this.rollback(conn);
			throw e;
		} finally {
			this.freeStmt(conn, stmt);
		}
	}

	public int executeCountQuery(String countSql) throws SQLException {
		Connection conn = getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(countSql);
			if (rs.next())
				return rs.getInt(1);
		} catch (SQLException e) {
			throw e;
		} finally {
			this.freeRecordSet(conn, stmt, rs);
		}
		return 0;
	}

	public int executeCountQuery(String countSql, String[] params) throws SQLException {
		writeLog(countSql, params);
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(countSql);
			for (int i = 0; i < params.length; i++) {
				stmt.setString(i + 1, params[i]);
			}
			rs = stmt.executeQuery();
			if (rs.next())
				return rs.getInt(1);
		} catch (SQLException e) {
			throw e;
		} finally {
			this.freeRecordSet(conn, stmt, rs);
		}
		return 0;
	}

	public List<Map<String, String>> executeQuery(String sql) throws SQLException {
		writeLog(sql, null);
		List<Map<String, String>> list = null;
		Connection conn = getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			list = this.resultSet2Map(rs);
		} catch (SQLException e) {
			throw e;
		} finally {
			this.freeRecordSet(conn, stmt, rs);
		}
		return list;
	}

	public List<Map<String, String>> executeQuery(String sql, int start, int length) throws SQLException {
		writeLog(sql, null);
		List<Map<String, String>> list = null;
		Connection conn = getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			String sqlstr = DBPage.pagingSelect(dbType, sql, start, length);
			if (!StringUtils.isBlank(sqlstr)) {
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sqlstr);
			} else {
				stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				rs = stmt.executeQuery(sql);
				rs.absolute(start);
			}
			list = this.resultSet2Map(rs, length);
		} catch (SQLException e) {
			throw e;
		} finally {
			this.freeRecordSet(conn, stmt, rs);
		}
		logger.info(list.toString());
		return list;
	}

	/**
	 * 参数允许不同类型
	 * 
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, String>> executeQuery(String sql, Object[] params) throws SQLException {
		writeLog(sql, params);
		List<Map<String, String>> list = null;
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(sql);
			for (int i = 0; i < params.length; i++) {
				if (params[i] instanceof String) {
					stmt.setString(i + 1, (String) params[i]);
				} else if (params[i] instanceof Date) {
					Date d = (Date) params[i];
					java.sql.Date date = new java.sql.Date(d.getTime());
					stmt.setDate(i + 1, date);
				} else if (params[i] instanceof Integer) {
					stmt.setInt(i + 1, (Integer) params[i]);
				} else if (params[i] instanceof Long) {
					stmt.setLong(i + 1, (Long) params[i]);
				} else if (params[i] instanceof BigDecimal) {
					stmt.setBigDecimal(i + 1, (BigDecimal) params[i]);
				} else if (params[i] instanceof Double) {
					stmt.setDouble(i + 1, (Double) params[i]);
				}
			}
			rs = stmt.executeQuery();
			list = this.resultSet2Map(rs);
		} catch (SQLException e) {
			throw e;
		} finally {
			this.freeRecordSet(conn, stmt, rs);
		}
		return list;
	}

	public List<Map<String, String>> executeQuery(String sql, String[] params) throws SQLException {
		writeLog(sql, params);

		List<Map<String, String>> list = null;
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(sql);
			for (int i = 0; i < params.length; i++) {
				stmt.setString(i + 1, params[i]);
			}
			rs = stmt.executeQuery();
			list = this.resultSet2Map(rs);
		} catch (SQLException e) {
			throw e;
		} finally {
			this.freeRecordSet(conn, stmt, rs);
		}
		return list;
	}

	public List<Map<String, String>> executeQuery(String sql, String[] params, int start, int length)
			throws SQLException {
		writeLog(sql, params);
		List<Map<String, String>> list = null;
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String sqlstr = DBPage.pagingSelect(dbType, sql, start, length);
			if (!StringUtils.isBlank(sqlstr)) {
				stmt = conn.prepareStatement(sqlstr);
				// rs = stmt.executeQuery(sqlstr);
			} else {
				stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				// rs=stmt.executeQuery(sql);
				// rs.absolute(start);
			}
			for (int i = 0; i < params.length; i++) {
				stmt.setString(i + 1, params[i]);
			}
			rs = stmt.executeQuery();
			if (StringUtils.isBlank(sqlstr)) {
				rs.absolute(start);
			}
			list = this.resultSet2Map(rs, length);
		} catch (SQLException e) {
			throw e;
		} finally {
			this.freeRecordSet(conn, stmt, rs);
		}
		return list;
	}

	public String getFieldValue(String sql, String field) throws SQLException {
		Map<String, String> map = firstRecord(sql);
		return map == null ? "" : (String) map.get(field.toUpperCase());
	}

	public String getFieldValue(String sql, String field, String[] params) throws SQLException {
		Map<String, String> map = firstRecord(sql, params);
		return map == null ? "" : (String) map.get(field.toUpperCase());
	}

	public Map<String, String> firstRecord(String sql) throws SQLException {
		List<Map<String, String>> list = this.executeQuery(sql);
		if (list.size() > 0)
			return list.get(0);
		return null;
	}

	public Map<String, String> firstRecord(String sql, String[] params) throws SQLException {
		List<Map<String, String>> list = this.executeQuery(sql, params);
		if (list.size() > 0)
			return list.get(0);
		return null;
	}

	public void batchUpdate(String sql, List<String[]> valueArray) throws SQLException {
		writeLog(sql, null);
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(sql);
			this.begin(conn);
			for (int i = 0; i < valueArray.size(); i++) {
				String[] value = valueArray.get(i);
				for (int j = 0; j < value.length; j++) {
					stmt.setString(j + 1, value[j]);
				}
				stmt.addBatch();
			}
			stmt.executeBatch();
			this.end(conn);
		} catch (SQLException e) {
			this.rollback(conn);
			throw e;
		} finally {
			this.freeStmt(conn, stmt);
		}
		// return -1;
	}

	public void transactUpdate(List<String> sqls) throws SQLException {
		Connection conn = getConnection();
		boolean oldAutoCommit = conn.getAutoCommit();
		conn.setAutoCommit(false);
		Statement stmt = null;
		try {
			dbManager.beginTransaction(conn);
			stmt = conn.createStatement();
			for (String sql : sqls) {
				// this.begin(conn);
				stmt.executeUpdate(sql);
			}
			dbManager.commit(conn);
			// this.end(conn);
		} catch (SQLException e) {
			this.rollback(conn);
			throw e;
		} finally {
			conn.setAutoCommit(oldAutoCommit);
			this.freeStmt(conn, stmt);
		}
	}

	public void transactUpdate(String[] sqls) throws SQLException {
		Connection conn = getConnection();
		boolean oldAutoCommit = conn.getAutoCommit();
		conn.setAutoCommit(false);
		Statement stmt = null;
		try {
			dbManager.beginTransaction(conn);
			stmt = conn.createStatement();
			for (String sql : sqls) {
				// this.begin(conn);
				stmt.executeUpdate(sql);
			}
			dbManager.commit(conn);
			// this.end(conn);
		} catch (SQLException e) {
			this.rollback(conn);
			throw e;
		} finally {
			conn.setAutoCommit(oldAutoCommit);
			this.freeStmt(conn, stmt);
		}
	}

	public void transactUpdate(List<String> sqls, List<String[]> args) throws SQLException {
		Connection conn = getConnection();
		boolean oldAutoCommit = conn.getAutoCommit();
		conn.setAutoCommit(false);
		PreparedStatement stmt = null;
		try {
			dbManager.beginTransaction(conn);
			// stmt = conn.createStatement();
			for (int i = 0; i < sqls.size(); i++) {
				// this.begin(conn);
				String[] params = args.get(i);
				if ((params != null) && (params.length > 0)) {
					// 按传参执行
					stmt = conn.prepareStatement(sqls.get(i));
					for (int j = 0; j < params.length; j++) {
						stmt.setString(j + 1, params[j]);
					}
					try {
						stmt.execute();
					} finally {
						stmt.close();
						stmt = null;
					}
				} else {
					Statement st = conn.createStatement();
					try {
						st.executeUpdate(sqls.get(i));
					} finally {
						st.close();
						st = null;
					}
				}
			}
			dbManager.commit(conn);
			// this.end(conn);
		} catch (SQLException e) {
			this.rollback(conn);
			throw e;
		} finally {
			conn.setAutoCommit(oldAutoCommit);
			this.freeStmt(conn, stmt);
		}
	}

	private void writeLog(String sql, Object[] params) {
		logger.info("enter sql:" + sql);
		StringBuffer sb = new StringBuffer();
		if (params != null && params.length > 0) {
			sb.append("params values: {");
			for (Object obj : params) {
				sb.append(obj.toString()).append(" ;\n");
			}
			sb.append("}");

			logger.info(sb.toString());
		}

	}

}

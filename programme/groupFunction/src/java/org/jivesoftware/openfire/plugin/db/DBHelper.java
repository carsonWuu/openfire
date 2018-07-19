package org.jivesoftware.openfire.plugin.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jivesoftware.database.DbConnectionManager;

public class DBHelper {

	public static int update(String tableName, Map fields, String condition)
			throws SQLException {
		Connection connect = DbConnectionManager.getConnection();
		PreparedStatement prep = null;
		int ret = 0;
		try {
			if (fields.size() < 1)
				return 0;
			StringBuffer sb = new StringBuffer("update ");
			sb.append(tableName).append(" set ");
			Iterator fds;
			for (fds = fields.keySet().iterator(); fds.hasNext();) {
				sb.append((String) fds.next()).append("=?");
				if (fds.hasNext())
					sb.append(",");
			}

			sb.append(" where ").append(condition);
			prep = connect.prepareStatement(sb.toString());
			fds = fields.keySet().iterator();
			int index = 1;
			Object v;
			for (; fds.hasNext(); prep.setObject(index++, v))
				v = fields.get(fds.next());

			ret = prep.executeUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			prep.close();
			DbConnectionManager.closeConnection(connect);
		}
		return ret;
	}

	public static int update(String tableName, Map fields, String condition,
			Object... condv) throws SQLException {
		Connection connect = DbConnectionManager.getConnection();
		PreparedStatement prep = null;
		int ret = 0;
		try {
			if (fields.size() < 1)
				return 0;
			StringBuffer sb = new StringBuffer("update ");
			sb.append(tableName).append(" set ");
			Iterator fds;
			for (fds = fields.keySet().iterator(); fds.hasNext();) {
				sb.append((String) fds.next()).append("=?");
				if (fds.hasNext())
					sb.append(",");
			}

			sb.append(" where ").append(condition);
			prep = connect.prepareStatement(sb.toString());
			fds = fields.keySet().iterator();
			int index = 1;
			Object v;
			for (; fds.hasNext(); prep.setObject(index++, v))
				v = fields.get(fds.next());

			Object aobj[];
			int j = (aobj = condv).length;
			for (int i = 0; i < j; i++) {
				Object vv = aobj[i];
				prep.setObject(index++, vv);
			}

			ret = prep.executeUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			prep.close();
			connect.close();
		}
		return ret;
	}


	public static int insert(String tableName, Map fields) throws SQLException {
		Connection connect = DbConnectionManager.getConnection();
		PreparedStatement prep = null;
		int ret = 0;
		try {
			if (fields.size() < 1)
				return 0;
			StringBuffer sb = new StringBuffer("insert into ");
			sb.append(tableName).append("(");
			Iterator fds;
			for (fds = fields.keySet().iterator(); fds.hasNext();) {
				sb.append((String) fds.next());
				if (fds.hasNext())
					sb.append(",");
			}

			sb.append(") values (");
			for (int i = 0; i < fields.size(); i++) {
				sb.append("?");
				if (i < fields.size() - 1)
					sb.append(",");
			}

			sb.append(");");
			prep = connect.prepareStatement(sb.toString());
			fds = fields.keySet().iterator();
			int index = 1;
			Object v;
			for (; fds.hasNext(); prep.setObject(index++, v))
				v = fields.get(fds.next());

			ret = prep.executeUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			prep.close();
			connect.close();
		}
		return ret;
	}

	public static Map insertAndReturnID(String tableName, Map fields)
			throws SQLException {
		Connection connect = DbConnectionManager.getConnection();
		PreparedStatement prep = null;
		Map map = null;
		ResultSet result = null;
		int ret = 0;
		try {
			if (fields.size() < 1)
				return null;
			StringBuffer sb = new StringBuffer("insert into ");
			sb.append(tableName).append("(");
			Iterator fds;
			for (fds = fields.keySet().iterator(); fds.hasNext();) {
				sb.append((String) fds.next());
				if (fds.hasNext())
					sb.append(",");
			}

			sb.append(") values (");
			for (int i = 0; i < fields.size(); i++) {
				sb.append("?");
				if (i < fields.size() - 1)
					sb.append(",");
			}

			sb.append(");");
			prep = connect.prepareStatement(sb.toString());
			fds = fields.keySet().iterator();
			int index = 1;
			Object v;
			for (; fds.hasNext(); prep.setObject(index++, v))
				v = fields.get(fds.next());

			ret = prep.executeUpdate();
			// 返回ID
			prep = connect.prepareStatement("select last_insert_id() as id");
			map = null;
			result = prep.executeQuery();
			ResultSetMetaData medata = result.getMetaData();
			int columnCount = medata.getColumnCount();
			if (result.next()) {
				map = new HashMap();
				for (index = 0; index < columnCount; index++) {
					String colName = medata.getColumnLabel(index + 1);
					map.put(colName, result.getObject(colName));
				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			prep.close();
			connect.close();
		}
		return map;
	}

	public static List queryForList(String sql, Object... params)
			throws SQLException {
		Connection connect = DbConnectionManager.getConnection();
		PreparedStatement prep = null;
		List resultList = null;
		ResultSet result = null;
		try {
			int index = 1;
			prep = connect.prepareStatement(sql);
			if (params != null) {
				Object aobj[];
				int j = (aobj = params).length;
				for (int i = 0; i < j; i++) {
					Object param = aobj[i];
					prep.setObject(index++, param);
				}

			}
			resultList = new ArrayList();
			result = prep.executeQuery();
			ResultSetMetaData medata = result.getMetaData();
			int columnCount = medata.getColumnCount();
			Map map;
			for (; result.next(); resultList.add(map)) {
				map = new HashMap();
				for (index = 0; index < columnCount; index++) {
					String colName = medata.getColumnLabel(index + 1);
					map.put(colName, result.getObject(colName));
				}

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			result.close();
			prep.close();
			connect.close();
		}

		return resultList;
	}

	public static Map queryFor(String sql, Object... params)
			throws SQLException {
		Connection connect = DbConnectionManager.getConnection();
		PreparedStatement prep = null;
		Map map = null;
		ResultSet result = null;
		try {
			int index = 1;
			prep = connect.prepareStatement(sql);
			if (params != null) {
				Object aobj[];
				int j = (aobj = params).length;
				for (int i = 0; i < j; i++) {
					Object param = aobj[i];
					prep.setObject(index++, param);
				}

			}
			map = null;
			result = prep.executeQuery();
			ResultSetMetaData medata = result.getMetaData();
			int columnCount = medata.getColumnCount();
			if (result.next()) {
				map = new HashMap();
				for (index = 0; index < columnCount; index++) {
					String colName = medata.getColumnLabel(index + 1);
					map.put(colName, result.getObject(colName));
				}

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			result.close();
			prep.close();
			connect.close();
		}
		return map;
	}

	public static Map queryFor(Connection con, String sql, Object... params)
			throws SQLException {
		PreparedStatement prep = null;
		Map map = null;
		ResultSet result = null;
		try {
			int index = 1;
			prep = con.prepareStatement(sql);
			if (params != null) {
				Object aobj[];
				int j = (aobj = params).length;
				for (int i = 0; i < j; i++) {
					Object param = aobj[i];
					prep.setObject(index++, param);
				}

			}
			map = null;
			result = prep.executeQuery();
			ResultSetMetaData medata = result.getMetaData();
			int columnCount = medata.getColumnCount();
			if (result.next()) {
				map = new HashMap();
				for (index = 0; index < columnCount; index++) {
					String colName = medata.getColumnLabel(index + 1);
					map.put(colName, result.getObject(colName));
				}

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			result.close();
			prep.close();
			con.close();
		}
		return map;
	}

	public static Object queryForValue(String sql, Object... params)
			throws SQLException {
		Connection connect = DbConnectionManager.getConnection();
		int index = 1;
		PreparedStatement prep = connect.prepareStatement(sql);
		if (params != null) {
			Object aobj[];
			int j = (aobj = params).length;
			for (int i = 0; i < j; i++) {
				Object param = aobj[i];
				prep.setObject(index++, param);
			}

		}
		Object ret = null;
		ResultSet result = prep.executeQuery();
		if (result.next())
			ret = result.getObject(1);
		result.close();
		prep.close();
		connect.close();
		return ret;
	}

	public static int executeUpdate(String sql, Object... params)
			throws SQLException {
		Connection connect = DbConnectionManager.getConnection();
		PreparedStatement prep = null;
		int ret = 0;
		try {
			int index = 1;
			prep = connect.prepareStatement(sql);
			if (params != null) {
				Object aobj[];
				int j = (aobj = params).length;
				for (int i = 0; i < j; i++) {
					Object param = aobj[i];
					prep.setObject(index++, param);
				}

			}
			ret = prep.executeUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			prep.close();
			connect.close();
		}
		return ret;
	}
}

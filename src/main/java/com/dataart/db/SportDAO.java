/**
 * 
 */
package com.dataart.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import com.dataart.beans.SportBean;

/**
 * @author vmeshcheryakov
 *
 */
public class SportDAO {

	private Connection conn;

	public SportDAO(Connection conn) throws IllegalArgumentException {
		if (conn == null) {
			throw new IllegalArgumentException("Connnection is null!");
		} else {
			this.conn = conn;
		}
	}

	public boolean insert(SportBean bean) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(
					"INSERT INTO sport (name, last_updated) VALUES (?, ?);",
					Statement.RETURN_GENERATED_KEYS
			);
			ps.setString(1, bean.getName());
			ps.setTimestamp(2, new Timestamp(bean.getLastUpdated().getTime()));
			int result = ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				bean.setId(rs.getInt(1));
			}
			return (result == 1);
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					// Nothing to do here
				}
			}
		}
	}

	public SportBean selectById(int id) throws SQLException {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(
					"SELECT name, last_updated FROM sport WHERE (sport_id = ?);"
			);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			SportBean bean = null;
			if (rs.next()) {
				bean = new SportBean();
				bean.setId(id);
				bean.setName(rs.getString(1));
				bean.setLastUpdated(rs.getTimestamp(2));
			}
			rs.close();
			return bean;
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					// Nothing to do here
				}
			}
		}
	}

	public SportBean selectByName(String name) throws SQLException {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(
					"SELECT sport_id, last_updated FROM sport WHERE (name = ?);"
			);
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			SportBean bean = null;
			if (rs.next()) {
				bean = new SportBean();
				bean.setId(rs.getInt(1));
				bean.setName(name);
				bean.setLastUpdated(rs.getTimestamp(2));
			}
			rs.close();
			return bean;
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					// Nothing to do here
				}
			}
		}
	}

	public boolean update(SportBean bean) throws SQLException {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(
					"UPDATE sport SET name = ?, last_updated = ? WHERE sport_id = ?;"
			);
			ps.setString(1, bean.getName());
			ps.setTimestamp(2, new Timestamp(bean.getLastUpdated().getTime()));
			ps.setInt(3, bean.getId());
			int result = ps.executeUpdate();
			return (result == 1);
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					// Nothing to do here
				}
			}
		}
	}

}

/**
 * 
 */
package com.dataart.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.dataart.beans.DivisionBean;

/**
 * @author vmeshcheryakov
 *
 */
public class DivisionDAO {

	private Connection conn;

	public DivisionDAO(Connection conn) throws IllegalArgumentException {
		if (conn == null) {
			throw new IllegalArgumentException("Connnection is null!");
		} else {
			this.conn = conn;
		}
	}

	public boolean insert(DivisionBean bean) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			 ps = conn.prepareStatement(
					"INSERT INTO division (sport_id, name) VALUES (?, ?);",
					Statement.RETURN_GENERATED_KEYS
			);
			ps.setInt(1, bean.getSportId());
			ps.setString(2, bean.getName());
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

	public DivisionBean selectById(int id) throws SQLException {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(
					"SELECT sport_id, name FROM division WHERE (division_id = ?);"
			);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			DivisionBean bean = null;
			if (rs.next()) {
				bean = new DivisionBean();
				bean.setId(id);
				bean.setSportId(rs.getInt(1));
				bean.setName(rs.getString(2));
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

	public DivisionBean selectByName(String name) throws SQLException {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(
					"SELECT division_id, sport_id FROM division WHERE (name = ?);"
			);
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			DivisionBean bean = null;
			if (rs.next()) {
				bean = new DivisionBean();
				bean.setId(rs.getInt(1));
				bean.setSportId(rs.getInt(2));
				bean.setName(name);
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

}

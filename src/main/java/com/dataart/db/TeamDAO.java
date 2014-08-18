/**
 * 
 */
package com.dataart.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.dataart.beans.TeamBean;

/**
 * @author vmeshcheryakov
 *
 */
public class TeamDAO {

	private Connection conn;

	public TeamDAO(Connection conn) throws IllegalArgumentException {
		if (conn == null) {
			throw new IllegalArgumentException("Connnection is null!");
		} else {
			this.conn = conn;
		}
	}

	public boolean insert(TeamBean bean) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(
					"INSERT INTO team (division_id, name) VALUES (?, ?);",
					Statement.RETURN_GENERATED_KEYS
			);
			ps.setInt(1, bean.getDivisionId());
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

	public TeamBean selectById(int id) throws SQLException {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(
					"SELECT division_id, name FROM team WHERE (team_id = ?);"
			);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			TeamBean bean = null;
			if (rs.next()) {
				bean = new TeamBean();
				bean.setId(id);
				bean.setDivisionId(rs.getInt(1));
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

	public TeamBean selectByName(String name) throws SQLException {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(
					"SELECT team_id, division_id FROM team WHERE (name = ?);"
			);
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			TeamBean bean = null;
			if (rs.next()) {
				bean = new TeamBean();
				bean.setId(rs.getInt(1));
				bean.setDivisionId(rs.getInt(2));
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

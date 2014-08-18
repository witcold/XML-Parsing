/**
 * 
 */
package com.dataart.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.dataart.beans.ResultBean;

/**
 * @author vmeshcheryakov
 *
 */
public class ResultDAO {

	private Connection conn;

	public ResultDAO(Connection conn) throws IllegalArgumentException {
		if (conn == null) {
			throw new IllegalArgumentException("Connnection is null!");
		} else {
			this.conn = conn;
		}
	}

	public boolean insert(ResultBean bean) throws SQLException {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(
					"INSERT INTO result (result_guid, division_id, team_x_id, team_y_id, score_x, score_y, description, pub_date, link) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);"
			);
			ps.setString(1, bean.getResultGuid());
			ps.setInt(2, bean.getDivisionId());
			ps.setInt(3, bean.getTeamX());
			ps.setInt(4, bean.getTeamY());
			ps.setInt(5, bean.getScoreX());
			ps.setInt(6, bean.getScoreY());
			ps.setString(7, bean.getDescription());
			ps.setTimestamp(8, new Timestamp(bean.getDate().getTime()));
			ps.setString(9, bean.getLink());
			int result = ps.executeUpdate();
			bean.setRevision(1);
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

	/**
	 * <P>
	 * <B>Note:</B> This method returns <B>false</B> when error is occurred.
	 * <P>
	 * 
	 * @param bean
	 * @return
	 */
	public boolean exist(ResultBean bean) {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(
					"SELECT count(*) FROM result WHERE (result_guid = ?);"
			);
			ps.setString(1, bean.getResultGuid());
			ResultSet rs = ps.executeQuery();
			int count = 0;
			if (rs.next()) {
				count = rs.getInt(1);
			}
			rs.close();
			return (count > 0);
		} catch (SQLException e) {
			return false;
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

	public boolean update(ResultBean bean) throws SQLException {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(
					"INSERT INTO result_history (result_guid, revision, division_id, team_x_id, team_y_id, score_x, score_y, description, pub_date, link) SELECT result_guid, revision, division_id, team_x_id, team_y_id, score_x, score_y, description, pub_date, link FROM result WHERE (result_guid = ?);"
			);
			ps.setString(1, bean.getResultGuid());
			int result1 = ps.executeUpdate();
			ps.close();
			bean.addRevision();
			ps = conn.prepareStatement(
					"UPDATE result SET score_x = ?, score_y = ?, description = ?, pub_date = ?, revision = ? WHERE (result_guid = ?);"
			);
			ps.setInt(1, bean.getScoreX());
			ps.setInt(2, bean.getScoreY());
			ps.setString(3, bean.getDescription());
			ps.setTimestamp(4, new Timestamp(bean.getDate().getTime()));
			ps.setInt(5, bean.getRevision());
			ps.setString(6, bean.getResultGuid());
			int result2 = ps.executeUpdate();
			return (result1 == 1) && (result2 == 1);
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

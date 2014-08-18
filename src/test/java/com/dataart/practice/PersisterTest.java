package com.dataart.practice;

import static org.junit.Assert.*;

import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.h2.tools.RunScript;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.dataart.beans.ResultBean;
import com.dataart.db.Persister;

public class PersisterTest {

	private Connection conn;

	@Before
	public void setUp() throws Exception {
		Class.forName("org.h2.Driver");
		conn = DriverManager.getConnection("jdbc:h2:mem:practice", "sa", "sa");
		Reader reader = new InputStreamReader(PersisterTest.class.getResourceAsStream("/create-db.sql"));
		RunScript.execute(conn, reader);
		reader.close();
	}

	@After
	public void tearDown() throws Exception {
		conn.close();
	}

	@Test
	public void testPersistEmpty() {
		//Preparing test data
		Persister persister = new Persister(conn);
		List<ResultBean> list = new ArrayList<ResultBean>();
		persister.persist(list, "tennis");
	}

	private ResultBean prepareBean(ResultBean bean) {
		bean.setDivisionName("DivisionName");
		bean.setDivisionId(1);
		bean.setTeamXName("TeamX");
		bean.setTeamYName("TeamY");
		bean.setScoreX((byte) 1);
		bean.setScoreY((byte) 2);
		bean.setResultGuid("1");
		return bean;
	}

	@Test
	public void testPersistAdded() throws SQLException{
		//Preparing test data
		Persister persister = new Persister(conn);
		ResultBean bean = prepareBean(new ResultBean());
		List<ResultBean> list = new ArrayList<ResultBean>();
		Date date = new Date();
		bean.setDate(date);
		list.add(bean);
		persister.persist(list, "hockey");
		ResultSet rs = null;
		try {
			rs = conn.createStatement().executeQuery(
					"SELECT result.result_guid, sport.name, division.name, teamx.name, teamy.name, pub_date, result.revision"
					+ " FROM result"
					+ " JOIN division ON (result.division_id = division.division_id)"
					+ " JOIN sport ON (sport.sport_id = division.sport_id)"
					+ " JOIN team AS teamx ON (result.team_x_id = teamx.team_id)"
					+ " JOIN team AS teamy ON (result.team_y_id = teamy.team_id);"
			);
			if (rs.next()) {
				assertEquals("Guid", "1", rs.getString(1));
				assertEquals("Sport", "hockey", rs.getString(2));
				assertEquals("Division", "DivisionName",rs.getString(3));
				assertEquals("Team", "TeamX", rs.getString(4));
				assertEquals("Team", "TeamY", rs.getString(5));
				assertEquals("Pub date", date, rs.getTimestamp(6));
				assertEquals("Revision", 1, rs.getInt(7));
			} else {
				fail("No results");
			}
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
	}

	@Test
	public void testPersistUpdated() throws SQLException {
		//Preparing test data
		Persister persister = new Persister(conn);
		ResultBean bean = prepareBean(new ResultBean());
		List<ResultBean> list = new ArrayList<ResultBean>();
		Date oldDate = new Date(1000);
		bean.setDate(oldDate);
		list.add(bean);
		//Adding
		persister.persist(list, "soccer");
		ResultSet rs = null;
		try {
			rs = conn.createStatement().executeQuery(
					"SELECT result_guid, pub_date, revision FROM result;"
			);
			if (rs.next()) {
				assertEquals("Guid", "1", rs.getString(1));
				assertEquals("Pub date", oldDate, rs.getTimestamp(2));
				assertEquals("Revision", 1, rs.getInt(3));
			} else {
				fail("No results");
			}
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
		//Skipping
		bean.setScoreX((byte) 2);
		bean.setScoreY((byte) 4);
		Date oldDate2 = new Date(2000);
		bean.setDate(oldDate2);
		persister.persist(list, "soccer");
		try {
			rs = conn.createStatement().executeQuery(
					"SELECT result_guid, pub_date, score_x, score_y, revision FROM result;"
			);
			if (rs.next()) {
				assertEquals("Guid", "1", rs.getString(1));
				assertEquals("Pub date", oldDate, rs.getTimestamp(2));
				assertEquals("Score X", (byte) 1, rs.getByte(3));
				assertEquals("Score Y", (byte) 2, rs.getByte(4));
				assertEquals("Revision", 1, rs.getInt(5));
			} else {
				fail("No results");
			}
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
		//Checking history
		try {
			rs = conn.createStatement().executeQuery(
					"SELECT result_guid, revision FROM result_history;"
			);
			if (rs.next()) {
				fail("History is not empty");
			}
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
		//Updating
		Date newDate = new Date();
		bean.setScoreX((byte) 3);
		bean.setScoreY((byte) 6);
		bean.setDate(newDate);
		persister.persist(list, "soccer");
		try {
			rs = conn.createStatement().executeQuery(
					"SELECT result_guid, pub_date, score_x, score_y, revision FROM result;"
			);
			if (rs.next()) {
				assertEquals("Guid", "1", rs.getString(1));
				assertEquals("Pub date", newDate, rs.getTimestamp(2));
				assertEquals("Score X", (byte) 3, rs.getByte(3));
				assertEquals("Score Y", (byte) 6, rs.getByte(4));
				assertEquals("Revision", 2, rs.getInt(5));
			} else {
				fail("No results");
			}
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
		//Checking history
		try {
			rs = conn.createStatement().executeQuery(
					"SELECT result_guid, pub_date, score_x, score_y, revision FROM result_history;"
			);
			if (rs.next()) {
				assertEquals("Guid", "1", rs.getString(1));
				assertEquals("Pub date", oldDate, rs.getTimestamp(2));
				assertEquals("Score X", (byte) 1, rs.getByte(3));
				assertEquals("Score Y", (byte) 2, rs.getByte(4));
				assertEquals("Revision", 1, rs.getInt(5));
			} else {
				fail("History is empty");
			}
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
	}

}

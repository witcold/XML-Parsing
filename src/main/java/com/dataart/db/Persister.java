/**
 * 
 */
package com.dataart.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dataart.beans.DivisionBean;
import com.dataart.beans.ResultBean;
import com.dataart.beans.SportBean;
import com.dataart.beans.TeamBean;

/**
 * @author vmeshcheryakov
 *
 */
public class Persister {

	private static final Logger LOGGER = LoggerFactory.getLogger(Persister.class);

	private Connection conn;

	private SportBean sportBean;

	private SportDAO sportDAO;

	private DivisionDAO divisionDAO;

	private TeamDAO teamDAO;

	private ResultDAO resultDAO;

	private int added, updated, skipped;

	public Persister(Connection conn) throws IllegalArgumentException {
		if (conn == null) {
			throw new IllegalArgumentException("Connnection is null!");
		} else {
			this.conn = conn;
			this.sportDAO = new SportDAO(conn);
			this.divisionDAO = new DivisionDAO(conn);
			this.teamDAO = new TeamDAO(conn);
			this.resultDAO = new ResultDAO(conn);
		}
	}

	private SportBean getSportBean(String sportName) throws SQLException {
		SportBean sportBean = sportDAO.selectByName(sportName);
		if (sportBean == null) {
			sportBean = new SportBean();
			sportBean.setName(sportName);
			sportBean.setLastUpdated(new Date(0));
			sportDAO.insert(sportBean);
		}
		return sportBean;
	}

	private DivisionBean getDivisionBean(String divisionName) throws SQLException {
		DivisionBean divisionBean = divisionDAO.selectByName(divisionName);
		if (divisionBean == null) {
			divisionBean = new DivisionBean();
			divisionBean.setSportId(sportBean.getId());
			divisionBean.setName(divisionName);
			divisionDAO.insert(divisionBean);
		}
		return divisionBean;
	}

	private TeamBean getTeamBean(int divisionId, String teamName) throws SQLException {
		TeamBean teamBean = teamDAO.selectByName(teamName);
		if (teamBean == null) {
			teamBean = new TeamBean();
			teamBean.setDivisionId(divisionId);
			teamBean.setName(teamName);
			teamDAO.insert(teamBean);
		}
		return teamBean;
	}

	private void persistSingleResult(ResultBean bean) {
		try {
			if (bean.getDate().after(sportBean.getLastUpdated())
					&& bean.getDivisionName() != null
					&& bean.getTeamXName() != null
					&& bean.getTeamYName() != null) {
				// Getting DivisionBean
				DivisionBean divisionBean = getDivisionBean(bean.getDivisionName());
				bean.setDivisionId(divisionBean.getId());
				// Getting first TeamBean
				TeamBean teamXBean = getTeamBean(divisionBean.getId(), bean.getTeamXName());
				bean.setTeamX(teamXBean.getId());
				// Getting second TeamBean
				TeamBean teamYBean = getTeamBean(divisionBean.getId(), bean.getTeamYName());
				bean.setTeamY(teamYBean.getId());
				if (resultDAO.exist(bean)) {
					resultDAO.update(bean);
					updated++;
				} else {
					resultDAO.insert(bean);
					added++;
				}
				conn.commit();
			} else {
				skipped++;
			}
		} catch (SQLException e) {
			try {
				LOGGER.warn("Can't persist bean \"{}\"\n{}", bean, e);
				conn.rollback();
			} catch (SQLException e1) {
				// Nothing to do here
			}
		}
	}

	@SuppressWarnings("boxing")
	public void persist(List<ResultBean> list, String sportName) {
		added = 0;
		updated = 0;
		skipped = 0;
		try {
			// Getting SportBean
			sportBean = getSportBean(sportName);
			conn.setAutoCommit(false);
			for (ResultBean bean : list) {
				persistSingleResult(bean);
			}
			// Changing last update time
			sportBean.setLastUpdated(new Date());
			sportDAO.update(sportBean);
			conn.commit();
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			LOGGER.error("Can't persist results", e);
		}
		LOGGER.info("{}:\tAdded to DB: {}\tUpdated: {}\tSkipped: {}", sportName, added, updated, skipped);
	}

}

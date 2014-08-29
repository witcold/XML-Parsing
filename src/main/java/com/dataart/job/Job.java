/**
 * 
 */
package com.dataart.job;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;
import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dataart.beans.ResultBean;
import com.dataart.data.DataProvider;
import com.dataart.data.URLDataProvider;
import com.dataart.db.Persister;
import com.dataart.parsers.Parser;

/**
 * @author vmeshcheryakov
 *
 */
public class Job implements Runnable {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Job.class);

	private static final String URL_STRING = "http://www.scorespro.com/rss2/live-{0}.xml";

	private DataSource ds;

	private DataProvider provider;

	private String sportName;

	private long initialDelay;

	private long period;

	public Job(DataSource ds, String sportName, long initialDelay, long period) throws IllegalArgumentException {
		if (ds == null || initialDelay < 0 || period < 0) {
			throw new IllegalArgumentException("Illegal job arguments!");
		} else {
			this.ds = ds;
			this.provider = new URLDataProvider();
			//this.provider = new FileDataProvider();
			this.sportName = sportName;
			this.initialDelay = initialDelay;
			this.period = period;
		}
	}

	public long getInitialDelay() {
		return initialDelay;
	}

	public void setInitialDelay(long initialDelay) {
		this.initialDelay = initialDelay;
	}

	public long getPeriod() {
		return period;
	}

	public void setPeriod(long period) {
		this.period = period;
	}

	@Override
	public void run() {
		Connection conn = null;
		try {
			conn = ds.getConnection();
			List<ResultBean> list = extract();
			Persister persister = new Persister(conn);
			persister.persist(list, sportName);
		} catch (SQLException e) {
			LOGGER.error("Can't connect to database", e);
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				LOGGER.warn("Can't close connection");
			}
		}
	}

	private List<ResultBean> extract() {
		InputStream is = null;
		try {
			is = provider.getInputStreamFromSource(URL_STRING.replace("{0}", sportName));
			//is = provider.getInputStreamFromSource("src/main/resources/live-{0}.xml".replace("{0}", sportName));
			List<ResultBean> list = Parser.parse(is);
			return list;
		} catch (JAXBException e) {
			LOGGER.error("Can't parse XML", e);
		} catch (IOException e) {
			LOGGER.error("Can't receive data stream", e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					// Nothing to do here
				}
			}
		}
		return Collections.emptyList();
	}

}

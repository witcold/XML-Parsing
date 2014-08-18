/**
 * 
 */
package com.dataart.db;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.postgresql.ds.PGPoolingDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author vmeshcheryakov
 *
 */
public class ConnectionPool {

	private ConnectionPool() {
	}

	public static DataSource getInstance() {
		return SingletonHolder.ds;
	}

	private static final class SingletonHolder {

		private static final Logger LOGGER = LoggerFactory.getLogger(SingletonHolder.class);

		private static Properties prop = new Properties();

		static final PGPoolingDataSource ds = new PGPoolingDataSource();

		static {
			FileInputStream fs = null;
			try {
				fs = new FileInputStream("src/main/resources/db.properties");
				prop.load(fs);
				ds.setServerName(prop.getProperty("server"));
				ds.setDatabaseName(prop.getProperty("database"));
				ds.setUser(prop.getProperty("user"));
				ds.setPassword(prop.getProperty("password"));
				ds.setMaxConnections(Integer.parseInt(prop.getProperty("maxConnections", "10")));
			} catch (Exception e) {
				LOGGER.error("Can't load properties", e);
				System.exit(1);
			} finally {
				try {
					fs.close();
				} catch (IOException e) {
					//Nothing to do here
				}
			}
		}

	}

}

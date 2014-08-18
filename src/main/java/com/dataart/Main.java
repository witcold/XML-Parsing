/**
 * 
 */
package com.dataart;

import java.util.logging.LogManager;

import com.dataart.db.ConnectionPool;
import com.dataart.job.Job;
import com.dataart.schedule.Scheduler;

/**
 * @author vmeshcheryakov
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			LogManager.getLogManager().readConfiguration(Main.class.getResourceAsStream("/logging.properties"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		Scheduler scheduler = new Scheduler(2);
		Job job;
		job = new Job(ConnectionPool.getInstance(), "soccer", 0, 300);
		scheduler.addWorker(job);
		job = new Job(ConnectionPool.getInstance(), "tennis", 0, 300);
		scheduler.addWorker(job);
		scheduler.start();
	}

}

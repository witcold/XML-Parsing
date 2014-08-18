/**
 * 
 */
package com.dataart.schedule;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.dataart.job.Job;

/**
 * @author vmeshcheryakov
 *
 */
public class Scheduler {

	private ScheduledExecutorService service;

	private List<Job> workers;

	public Scheduler(int size) {
		this.service = Executors.newScheduledThreadPool(size);
		this.workers = new ArrayList<>();
	}

	public void addWorker(Job worker) {
		workers.add(worker);
	}

	public void start() {
		for (Job worker : workers) {
			service.scheduleAtFixedRate(worker, worker.getInitialDelay(), worker.getPeriod(), TimeUnit.SECONDS);
		}
	}

	public void stop() {
		service.shutdown();
	}

}

/**
 * 
 */
package com.dataart.beans;

import java.util.Date;

/**
 * @author vmeshcheryakov
 *
 */
public class SportBean {

	private int id;

	private String name;

	private Date lastUpdated;

	/**
	 * 
	 */
	public SportBean() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

}

/**
 * 
 */
package com.dataart.beans;

/**
 * @author vmeshcheryakov
 *
 */
public class TeamBean {

	private int id;

	private int divisionId;

	private String name;

	/**
	 * 
	 */
	public TeamBean() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDivisionId() {
		return divisionId;
	}

	public void setDivisionId(int divisionId) {
		this.divisionId = divisionId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}

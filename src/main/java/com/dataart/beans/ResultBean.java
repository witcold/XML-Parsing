/**
 * 
 */
package com.dataart.beans;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;

/**
 * @author vmeshcheryakov
 *
 */
public class ResultBean {

	private String resultGuid;

	private int divisionId;

	private String divisionName;

	private int teamX;

	private String teamXName;

	private int teamY;

	private String teamYName;

	private byte scoreX;

	private byte scoreY;

	private String title;

	private String description;

	private String pubDate;

	private Date date;

	private String link;

	private int revision;

	/**
	 * 
	 */
	public ResultBean() {
	}

	@XmlElement(name = "guid")
	public String getResultGuid() {
		return resultGuid;
	}

	public void setResultGuid(String resultGuid) {
		this.resultGuid = resultGuid;
	}

	public int getDivisionId() {
		return divisionId;
	}

	public void setDivisionId(int divisionId) {
		this.divisionId = divisionId;
	}

	public String getDivisionName() {
		return divisionName;
	}

	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}

	public int getTeamX() {
		return teamX;
	}

	public void setTeamX(int teamX) {
		this.teamX = teamX;
	}

	public String getTeamXName() {
		return teamXName;
	}

	public void setTeamXName(String teamXName) {
		this.teamXName = teamXName;
	}

	public int getTeamY() {
		return teamY;
	}

	public void setTeamY(int teamY) {
		this.teamY = teamY;
	}

	public String getTeamYName() {
		return teamYName;
	}

	public void setTeamYName(String teamYName) {
		this.teamYName = teamYName;
	}

	public byte getScoreX() {
		return scoreX;
	}

	public void setScoreX(byte scoreX) {
		this.scoreX = scoreX;
	}

	public byte getScoreY() {
		return scoreY;
	}

	public void setScoreY(byte scoreY) {
		this.scoreY = scoreY;
	}

	@XmlElement(name = "title")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@XmlElement(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@XmlElement(name = "pubDate")
	public String getPubDate() {
		return pubDate;
	}

	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@XmlElement(name = "link")
	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public int getRevision() {
		return revision;
	}

	public void setRevision(int revisions) {
		this.revision = revisions;
	}

	public void addRevision() {
		this.revision++;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result
				+ ((divisionName == null) ? 0 : divisionName.hashCode());
		result = prime * result + ((link == null) ? 0 : link.hashCode());
		result = prime * result
				+ ((resultGuid == null) ? 0 : resultGuid.hashCode());
		result = prime * result + scoreX;
		result = prime * result + scoreY;
		result = prime * result
				+ ((teamXName == null) ? 0 : teamXName.hashCode());
		result = prime * result
				+ ((teamYName == null) ? 0 : teamYName.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ResultBean other = (ResultBean) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (divisionName == null) {
			if (other.divisionName != null)
				return false;
		} else if (!divisionName.equals(other.divisionName))
			return false;
		if (link == null) {
			if (other.link != null)
				return false;
		} else if (!link.equals(other.link))
			return false;
		if (resultGuid == null) {
			if (other.resultGuid != null)
				return false;
		} else if (!resultGuid.equals(other.resultGuid))
			return false;
		if (scoreX != other.scoreX)
			return false;
		if (scoreY != other.scoreY)
			return false;
		if (teamXName == null) {
			if (other.teamXName != null)
				return false;
		} else if (!teamXName.equals(other.teamXName))
			return false;
		if (teamYName == null) {
			if (other.teamYName != null)
				return false;
		} else if (!teamYName.equals(other.teamYName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return date + " : (" + divisionName + ")[" + divisionId + "] "
				+ teamXName + '[' + teamX + ']' + " - " 
				+ teamYName + '[' + teamY + ']' + " : "
				+ scoreX + "-" + scoreY + " [" + description + "]";
	}

}

/**
 * 
 */
package com.dataart.beans;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author vmeshcheryakov
 *
 */
@XmlRootElement(name="rss")
public class DataContainer {

	private List<ResultBean> beans;

	@XmlElementWrapper(name="channel")
	@XmlElement(name="item")
	public List<ResultBean> getBeans() {
		return beans;
	}

	public void setBeans(List<ResultBean> beans) {
		this.beans = beans;
	}

}

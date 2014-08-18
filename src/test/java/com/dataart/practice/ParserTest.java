/**
 * 
 */
package com.dataart.practice;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.junit.Test;

import com.dataart.beans.ResultBean;
import com.dataart.parsers.Parser;

/**
 * @author vmeshcheryakov
 *
 */
@SuppressWarnings("static-method")
public class ParserTest {

	/**
	 * Test method for {@link com.dataart.parsers.Parser#parse(java.io.InputStream)}.
	 * @throws Exception 
	 */
	@Test
	public void testParseSingle() throws Exception {
		//Preparing expected result
		List<ResultBean> expected = new ArrayList<ResultBean>();
		ResultBean bean = new ResultBean();
		bean.setResultGuid("123");
		bean.setDivisionName("XXX-YYY");
		bean.setTeamXName("Team1");
		bean.setTeamYName("Team2");
		bean.setScoreX((byte) 1);
		bean.setScoreY((byte) 2);
		bean.setDescription("Descr");
		bean.setDate(new Date(1000));
		bean.setLink("http://");
		expected.add(bean);
		//Preparing test data
		InputStream is = ParserTest.class.getResourceAsStream("/soccer.xml");
		List<ResultBean> actual = Parser.parse(is);
		//Checking
		assertEquals(expected, actual);
		//Clearing
		is.close();
	}


	/**
	 * Test method for {@link com.dataart.parsers.Parser#parse(java.io.InputStream)}.
	 * @throws JAXBException 
	 */
	@Test
	public void testParseLong() throws Exception {
		//Preparing test data
		InputStream is = ParserTest.class.getResourceAsStream("/tennis-10.xml");
		List<ResultBean> actual = Parser.parse(is);
		//Checking
		assertEquals(10, actual.size());
		assertEquals("WTA-S", actual.get(0).getDivisionName());
		assertEquals("http://www.scorespro.com/tennis/livescore/errani-svinci-r-vs-bye/11-08-2014/", actual.get(9).getResultGuid());
		//Clearing
		is.close();
	}

	@Test
	public void testParseFail() throws Exception {
		//Preparing test data
		InputStream is = ParserTest.class.getResourceAsStream("/empty.xml");
		try {
			Parser.parse(is);
		} catch (Exception e) {
			//Checking
			assertTrue(e instanceof NullPointerException);
		} finally {
			// Clearing
			is.close();
		}
	}

}

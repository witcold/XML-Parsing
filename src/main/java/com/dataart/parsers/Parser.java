/**
 * 
 */
package com.dataart.parsers;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dataart.beans.ResultBean;
import com.dataart.beans.DataContainer;
import com.dataart.utils.Utils;

/**
 * @author vmeshcheryakov
 *
 */
public class Parser {

	private static final Logger LOGGER = LoggerFactory.getLogger(Parser.class);

	//#Tennis #Livescore @ScoresPro: (WTA-S) #Ormaechea P. vs #Hantuchova D.: 10-22
	private static Pattern pattern = Pattern.compile(".*:\\s\\W(\\w*-\\w*)\\W\\s#?(.*)\\svs\\s#?(.*):\\s(\\d*)-(\\d*).*");

	public static boolean process(ResultBean bean) {
		SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH);
		Matcher matcher = pattern.matcher(bean.getTitle());
		if (matcher.matches()) {
			bean.setDivisionName(matcher.group(1));
			bean.setTeamXName(matcher.group(2));
			bean.setTeamYName(matcher.group(3));
			bean.setScoreX(Utils.tryParseByte(matcher.group(4)));
			bean.setScoreY(Utils.tryParseByte( matcher.group(5)));
			try {
				bean.setDate(sdf.parse(bean.getPubDate()));
			} catch (ParseException e) {
				LOGGER.warn("Can't parse date \"{}\"", bean.getPubDate());
				return false;
			}
			return true;
		}
		else {
			LOGGER.warn("Can't parse bean title \"{}\"", bean.getTitle());
		}
		return false;
	}

	public static List<ResultBean> parse(InputStream is) throws JAXBException {
		JAXBContext jc = JAXBContext.newInstance(DataContainer.class);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		DataContainer container = (DataContainer) unmarshaller.unmarshal(is);
		ArrayList<ResultBean> list = new ArrayList<ResultBean>();
		for (ResultBean bean : container.getBeans()) {
			if (process(bean)) {
				list.add(bean);
			}
		}
		return list;
	}

}

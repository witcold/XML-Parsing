/**
 * 
 */
package com.dataart.data;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author vmeshcheryakov
 *
 */
public class URLDataProvider implements DataProvider {

	/* (non-Javadoc)
	 * @see com.dataart.data.DataProvider#getInputStreamFromSource(java.lang.String)
	 */
	@Override
	public InputStream getInputStreamFromSource(String source) throws IOException {
		URL url = new URL(source);
		HttpURLConnection c = (HttpURLConnection) url.openConnection();
		c.setRequestMethod("GET");
		c.setUseCaches(false);
		return c.getInputStream();
	}

}

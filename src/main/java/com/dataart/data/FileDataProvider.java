/**
 * 
 */
package com.dataart.data;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author vmeshcheryakov
 *
 */
public class FileDataProvider implements DataProvider {

	/* (non-Javadoc)
	 * @see com.dataart.data.DataProvider#getInputStreamFromSource(java.lang.String)
	 */
	@Override
	public InputStream getInputStreamFromSource(String source) throws IOException {
		return new FileInputStream(source);
	}

}

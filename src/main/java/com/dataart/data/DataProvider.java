/**
 * 
 */
package com.dataart.data;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author vmeshcheryakov
 *
 */
public interface DataProvider {

	public InputStream getInputStreamFromSource(String source) throws IOException;

}

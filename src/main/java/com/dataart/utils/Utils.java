/**
 * 
 */
package com.dataart.utils;

/**
 * @author vmeshcheryakov
 *
 */
public class Utils {

	public static byte tryParseByte(String text) {
		try {
			return Byte.parseByte(text);
		} catch (Exception e) {
			return 0;
		}
	}

}

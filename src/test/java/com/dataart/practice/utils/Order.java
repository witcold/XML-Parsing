/**
 * 
 */
package com.dataart.practice.utils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author vmeshcheryakov
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Order {
	public int value();
}
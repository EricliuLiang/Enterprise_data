package org.kelab.enterprise.util;

/**
 * @author wzy 不短不长八字刚好.
 * @since 2018/9/20 9:08
 */
public class StringUtil {
	public static final boolean notBlank(String str){
		return str != null && !str.trim().equals("");
	}
}

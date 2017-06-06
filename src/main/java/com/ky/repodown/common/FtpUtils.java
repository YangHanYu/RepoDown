package com.ky.repodown.common;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author HanYu
 *
 */
public class FtpUtils {
	
	public static boolean isIndex(String url){
		return StringUtils.endsWith(url, "/");
	}
}

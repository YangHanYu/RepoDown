package com.ky.repodown;


/**
 * 
 * @author HanYu
 *
 */
public interface Walker {
	
	/**
	 * 遍历url所指向的FTP 或 HTTP 等协议指定的目录。
	 * 
	 * @param url
	 *            起始URL
	 */
	public void walk(String url);
	
}

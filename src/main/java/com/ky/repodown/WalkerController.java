package com.ky.repodown;

import java.util.List;

/**
 * 
 * @author HanYu
 *
 */
public interface WalkerController {
	
	public List<String> filter(Iterable<String> links);
}

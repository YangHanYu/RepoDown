package com.ky.repodown;

import com.ky.repodown.model.FtpUrl;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

/**
 * 
 * @author HanYu
 *
 */
public interface Downloader extends EventHandler<FtpUrl>,WorkHandler<FtpUrl> {
	
}

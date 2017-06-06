package com.ky.repodown;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ky.repodown.model.FtpUrl;

/**
 * 
 * @author HanYu
 *
 */
@Component
public class FileDownloader implements Downloader {
	private static final Logger LOGGER = LoggerFactory.getLogger(FileDownloader.class);
	
	public void onEvent(FtpUrl event, long sequence, boolean endOfBatch) throws Exception {
		download(event);
	}

	private void download(FtpUrl event) {
		for(char c : event.getUrl().toCharArray()){
			
		}
		LOGGER.info("downloading:{}", event.getUrl());
	}

	@Override
	public void onEvent(FtpUrl event) throws Exception {
		download(event);
	}

}

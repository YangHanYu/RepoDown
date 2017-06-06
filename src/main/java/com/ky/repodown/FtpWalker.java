package com.ky.repodown;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ky.repodown.common.FtpUtils;
import com.ky.repodown.model.FtpUrl;
import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;

/**
 * 
 * @author HanYu
 *
 */
@Component
public class FtpWalker implements Walker {
	private static final Logger LOGGER = LoggerFactory.getLogger(FtpWalker.class);
	private final RingBuffer<FtpUrl> ringBuffer;
	private CountDownLatch done = new CountDownLatch(1);
	private int timeoutMillis=60*1000;
	
	@Autowired
	private WalkerController walkerController;
	
	public FtpWalker(RingBuffer<FtpUrl> ringBuffer) {
		super();
		this.ringBuffer = ringBuffer;
	}

	private EventTranslatorOneArg<FtpUrl, String> TRANSLATOR = (event, sequence, url)->{
		event.setUrl(url);
	};
	
	
	public void onData(String url){
        ringBuffer.publishEvent(TRANSLATOR, url);
    }
	
	/*
	 * (non-Javadoc)
	 * @see com.ky.repodown.Walker#walk(java.lang.String)
	 */
	public void walk(String url) {
		boolean flag = true;
		while(flag){
			onData(url);
		}
//		executorService.submit(()->{
//			try {
//				walkProcess(url, executorService);
//			} catch (IOException e) {
//				LOGGER.error("遍历失败url[{}]:", url, e);
//			}
//		});
		done.countDown();
	}
	
//	private void walkProcess(String url, ExecutorService service) throws MalformedURLException, IOException{
//		if(FtpUtils.isIndex(url)){
//			Document doc = Jsoup.parse(new URL(url), timeoutMillis);
//			Elements links = doc.select("a");
//			links.remove(0); //移除父目录(../)
//			
//			links.forEach(l->{
//				String href = l.attr("href");
//				if(FtpUtils.isIndex(href)){
//					service.submit(()->{
//						try {
//							walkProcess(href, service);
//						} catch (IOException e) {
//							LOGGER.error("遍历失败url{}:", url, e);
//						}
//					});
//				}else{
//					onData(href);
//				}
//			});
//		}else{
//			onData(url);
//		}
//	}
	

	@Override
	public void awitDone() throws InterruptedException {
		done.await();
		
	}

}

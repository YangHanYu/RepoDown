package com.ky.repodown;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ky.repodown.common.FtpUtils;

import jodd.io.FileNameUtil;
import jodd.io.FileUtil;

/**
 * 
 * @author HanYu
 *
 */
@Component
public class FtpWalker implements Walker {
	private static final Logger LOGGER = LoggerFactory.getLogger(FtpWalker.class);
	private int defaultTimeoutMillis=10_000;
	private int maxTryCount=5;
	private static final String HEADER_USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.10 Safari/537.36";

	@Value("${source.rootUrl}")
    private String rootUrl;
	
	@Value("${dest.downloadDir}")
	private String basePath;
	
	private static final Logger MISSING_LOGGER = LoggerFactory.getLogger("com.ky.repodown.missingLogger");
	
	@Autowired
	private WalkerController walkerController;
	
	@Value("${walker.filter.enabled}")
	private boolean filterEnabled = true;
	
	@Autowired
	private ExecutorService executorService;

	
	/*
	 * (non-Javadoc)
	 * @see com.ky.repodown.Walker#walk(java.lang.String)
	 */
	public void walk(String url) {
	    LOGGER.info("walker filterEnabled:{}", filterEnabled);
		executorService.submit(()->{
			try {
				walkProcess(url, executorService);
			} catch (Exception e) {
			    MISSING_LOGGER.error("URL:{} , exception:{} , e-msg:{}", url, e.getClass().getName(), StringUtils.replaceAll(e.getMessage(), "\n", " ") );
				LOGGER.error("遍历失败url:{}", url, e);
			}
		});
	}
	
	private void walkProcess(String url, ExecutorService service) throws MalformedURLException, IOException{
		if(FtpUtils.isIndex(url)){
			Document doc = null;
			
			int timeoutMillis = defaultTimeoutMillis;
			int tryCount = 0;
			while(true){
				try {
					doc = Jsoup.connect(url).timeout(timeoutMillis).header("User-Agent", HEADER_USER_AGENT).get();
					break;
				} catch (SocketTimeoutException e) {
					if(++tryCount <= maxTryCount){
						timeoutMillis = timeoutMillis*2;
						continue;
					}else{
						throw new SocketTimeoutException("多次重试连接均超时.");
					}
				}
			}
			
			Elements links = doc.select("a");
			links.remove(0); //移除父目录(../)
			
			List<String> hrefLinks = links.stream().map(l->{
				String href = l.attr("href");
				if(href.startsWith("http://")){
				    return href;
				}else{
				    return url.replaceFirst("/*$", "")  + "/" + href.replaceFirst("^/*", "");
				}
			}).collect(Collectors.toList());
			
			if(filterEnabled){
			    hrefLinks = walkerController.filter(hrefLinks);
			}
			
			
			
			hrefLinks.forEach(href->{
				if(FtpUtils.isIndex(href)){
					service.submit(()->{
						try {
							walkProcess(href, service);
						} catch (Exception e) {
						    MISSING_LOGGER.error("URL:{} , exception:{} , e-msg:{}", url, e.getClass().getName(), StringUtils.replaceAll(e.getMessage(), "\n", " ") );
							LOGGER.error("遍历失败url:{}", url, e);
						}
					});
				}else{
					download(href);
				}
			});
		}else{
			download(url);
		}
	}

	public void download(String url) {
		LOGGER.info(url);
		
		String destFile = FileNameUtil.concat(basePath, StringUtils.replace(url, rootUrl, ""));
		
		int timeoutMillis = defaultTimeoutMillis;
		int tryCount = 0;
		
		while (true) {
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeoutMillis).setConnectTimeout(timeoutMillis)
					.build();
			HttpGet get = new HttpGet(url);
			get.setHeader("User-Agent", HEADER_USER_AGENT);
			get.setConfig(requestConfig);
			
			try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
				try (CloseableHttpResponse response = httpclient.execute(get)) {
					HttpEntity entity = response.getEntity();
					try (InputStream in = entity.getContent()) {
//						FileUtil.mkdirs(destFile.replaceFirst("[\\\\/][^\\\\/]+$", ""));
					    FileUtils.forceMkdirParent(new File(destFile));
						FileUtil.touch(destFile);
						FileUtil.writeStream(destFile, in);
						break;
					}
				}
			} catch (Exception e) {
				if (e instanceof ConnectTimeoutException) {
					if (++tryCount <= maxTryCount) {
						timeoutMillis = timeoutMillis * 2;
						continue;
					} else {
					    MISSING_LOGGER.error("URL:{} , exception:{} , e-msg:{}", url, e.getClass().getName(), StringUtils.replaceAll(e.getMessage(), "\n", " ") );
						LOGGER.error("多次重试均超时, url:{}", url, e);
						break;
					}
				} else {
				    MISSING_LOGGER.error("URL:{} , exception:{} , e-msg:{}", url, e.getClass().getName(), StringUtils.replaceAll(e.getMessage(), "\n", " ") );
					LOGGER.error("写文件失败,destFile:{}", destFile, e);
					break;
				}
			} 
		}
		
	}
	


}

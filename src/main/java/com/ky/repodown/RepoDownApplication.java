package com.ky.repodown;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;

/**
 * 
 * @author HanYu
 *
 */
@Configuration
@SpringBootApplication
@EnableMBeanExport
public class RepoDownApplication implements CommandLineRunner {
	private static final Logger LOGGER = LoggerFactory.getLogger(RepoDownApplication.class);
	
	@Autowired
	private Walker walker;
	
	
	@Override
	public void run(String... args) throws Exception {
		String url="http://maven.aliyun.com/nexus/content/groups/public/";
		
		LOGGER.info("start...");
		walker.walk(url);
		LOGGER.info("done..");
	}
	
	@Bean
	public ExecutorService executorService(BlockingQueue<Runnable> workQueue){
		int corePoolSize = 20;
		int maximumPoolSize = 20;
		long keepAliveTime = 30;
		ExecutorService executorService = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, workQueue, new ThreadPoolExecutor.CallerRunsPolicy());
		return executorService;
	}
	
	@Bean
	public BlockingQueue<Runnable> workQueue(){
		return new LinkedBlockingQueue<>();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(RepoDownApplication.class, args);
	}
}

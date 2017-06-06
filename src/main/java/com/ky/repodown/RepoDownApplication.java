package com.ky.repodown;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ky.repodown.model.FtpUrl;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

/**
 * 
 * @author HanYu
 *
 */
@Configuration
@SpringBootApplication
public class RepoDownApplication implements CommandLineRunner {
	private static final Logger LOGGER = LoggerFactory.getLogger(RepoDownApplication.class);
	
	@Autowired
	private Walker walker;
	
	@Autowired
	private Disruptor<FtpUrl> disruptor;
	
	
	@Override
	public void run(String... args) throws Exception {
		String url="http://maven.aliyun.com/nexus/content/groups/public/";
		
		LOGGER.info("start...");
		walker.walk(url);
		walker.awitDone();
		disruptor.shutdown();
		LOGGER.info("done..");
	}
	
	@Bean
    public RingBuffer<FtpUrl> ringBuffer(Disruptor<FtpUrl> disruptor) {
        return disruptor.start();
    }
	
	@SuppressWarnings("unchecked")
	@Bean
	public Disruptor<FtpUrl> disruptor(Downloader downloader){
		Disruptor<FtpUrl> disruptor = new Disruptor<>(FtpUrl::new, 4096, r->{return new Thread(r);}, ProducerType.SINGLE, new YieldingWaitStrategy());
		disruptor.handleEventsWithWorkerPool(downloader, downloader, downloader);
		return disruptor;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(RepoDownApplication.class, args);
	}
}

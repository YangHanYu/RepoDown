package com.ky.repodown;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.LongAdder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.common.hash.BloomFilter;
import com.ky.repodown.common.FtpUtils;

@Component
@Scope("prototype")
public class ReDownBiz {
    @Autowired
    private FtpWalker walker;
    
    @Autowired
    private BlockingQueue<Runnable> workQueue;
    
    @Autowired
    private ExecutorService executorService;
    
    @Value("${redown.waterLine}")
    private int workQueueWaterLine = 5000;
    
    @Value("${redown.downloadLimit}")
    private static int downloadLimit = 1000;
    
    @Autowired
    private LongAdder rowAdder;
    
    @Autowired
    BloomFilter<String> bloomFilter;
    
    private Semaphore downloadSemaphore = new Semaphore(downloadLimit);
    
    public void reDownload(String file) throws UnsupportedEncodingException, FileNotFoundException, IOException, InterruptedException{
        rowAdder.reset();
        
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"))){
            Pattern urlParrern = Pattern.compile("(?i)(?<=^url:)\\bhttp://[-A-Z0-9+&@#/%?=~_|$!:,.;]*[A-Z0-9+&@#/%=~_|$]");
            
            String line = null;
            while((line=reader.readLine())!=null){
                rowAdder.increment();
                
                Matcher m = urlParrern.matcher(line);
                if(m.find()){
                    String url = m.group();
                    
                    if(bloomFilter.mightContain(url) || url.matches(".+\\.html#?/?$")){
                        continue;
                    }
                    while(workQueue.size()>=workQueueWaterLine){
                        Thread.sleep(1000);
                    }
                    
                    if(FtpUtils.isIndex(url)){
                        walker.walk(url);
                    }else{
                        downloadSemaphore.acquire();
                        executorService.submit(()->{
                            try {
                                walker.download(url);
                            } finally {
                                downloadSemaphore.release();
                            }
                        });
                    }
                    bloomFilter.put(url);
                    
                }
            }
        }
    }
}

package com.ky.repodown.jmx;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.LongAdder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import com.google.common.hash.BloomFilter;

/**
 * 
 * @author HanYu
 *
 */
@Component
@ManagedResource(objectName= "bean:name=downloadQueueMBean" , description= "My Managed Bean" ) 
public class DownloadQueueMBean implements IDownloadQueueMBean{
	
	@Autowired
	private BlockingQueue<Runnable> workQueue;
	
	@Autowired
    private LongAdder rowAdder;
	
	@Autowired
    BloomFilter<String> bloomFilter;
	
	@ManagedAttribute(description="文件遍历和下载任务队列的长度.")
	@Override
	public int getQueueLength() {
		return workQueue.size();
	}
	
	@ManagedAttribute(description="重下载,MissingLog文件已处理的行数.")
    @Override
	public long getProcessedRow(){
	    return rowAdder.longValue();
	}
	
	@ManagedAttribute(description="重下载,URL布隆过滤器当前已填充的数据量.")
	@Override
	public long getBloomFilterElements(){
	    return bloomFilter.approximateElementCount();
	}
	
	
	
}

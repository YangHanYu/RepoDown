package com.ky.repodown.jmx;

import java.util.concurrent.BlockingQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

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
	
	@ManagedAttribute
	@Override
	public int getQueueLength() {
		return workQueue.size();
	}
	
	
}

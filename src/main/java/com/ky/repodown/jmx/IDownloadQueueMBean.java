package com.ky.repodown.jmx;


/**
 * 
 * @author HanYu
 *
 */
public interface IDownloadQueueMBean {
    
	public int getQueueLength();

    public long getProcessedRow();

    public long getBloomFilterElements();
}

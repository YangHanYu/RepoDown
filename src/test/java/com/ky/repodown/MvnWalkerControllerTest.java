package com.ky.repodown;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.common.collect.Lists;

/**
 * 
 * @author HanYu
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MvnWalkerControllerTest {
	
	@Autowired
    private MvnWalkerController controller;
	
	@Test
	public void testFilter(){
	    String text = "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/1.0-rc1/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/1.0.1/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/1.0/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/1.1-rc1/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/1.1-rc2/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/1.1.1/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/1.1.2/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/1.1.3/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/1.1.4/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/1.1.5/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/1.1/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/1.2-rc1/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/1.2-rc2/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/1.2.1/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/1.2.2/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/1.2.3/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/1.2.4/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/1.2.5/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/1.2.6/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/1.2.7/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/1.2.8/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/1.2.9/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/1.2/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/2.0-m1/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/2.0-m2/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/2.0-m3/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/2.0-m4/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/2.0-m5/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/2.0-rc1/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/2.0-rc2/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/2.0.1/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/2.0.2/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/2.0.3/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/2.0.4/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/2.0.5/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/2.0.6/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/2.0.7/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/2.0.8/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/2.0/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/2.5.1/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/2.5.2/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/2.5.3/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/2.5.4/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/2.5.5/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/2.5.6.SEC01/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/2.5.6.SEC02/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/2.5.6.SEC03-atlassian-2/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/2.5.6.SEC03-atlassian-6/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/2.5.6.SEC03/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/2.5.6/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/2.5/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/3.0.0.M3/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/3.0.0.M4/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/3.0.0.RC1/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/3.0.0.RC2/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/3.0.0.RC3/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/3.0.0.RELEASE/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/3.0.1.RELEASE/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/3.0.2.RELEASE/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/3.0.3.RELEASE/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/3.0.4.RELEASE/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/3.0.5.RELEASE/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/3.0.5.release/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/3.0.6.RELEASE/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/3.0.6.release/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/3.0.7.RELEASE/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/3.0.7.release/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/3.1.0.M1/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/3.1.0.M2/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/3.1.0.RC1/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/3.1.0.RC2/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/3.1.0.RELEASE/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/3.1.1.RELEASE/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/3.1.1.Release/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/3.1.1.release/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/3.1.2.RELEASE/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/3.1.2.release/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/3.1.3.RELEASE/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/3.1.3.release/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/3.1.4.RELEASE/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/3.1.4.Release/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/3.1.4.release/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/3.2.0.BUILD/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/3.2.0.M1/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/3.2.0.M2/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/3.2.0.RC1/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/3.2.0.RC2/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/3.2.0.RELEASE/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/3.2.0.release/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/3.2.1.RELEASE/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/3.2.10.RELEASE/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/3.2.11.RELEASE/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/3.2.12.RELEASE/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/3.2.13.RELEASE/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/3.2.13.release/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/3.2.14.RELEASE/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/3.2.15.RELEASE/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/3.2.16.RELEASE/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/3.2.17.RELEASE/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/3.2.18.RELEASE/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/3.2.2.RELEASE/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/3.2.3.RELEASE/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/3.2.3.release/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/3.2.4.RELEASE/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/3.2.4.RElEASE/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/3.2.5.RELEASE/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/3.2.5.Release/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/3.2.5.release/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/3.2.6.RELEASE/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/3.2.7.RELEASE/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/3.2.8.RELEASE/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/3.2.8.release/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/3.2.9.RELEASE/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/3.2.9.release/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.0.0.M1/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.0.0.M2/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.0.0.M3/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.0.0.RC1/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.0.0.RC2/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.0.0.RELEASE/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.0.0.Release/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.0.1.RELEASE/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.0.1.Release/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.0.2.RELEASE/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.0.3.RELEASE/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.0.3.release/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.0.4.RELEASE/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.0.4.Release/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.0.4.release/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.0.5.RELEASE/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.0.5.release/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.0.6.RELEASE/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.0.6.release/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.0.7.RELEASE/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.0.8.RELEASE/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.0.9.RELEASE/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.0.9.release/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.1.0.RC1/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.1.0.RC2/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.1.0.RELEASE/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.1.0.Release/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.1.1.RELEASE/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.1.2.RELEASE/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.1.2.Release/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.1.2.release/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.1.3.RELEASE/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.1.3.Release/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.1.3.release/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.1.4.RELEASE/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.1.5.RELEASE/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.1.6.RELEASE/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.1.6.release/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.1.7.RELEASE/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.1.7.RELEaSE/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.1.7.release/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.1.8.RELEASE/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.1.9.RELEASE/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.1.9.release/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.2.0.RC1/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.2.0.RC2/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.2.0.RC3/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.2.0.RELEASE/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.2.1.RELEASE/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.2.2.RELEASE/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.2.3.RELEASE/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.2.4.RELEASE/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.2.4.release/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.2.5.RELEASE/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.2.5.Release/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.2.5.release/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.2.6.RELEASE/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.2.6.Release/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.2.6.release/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.2.7.RELEASE/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.2.8.RELEASE/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.2.8.Release/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.2.8.release/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.2.9.RELEASE/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.3.0.RELEASE/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.3.0.Release/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.3.1.RELEASE/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.3.1.release/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.3.2.RELEASE/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.3.2.release/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.3.3.RELEASE/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.3.3.release/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.3.4.RELEASE/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.3.4.Release/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.3.4.release/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.3.5.RELEASE/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.3.5.Release/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.3.5.release/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.3.6.RELEASE/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.3.6.release/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.3.7.RELEASE/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.3.8.RELEASE/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/RELEASE-5.3.7/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/RELEASE-5.3.8/\r\n" + 
	            "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.3.9.RELEASE/";
	    
		List<String> links = Lists.newArrayList(text.split("\r\n"));
		
		List<String> newLinks = controller.filter(links);
		
		assertThat(newLinks).contains("http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/4.3.9.RELEASE/",
		        "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/1.2.9/",
		        "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/2.5.6/",
		        "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/3.2.18.RELEASE/",
		        "http://maven.aliyun.com/nexus/content/groups/public/org/springframework/spring-core/RELEASE-5.3.8/"
		        );
		
	} 
}

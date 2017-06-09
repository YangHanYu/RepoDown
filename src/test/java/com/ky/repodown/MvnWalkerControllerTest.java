package com.ky.repodown;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

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
		
		List<String> list = Lists.newArrayList();
		
		final String v1_2_0 = "http://maven.aliyun.com/nexus/content/groups/public/JUnit/1.2.0/";
		final String v1_2_1 = "http://maven.aliyun.com/nexus/content/groups/public/JUnit/1.2.1/";
		final String v1_2_3 = "http://maven.aliyun.com/nexus/content/groups/public/JUnit/1.2.3/";
		list.add(v1_2_0);
		list.add(v1_2_1);
		list.add(v1_2_3);
		
		final String v2_1 = "http://maven.aliyun.com/nexus/content/groups/public/JUnit/2.1/";
		final String v2_1_1 = "http://maven.aliyun.com/nexus/content/groups/public/JUnit/2.1.1/";
		list.add(v2_1);
		list.add(v2_1_1);
		
		final String v3_1_1_rc1 = "http://maven.aliyun.com/nexus/content/groups/public/JUnit/3.1.1-RC1";
		final String v3_1_1_rc2 = "http://maven.aliyun.com/nexus/content/groups/public/JUnit/3.1.1-RC2";
		final String v3_1_1release = "http://maven.aliyun.com/nexus/content/groups/public/JUnit/3.1.1-RELEASE";
		final String v3_2_1rc1 = "http://maven.aliyun.com/nexus/content/groups/public/JUnit/3.2.1-RC1";
		final String v3_2_1rc2 = "http://maven.aliyun.com/nexus/content/groups/public/JUnit/3.2.2-RC2";
		list.add(v3_1_1_rc1);
		list.add(v3_1_1_rc2);
		list.add(v3_1_1release);
		list.add(v3_2_1rc1);
		list.add(v3_2_1rc2);
		
		
		final String v4_1_m1 = "http://maven.aliyun.com/nexus/content/groups/public/JUnit/4.1-M1/";
		final String v4_1_m2 = "http://maven.aliyun.com/nexus/content/groups/public/JUnit/4.1-M2/";
		final String v4_1_m3 = "http://maven.aliyun.com/nexus/content/groups/public/JUnit/4.3-M3/";
		list.add(v4_1_m1);
		list.add(v4_1_m2);
		list.add(v4_1_m3);
		
		List<String> newList = controller.filter(list);
		
		assertThat(newList).hasSize(3);
		assertThat(newList).contains(v1_2_3);
		assertThat(newList).contains(v2_1_1);
		assertThat(newList).contains(v3_1_1release);
		
	} 
}

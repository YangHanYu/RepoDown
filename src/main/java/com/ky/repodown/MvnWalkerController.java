package com.ky.repodown;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.ky.repodown.model.VersionPath;

import jodd.io.FileNameUtil;

/**
 * 
 * @author HanYu
 *
 */
@Component
public class MvnWalkerController implements WalkerController{
	private static final String VERSION_DIR_PATTERN = "^\\d+(\\.\\d+){1,2}.*$";
	
	@Override
	public List<String> filter(Iterable<String> links) {
		
		List<String> versonList = Lists.newArrayList();   // 版本目录
		List<String> noVersonList = Lists.newArrayList(); // 非版本目录
		
		// 将目录根据是否是版本目录进行分类
		links.forEach(url->{
			try {
				String dir = getEndDir(url);
				if(dir.matches(VERSION_DIR_PATTERN)){
					versonList.add(url);
				}else{
					noVersonList.add(url);
				}
			} catch (Exception e) {
				noVersonList.add(url);
			}
		});
		
		// 将版本目录名中的版本信息分节提取出来
		List<VersionPath> versionPathList = versonList.stream().map(url->{
			String dir = getEndDir(url);
			return new VersionPath(dir, url);
		}).collect(Collectors.toList());
		
		List<String> result = Lists.newArrayList();
		
		Map<Integer, List<VersionPath>> versionGroups = versionPathList.stream().collect(Collectors.groupingBy(v->{
			return v.getArtiVersion().getMajorVersion();
		}));
		
		versionGroups.forEach((k,v)->{
			
			Optional<VersionPath> vPath = v.stream().max((a, b) -> {
				return a.getCompVersion().compareTo(b.getCompVersion());
			});
			result.add((String) vPath.get().getData());
		});
		
		result.addAll(noVersonList);
		return result;
	}

	private String getEndDir(String url) {
		String dir = FileNameUtil.getName(url.replaceFirst("/$", ""));
		return dir;
	}
	
}

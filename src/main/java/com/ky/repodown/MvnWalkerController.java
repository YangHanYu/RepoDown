package com.ky.repodown;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.maven.artifact.versioning.ArtifactVersion;
import org.apache.maven.artifact.versioning.ComparableVersion;
import org.apache.maven.artifact.versioning.DefaultArtifactVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private static final Logger LOGGER = LoggerFactory.getLogger(MvnWalkerController.class);
	
	/*
	 * 符合maven规范的标准版本字符串，格式形如:d.d.d-xxx , d.d.d.xxx , d.d-xxx
	 */
	private static final String STD_VERSION_STR = "(^\\d+(\\.\\d+){1}(-.+)?$)|(^\\d+(\\.\\d+){2}([-.].+)?$)";
	
	private static final String VERSION_DIGIT_STR = "\\d+(\\.\\d+){1,2}";
	private static final Pattern HAS_VERSION_DIGIT_PATTERN = Pattern.compile(VERSION_DIGIT_STR);
	
	
	@Override
	public List<String> filter(Iterable<String> links) {
		
		List<VersionPath> versionPathList = Lists.newArrayList();
		List<String> nonVersonList = Lists.newArrayList();
		
		// 将目录根据是否是版本目录进行分类
		links.forEach(url->{
			try {
				String dir = getEndDir(url);
				Matcher hasVersionDigitMatcher = HAS_VERSION_DIGIT_PATTERN.matcher(dir);
				
				if(dir.matches(STD_VERSION_STR)){
					String versionString = dir;
					versionPathList.add(new VersionPath(versionString, url));
				}else if(hasVersionDigitMatcher.find()){
					String[] versionDigitLiftAndRight = dir.split(VERSION_DIGIT_STR, 2);
					final String left = versionDigitLiftAndRight[0];
					final String right = versionDigitLiftAndRight[1];
					String versionString = hasVersionDigitMatcher.group() + "-" + left + right; 
					versionPathList.add(new VersionPath(versionString, url));
				}else{
					nonVersonList.add(url);
				}
			} catch (Exception e) {
				nonVersonList.add(url);
				LOGGER.info("不符合预期的URL:{}。已将其归为非版本类目录。 错误信息:{}", url, e.getMessage());
			}
		});
		
		List<String> result = Lists.newArrayList();
		
		//按版本限定符首字符分组
		Map<String, List<VersionPath>> qualifierGroups = versionPathList.stream().collect(Collectors.groupingBy(v->{
			final String qualifier = StringUtils.defaultIfBlank(v.getArtiVersion().getQualifier(), "");
			return qualifier.toUpperCase();
		}));
		
		//取限定符最大的包
		Optional<String> newestQualifierGroupName = qualifierGroups.keySet().stream().max((a, b)->{
			return a.compareToIgnoreCase(b);
		});
		
		//取限定符最大的分组中，各个大版本的最新版本(注:为了尽量取Release版本)
		if(newestQualifierGroupName.isPresent()){
			List<VersionPath> newestQualifierGroup = qualifierGroups.get(newestQualifierGroupName.get());
			
			//按最大版本号进行分组
			Map<Integer, List<VersionPath>> majorGroups = newestQualifierGroup.stream().collect(Collectors.groupingBy(v->{
				return v.getArtiVersion().getMajorVersion();
			}));
			
			majorGroups.forEach((major, majorVersionList)->{
				
				//取版本最新的
				Optional<VersionPath> vPath = majorVersionList.stream().max((a, b) -> {
					return a.getCompVersion().compareTo(b.getCompVersion());
				});
				result.add((String) vPath.get().getData());
			});
		}
		
		//无限定符分组中，各个大版本的最新版本
		List<VersionPath> newestQualifierGroup = qualifierGroups.get("");
		if(newestQualifierGroup!=null){
			
			//按最大版本号进行分组
			Map<Integer, List<VersionPath>> majorGroups = newestQualifierGroup.stream().collect(Collectors.groupingBy(v->{
				return v.getArtiVersion().getMajorVersion();
			}));
			
			majorGroups.forEach((major, majorVersionList)->{
				
				//取版本最新的
				Optional<VersionPath> vPath = majorVersionList.stream().max((a, b) -> {
					return a.getCompVersion().compareTo(b.getCompVersion());
				});
				result.add((String) vPath.get().getData());
			});
		}
		
	//////////////////////////////////////////////////////////////////////////////
		
//		//按最大版本号进行分组
//		Map<Integer, List<VersionPath>> majorGroups = versionPathList.stream().collect(Collectors.groupingBy(v->{
//			return v.getArtiVersion().getMajorVersion();
//		}));
//		
//		
//		majorGroups.forEach((major, majorVersionList)->{
//			
//			//按版本限定符首字符分组
//			Map<String, List<VersionPath>> qualifierGroups = majorVersionList.stream().collect(Collectors.groupingBy(v->{
//				final String qualifier = StringUtils.defaultIfBlank(v.getArtiVersion().getQualifier(), "");
//				String startChar = StringUtils.substring(qualifier, 0, 1).toUpperCase();
//				return startChar;
//			}));
//			
//			qualifierGroups.forEach((qualifier, qualifierVersionList)->{
//				
//				//按小版本分组
//				Map<String, List<VersionPath>> minVerGroups = qualifierVersionList.stream().collect(Collectors.groupingBy(vp->{
//					int minor = vp.getArtiVersion().getMinorVersion();
//					int incremental = vp.getArtiVersion().getIncrementalVersion();
//					
//					int lenght = 30; //补齐长度，一般认为版本号长度不超过30
//					return StringUtils.leftPad(String.valueOf(minor), lenght, "0") + "." + StringUtils.leftPad(String.valueOf(incremental), lenght, "0");
//				}));
//				
//				minVerGroups.forEach((minVer, minVerVersionList)->{
//					
//					//取版本最新的
//					Optional<VersionPath> vPath = minVerVersionList.stream().max((a, b) -> {
//						return a.getCompVersion().compareTo(b.getCompVersion());
//					});
//					result.add((String) vPath.get().getData());
//				});
//			});
//		});
		
		result.addAll(nonVersonList);
		
		return result;
	}

	private String getEndDir(String url) {
		String dir = FileNameUtil.getName(url.replaceFirst("/$", ""));
		return dir;
	}
	
	public static void main(String[] args) {
		ArtifactVersion v = new DefaultArtifactVersion("1.2.3dabc");
		System.out.println(v.getQualifier());
		System.out.println(v.getMajorVersion());
		System.out.println(v.getMinorVersion());
		System.out.println(v.getIncrementalVersion());
		System.out.println(v.getBuildNumber());
		
		ComparableVersion cv1 =new ComparableVersion("1.2.1.Me");
		ComparableVersion cv2 =new ComparableVersion("1.2.1.RE");
		System.out.println("+++++++++++"+ cv1.compareTo(cv2));
		System.out.println("+++++++++++"+ StringUtils.substring("", 0, 1));
	}
	
}

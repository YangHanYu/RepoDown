package com.ky.repodown;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.ky.repodown.model.QualifierVersion;
import com.ky.repodown.model.VersionPath;


/**
 * 
 * @author HanYu
 *
 */
@Component
public class MvnWalkerController implements WalkerController{
	private static final Logger LOGGER = LoggerFactory.getLogger(MvnWalkerController.class);
	private static final Pattern URL_END_DIR_PATTERN = Pattern.compile("(?<=/)[^/]+(?=/$|$)");
	private static final Pattern VERSION_DIR_PATTERN = Pattern.compile("^(?:([A-Za-z][\\w_-]+?)[.\\-\\s]|)(\\b\\d+(?:\\.\\d+){1,2}\\b)(?:[.-](\\D.+)|)$");
	
	private static final Pattern QUALIFIER_VERSION_PATTERN = Pattern.compile("(\\d+(?:\\.\\d+){1,2}\\b(?:[.-].+?|))(\\d*)$");
	
	
	@Override
	public List<String> filter(Iterable<String> links) {
	    List<String> result = Lists.newArrayList();
	    List<VersionPath> versionList = Lists.newArrayList();
	    List<String> nonVersionList = Lists.newArrayList();
	    
		links.forEach(link->{
		    try {
                String dirName = getEndDir(link);
                Matcher m = VERSION_DIR_PATTERN.matcher(dirName);
                if(m.find()){
                    String prefix = m.group(1);
                    String infix = m.group(2);
                    String suffix = m.group(3);
                    
                    if(prefix==null && suffix==null){
                        versionList.add(new VersionPath(infix, link));
                    }else if(prefix==null && suffix!=null){
                        versionList.add(new VersionPath(infix + "-" + suffix, link));
                    }else if(prefix!=null && suffix==null){
                        versionList.add(new VersionPath(infix + "-" + prefix, link));
                    }else{
                        versionList.add(new VersionPath(infix + "-" + suffix, link));
                    }
                }else{
                    nonVersionList.add(link);
                }
            } catch (Exception e) {
                nonVersionList.add(link);
            }
		});
		
		List<VersionPath> riddleList = Lists.newArrayList();
		versionList.stream().collect(Collectors.groupingBy(v->{ //按大版本号分组
		    int major = v.getArtiVersion().getMajorVersion();
		    return major;
		})).forEach((major, majorVerList)->{ 
		    majorVerList.stream().collect(Collectors.groupingBy(v->{ //按限定符分组
		        String qualifier = v.getArtiVersion().getQualifier();
	            return StringUtils.defaultIfEmpty(qualifier, "").toUpperCase();
		    })).forEach((qualifier, qualifierVerList)->{ 
		        Optional<VersionPath> max = qualifierVerList.stream().max((a, b)->{ //取版本最大的
		            return a.getCompVersion().compareTo(b.getCompVersion());
		        });
		        if(max.isPresent()){
		            riddleList.add(max.get());
		        }
		    });
		});
		
		riddleList.stream().map(v->{
		    String verStr = v.getVersionString();
		    Matcher m = QUALIFIER_VERSION_PATTERN.matcher(verStr);
		    m.find();
		    String head = m.group(1);
		    final String qVersion = m.group(2);
		    int qualifierVersion = -1;
		    if(StringUtils.isNotBlank(qVersion)){
		        qualifierVersion = NumberUtils.toInt(qVersion);
		    }
		    
		    return new QualifierVersion(head, qualifierVersion, v.getData());
		}).collect(Collectors.groupingBy(QualifierVersion::getVersionHead)).forEach((q, l)->{
		    Optional<QualifierVersion> max = l.stream().max(Comparator.comparing(QualifierVersion::getQualifierVersion));
		    if(max.isPresent()){
		        result.add((String) max.get().getData());
		    }
		});
		
		result.addAll(nonVersionList);
		return result;
	}

	private String getEndDir(String url) {
	    Matcher m = URL_END_DIR_PATTERN.matcher(url);
	    m.find();
	    return m.group();
	}
	
}

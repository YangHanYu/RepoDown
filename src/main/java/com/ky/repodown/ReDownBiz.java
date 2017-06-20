package com.ky.repodown;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ky.repodown.common.FtpUtils;

@Component
@Scope("prototype")
public class ReDownBiz {
    
    @Autowired
    private FtpWalker walker;
    
    public void reDownload(String file) throws UnsupportedEncodingException, FileNotFoundException, IOException{
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"))){
            Pattern urlParrern = Pattern.compile("(?i)(?<=^url:)\\bhttp://[-A-Z0-9+&@#/%?=~_|$!:,.;]*[A-Z0-9+&@#/%=~_|$]");
            
            String line = null;
            while((line=reader.readLine())!=null){
                Matcher m = urlParrern.matcher(line);
                if(m.find()){
                    String url = m.group();
                    if(FtpUtils.isIndex(url)){
                        walker.walk(url);
                    }else{
                        walker.download(url);
                    }
                }
            }
        }
    }
}

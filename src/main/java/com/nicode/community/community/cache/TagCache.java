package com.nicode.community.community.cache;

import com.nicode.community.community.dto.TagDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TagCache {
    public static List<TagDTO> getCache(){
        ArrayList<TagDTO> tagDTOS = new ArrayList<>();

        TagDTO programLanguage = new TagDTO();
        programLanguage.setCategoryName("开发语言");
        programLanguage.setTags(Arrays.asList("Java","JavaScript","CSS","HTML","PHP","Python","Ruby","Golang","node","shell"
                , "bash","node.js","objective-c","swift","C","C#","sass","less","asp.net","C++","lua","Scala","actionscript"
                ,"rust","erlang","perl"));
        tagDTOS.add(programLanguage);

        TagDTO framework = new TagDTO();
        framework.setCategoryName("平台框架");
        framework.setTags(Arrays.asList("laravel", "spring", "express", "django", "flask", "yii", "ruby-on-rails", "tornado", "koa", "struts"));
        tagDTOS.add(framework);

        TagDTO server = new TagDTO();
        server.setCategoryName("服务器");
        server.setTags(Arrays.asList("linux","nginx","docker","apache","ubuntu","centos","缓存","tomcat","负载均衡","unix","hadoop","windows-server"));
        tagDTOS.add(server);

        TagDTO DBCache = new TagDTO();
        DBCache.setCategoryName("数据库和缓存");
        DBCache.setTags(Arrays.asList("mysql","redis","mongodb","sql","oracle","nosql","memcached","sqlserver","postgresql","sqlite"));
        tagDTOS.add(DBCache);

        TagDTO devTools = new TagDTO();
        devTools.setCategoryName("开发工具");
        devTools.setTags(Arrays.asList("git","github","visual-studio-code","vim","sublime-text","xcode","intellij-idea","eclipse","maven","ide","svn","visual-studio","atom","emacs","textmate","hg"));
        tagDTOS.add(devTools);

        TagDTO systemDevice = new TagDTO();
        systemDevice.setCategoryName("系统设备");
        systemDevice.setTags(Arrays.asList("android","ios","chrome","windows","iphone","firefox","internet-explorer","safari","ipad","opera","apple-watch"));
        tagDTOS.add(systemDevice);

        return tagDTOS;
    };

    public static String filterInvalid(String tags){
        String[] split = StringUtils.split(tags, ",");
        List<String> tagList = getCache().stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
        String invalidTags = Arrays.stream(split).filter(t -> !tagList.contains(t)).collect(Collectors.joining(","));
        return invalidTags;
    }
}

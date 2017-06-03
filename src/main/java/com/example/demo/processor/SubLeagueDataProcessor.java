package com.example.demo.processor;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/06/03.
 *
 * @author Administrator
 */
@Component
public class SubLeagueDataProcessor implements PageProcessor {
    private final static Logger logger = LoggerFactory.getLogger(SubLeagueDataProcessor.class);

    private Site site = Site
            .me()
            .setRetryTimes(3)
            .setSleepTime(1000)
            .setCharset("UTF-8")
            .setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.98 Safari/537.36")
            ;

    @Override
    public void process(Page page) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_MISSING_VALUES, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        String html = page.getRawText();
        BufferedReader reader = new BufferedReader(new StringReader(html));
        String lineTxt;
        String lineTitle;
        String lineContent;
        String url = page.getUrl().toString();
        logger.info(url);
        try {
            Map<String,List> matchMap= new HashMap<>();
            page.putField("LeagueSeason",url.substring(url.indexOf("t/")+2,url.indexOf("/s")));
            while((lineTxt = reader.readLine()) != null){
                if(lineTxt.contains("=")){
                    lineTitle = lineTxt.substring(0,lineTxt.indexOf("="));
                    lineContent = lineTxt.substring(lineTxt.indexOf(" = ")+3,lineTxt.length()-1);
                    List list = new ArrayList();

                    if(lineTitle.contains("arrLeague")){
                        try{
                            list = objectMapper.readValue(lineContent,List.class);
                        }catch (Exception e){
                            System.out.println(e.getMessage());
                        }
                        page.putField("leagueId",String.valueOf(list.get(0)));
                        page.putField("league",list);
                    }else if(lineTitle.contains("arrTeam")){
                        try{
                            list = objectMapper.readValue(lineContent,List.class);
                        }catch (Exception e){
                            System.out.println(e.getMessage());
                        }
                        page.putField("team",list);
                    }else if(lineTitle.contains("jh[\"")){
                        String key = lineTitle.substring(lineTitle.indexOf("jh[\"")+4,lineTitle.indexOf("\"]"));
                        try{
                            list = objectMapper.readValue(lineContent,List.class);
                        }catch (Exception e){
                            System.out.println(e.getMessage());
                        }
                        matchMap.put(key,list);
                    }
                }
            }
            if(matchMap.size()>0){
                page.putField("matchMap",matchMap);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Site getSite() {
        return site;
    }
}

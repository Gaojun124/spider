package com.example.demo.processor;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaojun on 2017-5-31.
 *
 * @author gaojun
 */
@Component
public class HtmlPageProcessor implements PageProcessor {
    private final static Logger logger = LoggerFactory.getLogger(HtmlPageProcessor.class);
    private Site site = Site
            .me()
            .setRetryTimes(3)
            .setSleepTime(1000)
            //.setCharset("UTF-8")
            .setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.98 Safari/537.36")
            ;

    @Override
    public void process(Page page) {

        Selectable selectable =  page.getHtml().css("head script","src");
        String matchResultJs = selectable.regex(".*matchResult.*").toString();
        String leagueSeasonJs = selectable.regex(".*leagueSeason.*").toString();
        String url = page.getUrl().toString();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_MISSING_VALUES, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        if(url.contains("html")){
            page.putField("url",page.getUrl().toString());
            page.addTargetRequest("http://info.310win.com"+matchResultJs);
            page.addTargetRequest("http://info.310win.com"+leagueSeasonJs);
            if(matchResultJs==null||leagueSeasonJs==null){
                logger.error(page.getUrl().toString());
                logger.error(page.getRawText());
            }else {
                logger.info(page.getUrl().toString());
            }
        }else if(url.contains("matchResult")){
            logger.info(page.getUrl().toString());
            String html = page.getRawText();
            BufferedReader reader = new BufferedReader(new StringReader(html));
            String lineTxt;
            String lineTitle;
            String lineContent;
            page.putField("pageFlag","matchResult");
            try {
                while((lineTxt = reader.readLine()) != null){
                    if(lineTxt.contains("=")){
                        lineTitle = lineTxt.substring(0,lineTxt.indexOf("="));
                        lineContent = lineTxt.substring(lineTxt.indexOf(" = ")+3,lineTxt.length()-1);
                        List list = new ArrayList();
                        if(lineTitle.contains("arrLeague")||lineTitle.contains("arrCup")){
                            try{
                                list = objectMapper.readValue(lineContent,List.class);
                            }catch (Exception e){
                                System.out.println(e.getMessage());
                            }
                            page.putField("leagueId",String.valueOf(list.get(0)));
                            page.putField("league",list);
                        }else if(lineTitle.contains("arrSubLeague")||lineTitle.contains("arrCupKind")){
                            try{
                                list = objectMapper.readValue(lineContent,List.class);
                            }catch (Exception e){
                                System.out.println(e.getMessage());
                            }
                            page.putField("subLeague",list);
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }else if(url.contains("LeagueSeason")){
            logger.info(page.getUrl().toString());
            String html = page.getRawText();
            BufferedReader reader = new BufferedReader(new StringReader(html));
            String leagueId = url.substring(url.indexOf("/sea")+4,url.indexOf(".js"));
            String lineTxt = "";
            String lineContent;
            try {
                while((lineTxt = reader.readLine()) != null){
                    lineContent = lineTxt.substring(lineTxt.indexOf(" = ")+3,lineTxt.length()-1);
                    try{
                        List object = objectMapper.readValue(lineContent,List.class);
                        page.putField("pageFlag","leagueSeason");
                        page.putField("leagueId",leagueId);
                        page.putField("leagueSeason", object);
                    }catch (Exception e){
                        System.out.println(url);
                        System.out.println(e.getMessage());
                    }

                }
            } catch (IOException e) {
                System.out.println(lineTxt);
                System.out.println(e.getMessage());
            }
        }

    }

    @Override
    public Site getSite() {
        return site;
    }
}

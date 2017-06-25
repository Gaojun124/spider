package com.example.demo.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * Created by Administrator on 2017/06/10.
 *
 * @author Administrator
 */
@Component
public class DanChangDateProcessor implements PageProcessor {
    private final static Logger logger = LoggerFactory.getLogger(DanChangDateProcessor.class);

    private Site site = Site
            .me()
            .setRetryTimes(3)
            .setSleepTime(1000)
            .setCharset("UTF-8")
            //.setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.98 Safari/537.36")
            ;

    @Override
    public void process(Page page) {
        String title = page.getHtml().$("head title").toString();
        String url = page.getUrl().toString();
        String date = url.substring(url.indexOf("issueNum=")+9);
        logger.info(url);
        if(title!=null&&!title.equals("运行时错误")){
            logger.info(date);
            page.putField("date",date);
        }
    }

    @Override
    public Site getSite() {
        return site;
    }
}

package com.example.demo.pipeline;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * Created by Administrator on 2017/06/10.
 *
 * @author Administrator
 */
@Component
public class DanChangDatePipeline implements Pipeline {

    private final static Logger logger = LoggerFactory.getLogger(CupDataPipeline.class);

    @Override
    public void process(ResultItems resultItems, Task task) {

    }
}

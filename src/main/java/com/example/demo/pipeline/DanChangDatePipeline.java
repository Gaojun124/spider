package com.example.demo.pipeline;

import com.example.demo.dao.entity.DanChangDate;
import com.example.demo.dao.repository.DanChangDateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private DanChangDateRepository danChangDateRepository;

    private final static Logger logger = LoggerFactory.getLogger(CupDataPipeline.class);

    @Override
    public void process(ResultItems resultItems, Task task) {
        String date = resultItems.get("date");
        DanChangDate danChangDate = new DanChangDate();
        danChangDate.setA(date);
        danChangDateRepository.save(danChangDate);
    }
}

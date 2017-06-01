package com.example.demo.pipeline;

import com.example.demo.dao.entity.Test2;
import com.example.demo.dao.entity.Test3;
import com.example.demo.dao.repository.Test2Repository;
import com.example.demo.dao.repository.Test3Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaojun on 2017-6-1.
 *
 * @author gaojun
 */
@Component
public class HtmlPipeline implements Pipeline {
    private final Test2Repository test2Repository;
    private final Test3Repository test3Repository;

    @Autowired
    public HtmlPipeline(Test2Repository test2Repository, Test3Repository test3Repository) {
        this.test2Repository = test2Repository;
        this.test3Repository = test3Repository;
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        String pageFlag = resultItems.get("pageFlag");
        if(pageFlag!=null){
            if(pageFlag.equals("leagueSeason")){
                String leagueId = resultItems.get("leagueId");
                List leagueSeasonList = resultItems.get("leagueSeason");
                List<Test2> test2List = new ArrayList<>();
                for (Object o : leagueSeasonList) {
                    Test2 test2 = new Test2();
                    test2.setA(leagueId);
                    test2.setB(String.valueOf(o));
                    test2List.add(test2);
                }
                test2Repository.save(test2List);
            }else if(pageFlag.equals("matchResult")){
                String leagueId = resultItems.get("leagueId");
                List subLeagueList = resultItems.get("subLeague");
                List<Test3> test3List = new ArrayList<>();
                if(subLeagueList!=null){
                    for (Object o : subLeagueList) {
                        List list = (List) o;
                        Test3 test3 = new Test3();
                        test3.setId(Long.valueOf(leagueId+String.valueOf(list.get(0))));
                        test3.setA(leagueId);
                        test3.setB(String.valueOf(list.get(0)));
                        test3.setC(String.valueOf(list.get(1)));
                        test3List.add(test3);
                    }
                    test3Repository.save(test3List);
                }
            }
        }

    }
}

package com.example.demo.pipeline;

import com.example.demo.dao.entity.League;
import com.example.demo.dao.entity.Match;
import com.example.demo.dao.entity.Team;
import com.example.demo.dao.repository.LeagueRepository;
import com.example.demo.dao.repository.MatchRepository;
import com.example.demo.dao.repository.TeamRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by gaojun on 2017-6-1.
 *
 * @author gaojun
 */
@Component
public class CupDataPipeline implements Pipeline {
    private final static Logger logger = LoggerFactory.getLogger(CupDataPipeline.class);

    @Autowired
    private LeagueRepository leagueRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private MatchRepository matchRepository;

    @Override
    public void process(ResultItems resultItems, Task task) {
    String LeagueSeason = resultItems.get("LeagueSeason");
        if(resultItems.get("league")!=null){
            List  list = resultItems.get("league");
            League league = new League();
            league.setA(String.valueOf(list.get(0)));
            league.setB(String.valueOf(list.get(1)));
            league.setC(String.valueOf(list.get(3)));
            league.setD(String.valueOf(list.get(7)));
            league.setE(String.valueOf(list.get(9)));
            league.setF(String.valueOf(list.get(11)));
            league.setG(String.valueOf(list.get(12)));
            try{
                leagueRepository.save(league);
            }catch (Exception e){
                logger.error(e.getMessage());
            }

        }

        if(resultItems.get("team")!=null){
            List  list = resultItems.get("team");
            List<Team> teamList = new ArrayList<>();
            for (Object o : list) {
                List olist = (List) o;
                Team team = new Team();
                team.setA(String.valueOf(olist.get(0)));
                team.setB(String.valueOf(olist.get(1)));
                team.setC(String.valueOf(olist.get(2)));
                team.setD(String.valueOf(olist.get(3)));
                teamList.add(team);
            }
            try{
                teamRepository.save(teamList);
            }catch (Exception e){
                logger.error(e.getMessage());
            }

        }

        if(resultItems.get("matchMap")!=null){
            Map<String,List> map = resultItems.get("matchMap");
            List<Match> matchList = new ArrayList<>();
            for (Map.Entry<String, List> entry : map.entrySet()) {
                for (Object o : entry.getValue()) {
                    List olist = (List) o;
                    Match match = new Match();
                    match.setA(String.valueOf(olist.get(0)));
                    match.setB(String.valueOf(olist.get(1)));
                    match.setC(LeagueSeason);
                    match.setD(entry.getKey());
                    match.setE(String.valueOf(olist.get(3)));
                    match.setF(String.valueOf(olist.get(4)));
                    match.setG(String.valueOf(olist.get(5)));
                    match.setH(String.valueOf(olist.get(6)));
                    match.setI(String.valueOf(olist.get(7)));
                    match.setJ(String.valueOf(olist.get(8)));
                    match.setK(String.valueOf(olist.get(9)));
                    match.setL(String.valueOf(olist.get(10)));
                    match.setM(String.valueOf(olist.get(11)));
                    match.setN(String.valueOf(olist.get(12)));
                    match.setO(String.valueOf(olist.get(13)));
                    matchList.add(match);
                }
            }
            try{
                matchRepository.save(matchList);
            }catch (Exception e){
                logger.error(e.getMessage());
            }

        }

    }
}

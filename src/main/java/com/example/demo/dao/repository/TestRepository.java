package com.example.demo.dao.repository;

import com.example.demo.dao.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by gaojun on 2017-5-31.
 *
 * @author gaojun
 */
@Repository
@Transactional
public interface TestRepository  extends JpaRepository<Test,Long>{
    @Query(value = "SELECT concat('http://info.310win.com/cn/' ,case when g=1 then 'League'when g= 0 then 'SubLeague' else 'CupMatch' end , '/' , c , '.html') FROM t_test"
            ,nativeQuery = true)
    List<String> queryUrl();

    @Query(value = "SELECT concat('http://info.310win.com/jsData/matchResult/',c.b,'/s',a.c,'_',b.b ,'.js')FROM t_test a LEFT JOIN t_test3 b ON a.c = b.a LEFT JOIN t_test2 c ON a.c = c.a WHERE a.g=0"
            ,nativeQuery = true)
    List<String> querySubLeagueUrl();

    @Query(value = "SELECT concat('http://info.310win.com/jsData/matchResult/',c.b,'/s',a.c,'.js')FROM t_test a LEFT JOIN t_test2 c ON a.c = c.a WHERE a.g=1"
            ,nativeQuery = true)
    List<String> queryLeagueUrl();

    @Query(value = "SELECT concat('http://info.310win.com/jsData/matchResult/',c.b,'/c',a.c,'.js')FROM t_test a LEFT JOIN t_test2 c ON a.c = c.a WHERE a.g=2"
            ,nativeQuery = true)
    List<String> queryCupUrl();
}

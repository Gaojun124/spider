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
    @Query(value = "SELECT 'http://info.310win.com/cn/' || decode(g, 1, 'League', 0, 'SubLeague', 2, 'CupMatch') || '/' || c || '.html' FROM T_TEST"
            ,nativeQuery = true)
    List<String> queryUrl();
}

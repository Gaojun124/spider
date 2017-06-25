package com.example.demo.dao.repository;

import com.example.demo.dao.entity.DanChangDate;
import com.example.demo.dao.entity.League;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by gaojun on 2017-5-31.
 *
 * @author gaojun
 */
@Repository
@Transactional
public interface DanChangDateRepository extends JpaRepository<DanChangDate,Long>{
}

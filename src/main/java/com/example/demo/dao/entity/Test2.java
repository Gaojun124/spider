package com.example.demo.dao.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by gaojun on 2017-6-1.
 *
 * @author gaojun
 */
@Getter
@Setter
@Entity
@Table(name = "t_test2")
public class Test2 {
    @Id
    @GeneratedValue
    private Long id;
    private String a;
    private String b;
}

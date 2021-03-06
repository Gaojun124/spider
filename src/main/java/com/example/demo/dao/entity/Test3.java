package com.example.demo.dao.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by gaojun on 2017-6-1.
 * @author gaojun 
 */
@Getter
@Setter
@Entity
@Table(name = "t_test3")
public class Test3 {
    @Id
    @GeneratedValue
    private Long id;
    private String a;
    private String b;
    private String c;
}
package com.example.demo.dao.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by gaojun on 2017-5-31.
 *
 * @author gaojun
 */
@Getter
@Setter
@Entity
@Table(name = "T_TEST")
public class Test {
    @Id
    @GeneratedValue
    private Long id;
    private String a;
    private String b;
    private String c;
    private String d;
    private String e;
    private String f;
    private String g;
}

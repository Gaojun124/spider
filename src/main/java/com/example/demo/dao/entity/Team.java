package com.example.demo.dao.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by Administrator on 2017/06/03.
 *
 * @author Administrator
 */
@Getter
@Setter
@Entity
@Table(name = "t_team")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String a;
    private String b;
    private String c;
    private String d;
    private String e;
}

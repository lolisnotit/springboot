package com.example.demo.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;


@Entity
@Data
@Table(name = "TASK")
public class Task implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "contents")
    private String contents;

    @Column(name= "task")
    private String task;
    @Column(name = "update_date")
    private Date updateDate;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "delete_date")
    private Date deleteDate;
}


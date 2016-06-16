package com.example.testing.rxjavalearn.bean;

import java.util.List;

/**
 * author: baiiu
 * date: on 16/5/20 15:57
 * description:
 */
public class Student {
  public String name;
  public List<Course> courseList;

  public Student(String name, List<Course> courseList) {
    this.name = name;
    this.courseList = courseList;
  }
}

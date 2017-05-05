package com.example.testing.rxjavalearn.bean;

/**
 * auther: baiiu
 * time: 16/5/16 16 22:31
 * description:
 */
public class Repo {
  String id;
  String name;
  String full_name;
  String html_url;
  String description;
  boolean fork;
  String forks_count;

  @Override public String toString() {
    return "Repo{" +
        "id='" + id + '\'' +
        ", name='" + name + '\'' +
        ", full_name='" + full_name + '\'' +
        ", html_url='" + html_url + '\'' +
        ", description='" + description + '\'' +
        ", fork=" + fork +
        ", forks_count='" + forks_count + '\'' +
        '}';
  }
}

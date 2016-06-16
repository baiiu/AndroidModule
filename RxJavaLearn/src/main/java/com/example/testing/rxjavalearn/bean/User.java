package com.example.testing.rxjavalearn.bean;

/**
 * author: baiiu
 * date: on 16/5/16 14:46
 * description:
 */
public class User {
  public String login;
  public String id;
  public String location;
  public String email;
  public String followers;



  @Override public String toString() {
    return "User{" +
        "login='" + login + '\'' +
        ", id='" + id + '\'' +
        ", location='" + location + '\'' +
        ", email='" + email + '\'' +
        ", followers='" + followers + '\'' +
        '}';
  }
}

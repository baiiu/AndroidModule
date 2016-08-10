package com.example.testing.myapplication.bean;

import java.util.List;

/**
 * auther: baiiu
 * time: 16/5/16 16 22:46
 * description:
 */
public class GankIOHistory {
  boolean error;
  List<String> results;

  @Override public String toString() {
    return "GankIOHistory{" +
        "error=" + error +
        ", results=" + results +
        '}';
  }
}

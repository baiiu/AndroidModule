package com.example.testing.myapplication.bean;

import java.util.List;

/**
 * author: baiiu
 * date: on 16/5/17 17:50
 * description:
 */
public class GankIODay {
  public List<String> category;
  boolean error;

  @Override public String toString() {
    return "GankIODay{" +
        "category=" + category +
        ", error=" + error +
        '}';
  }
}

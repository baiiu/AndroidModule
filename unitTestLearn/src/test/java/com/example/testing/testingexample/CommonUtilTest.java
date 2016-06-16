package com.example.testing.testingexample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * author: baiiu
 * date: on 16/4/29 17:51
 * description:
 * CommonUtil中一部分没有依赖framework,在此处测.
 */
public class CommonUtilTest {
  private List<String> emptyList;
  private List<String> notEmptyList;
  private List<String> notEmptyListAnother;

  @Before public void setUp() throws Exception {
    emptyList = Collections.emptyList();
    notEmptyList = Arrays.asList("哈哈", "哈哈哈");
    notEmptyListAnother = Arrays.asList("哈哈哈", "哈哈哈", "哈哈");
  }

  @Test public void testIsListEmpty() throws Exception {
    assertTrue(CommonUtil.isEmpty(emptyList));
    assertFalse(CommonUtil.isEmpty(notEmptyList));
  }

  @Test public void testIsAllListEmpty() throws Exception {
    assertTrue(CommonUtil.isAllEmpty(emptyList, Collections.emptyList(), new ArrayList()));
    assertFalse(CommonUtil.isAllEmpty(emptyList, notEmptyList));
  }

  @Test public void testIsOneListEmpty() throws Exception {
    assertTrue(CommonUtil.isOneEmpty(emptyList, Collections.emptyList()));
    assertTrue(CommonUtil.isOneEmpty(emptyList, notEmptyList));
    assertFalse(CommonUtil.isOneEmpty(notEmptyList, notEmptyListAnother));
  }

  /*
  TextUtils is not mocked
   */
  public void testIsStringEmpty() throws Exception {
  }

  public void testIsAllStringEmpty() throws Exception {
  }

  public void testIsOneStringEmpty() throws Exception {
  }
}
package com.example.testing.testingexample;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * author: baiiu
 * date: on 16/4/27 14:15
 * description: Java单元测试类.Local Unit Test,对frameword毫无依赖
 */
public class LocalUnitTest {

  private Calculator calculator;

  @Before public void setUp() throws Exception {
    calculator = new Calculator();
  }

  @Test public void testSum() throws Exception {
    Assert.assertEquals(6D, calculator.sum(1D, 5D));
  }

  @Test public void testSubtract() throws Exception {
    Assert.assertEquals(1D, calculator.subtract(3D, 2D));
  }

  @Test public void testDivide() throws Exception {
    Assert.assertEquals(5D, calculator.divide(10D, 2D));
  }

  @Test public void testMultiply() throws Exception {
    Assert.assertEquals(6D, calculator.multiply(2D, 3D));
  }
}
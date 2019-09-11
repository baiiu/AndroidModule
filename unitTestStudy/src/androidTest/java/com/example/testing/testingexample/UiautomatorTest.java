package com.example.testing.testingexample;

/**
 * author: baiiu
 * date: on 16/4/27 15:35
 * description: uiautomator测试,请将屏幕滑到包含该app的页面
 */
//public class UiautomatorTest extends InstrumentationTestCase {
//  private UiDevice device;
//
//  @Override public void setUp() throws Exception {
//    device = UiDevice.getInstance(getInstrumentation());
//    //device.pressHome();
//    //device.wait(Until.hasObject(By.desc("Apps")), 3000);
//
//    //UiObject2 appsButton = device.findObject(By.desc("Apps"));
//    //if (appsButton != null) {
//    //  appsButton.click();
//    //}
//
//    device.wait(Until.hasObject(By.text("TestingExample")), 3000);
//    UiObject2 TestingExampleApp = device.findObject(By.text("TestingExample"));
//    TestingExampleApp.click();
//  }
//
//  public void testSayHello() throws Exception {
//
//    device.wait(Until.hasObject(By.text("hello_world")), 3000);
//    //UiObject2 targetTextView = device.findObject(By.text("hello_world"));
//
//    device.wait(Until.hasObject(By.text("Enter your name here")), 3000);
//    UiObject2 editText = device.findObject(By.text("Enter your name here"));
//    editText.setText("Peter");
//
//    device.wait(Until.hasObject(By.text("Say hello!")), 3000);
//    UiObject2 button = device.findObject(By.text("Say hello!"));
//    button.click();
//
//    device.waitForIdle(3000);
//
//    UiObject targetTextView = device.findObject(new UiSelector().textContains("Hello"));
//    assertEquals("Hello, " + "Peter" + "!", targetTextView.getText());
//  }
//}

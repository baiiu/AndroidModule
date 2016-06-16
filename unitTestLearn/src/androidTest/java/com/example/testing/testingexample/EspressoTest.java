package com.example.testing.testingexample;

import android.support.test.InstrumentationRegistry;
import android.test.ActivityInstrumentationTestCase2;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * 不推荐
 */
public class EspressoTest extends ActivityInstrumentationTestCase2<MainActivity> {

  private static final String STRING_TO_BE_TYPED = "peter";
  private MainActivity activity;

  public EspressoTest() {
    super(MainActivity.class);
  }

  @Override public void setUp() throws Exception {
    super.setUp();
    /**
     * 必须调用这两行代码
     */
    injectInstrumentation(InstrumentationRegistry.getInstrumentation());
    activity = getActivity();
  }

  /**
   * 方法名必须以test开头.尽量不要继承,请使用JUnit 4 style,
   * 这样写会多写代码,并且会出错.
   * 比如此例,输入太慢了,程序就执行了.
   */
  public void testSayHello() {
    onView(withId(R.id.editText)).perform(typeText(STRING_TO_BE_TYPED),
        closeSoftKeyboard()); //line 1

    onView(withText("Say hello!")).perform(click()); //line 2

    String expectedText = "Hello, " + STRING_TO_BE_TYPED + "!";
    onView(withId(R.id.textView)).check(matches(withText(expectedText))); //line 3
  }
}
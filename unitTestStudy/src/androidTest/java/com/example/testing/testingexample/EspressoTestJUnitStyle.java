package com.example.testing.testingexample;


import androidx.test.filters.LargeTest;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


//@RunWith(AndroidJUnit4.class) @LargeTest
//public class EspressoTestJUnitStyle {
//
//  private static final String STRING_TO_BE_TYPED = "peter";
//
//  @Rule public ActivityTestRule<MainActivity> mActivityRule =
//      new ActivityTestRule<>(MainActivity.class);
//
//  @Test public void sayHello() {
//    onView(withId(R.id.editText)).perform(typeText(STRING_TO_BE_TYPED),
//        closeSoftKeyboard()); //line 1
//
//    onView(withText("Say hello!")).perform(click()); //line 2
//
//    String expectedText = "Hello, " + STRING_TO_BE_TYPED + "!";
//    onView(withId(R.id.textView)).check(matches(withText(expectedText))); //line 3
//  }
//}
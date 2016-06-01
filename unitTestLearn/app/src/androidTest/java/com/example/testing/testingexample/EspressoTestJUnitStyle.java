package com.example.testing.testingexample;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class) @LargeTest public class EspressoTestJUnitStyle {

  private static final String STRING_TO_BE_TYPED = "peter";

  @Rule public ActivityTestRule<MainActivity> mActivityRule =
      new ActivityTestRule<>(MainActivity.class);

  @Test public void sayHello() {
    onView(withId(R.id.editText)).perform(typeText(STRING_TO_BE_TYPED),
        closeSoftKeyboard()); //line 1

    onView(withText("Say hello!")).perform(click()); //line 2

    String expectedText = "Hello, " + STRING_TO_BE_TYPED + "!";
    onView(withId(R.id.textView)).check(matches(withText(expectedText))); //line 3
  }
}
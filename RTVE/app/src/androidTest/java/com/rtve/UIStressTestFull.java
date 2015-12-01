package com.rtve;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.rtve.ui.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class UIStressTestFull {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule(MainActivity.class);

    @Test
    public void adding100CamerasTest() {
        String[] testNames = makeTestArray();

        for (String testName: testNames) {
            onView(withId(R.id.action_addCam)).perform(click());
            onView(withId(R.id.dialog_add_camera_camera_name)).perform(typeText(testName)).perform(closeSoftKeyboard());
            onView(withText("OK")).perform(click());
        }
        onView(withText("RTVE")).check(matches(isDisplayed()));
    }


    public String[] makeTestArray(){
        String[] test = {"qwertyuiop","asdfghjkl","zxcvbnm",",.","1243567890","@#$%-+()","*\"':;!?","_/.",
                ",<>.","Sony","Canon","Nikon","QWERTYUIOP2","ASDFGHJKKL2","ZXCVBNM2","abatement","abates","abating","abattoir","abattoirs","abbess",
                "zloty","zodiacal","semaphore","semaphores","semaphoring","semblance",
                "semblances","semen","semester","semiconductor","endTest!"};
        return test;
    }
}


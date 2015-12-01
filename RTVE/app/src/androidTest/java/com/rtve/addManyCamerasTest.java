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
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/*  adding100CamerasTest()

    1. Is there a limit to how many configurations can be created? Is it a reasonable limit?
    This test is done using the testNames array to automate the creation of 100 camera configurations.

    Result:PASS

    */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class addManyCamerasTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule(MainActivity.class);

    @Test
    public void adding100CamerasTest() {
        String[] testNames = makeTestArray();

        for (String testName: testNames) {
            onView(withId(R.id.action_addCam)).perform(click());
            onView(withId(R.id.dialog_add_camera_camera_name)).perform(typeText(testName));
            onView(withText("OK")).perform(click());
        }
        onView(withText("RTVE")).check(matches(isDisplayed()));
    }


    public String[] makeTestArray(){
        String[] test = {"qwertyuiop","asdfghjkl","zxcvbnm",",.","1243567890","@#$%-+()","*\"':;!?","_/.",
                ",<>.","Sony","Canon","Nikon","QWERTYUIOP2","ASDFGHJKKL2","ZXCVBNM2","abatement","abates","abating","abattoir","abattoirs","abbess",
                "abbey","abbeys","paragons","paragraph","paragraphing","paragraphs","parakeet","parakeets","input",
                "inputs","inputting","inquest","inquests","inquire","inquired","gelatin","gelatine",
                "gelatinous","gelding","geldings","gelignite","gelled","gels","evidently","evil","evildoer","evilly",
                "evilness","evils","evince","evinced","evinces","evincing","dotted","dottiness","dotting",
                "dotty","double","doubled","doubler","doubles","doublespeak","syndicates","syndication",
                "syndromes","synergism","synergistic","tackier","tackiest","tackiness","tacking","tackle",
                "tackled","waterproof","waterproofed","waterproofing","waterproofs","zip",
                "zipped","zipper","zipping","zippy","zips","zirconium",
                "zloty","zodiacal","semaphore","semaphores","semaphoring","semblance",
                "semblances","semen","semester","semiconductor","endTest!"};
        return test;
    }
}


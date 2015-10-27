package com.rtve.coreTest;
import android.content.Context;
import android.test.AndroidTestCase;
import android.test.mock.MockContext;

import com.rtve.common.CameraConfig;
import com.rtve.common.CameraConfigList;
import com.rtve.common.CameraFactory;
import com.rtve.common.LensType;
import com.rtve.core.storage.*;
import org.junit.Test;


import static org.junit.Assert.*;

/**
 * Created by Arjun on 10/27/2015.
 */
public class CameraConfigSaverTest {
    @Test
    public void configSaverTest(){
        CameraConfigSaver testConfigLoader = new CameraConfigSaver(); //This constructor was temporarily made public for testing purposes
        AndroidTestCase test = new AndroidTestCase();
        Context c = new MockContext();

        CameraConfigList testList = new CameraConfigList();
        String name = "testLensNarrowCamera";
        String name2 = "testLensNarrowCamera";
        LensType lens = LensType.NARROW;
        CameraConfig element1 = CameraFactory.createCamera(name, lens);
        CameraConfig element2 = CameraFactory.createCamera(name2, lens);
        testList.add(element1);
        testList.add(element2);

        assertTrue(testConfigLoader.save(c, testList, "testLoadout"));

    }
}

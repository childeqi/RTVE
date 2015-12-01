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
        AndroidTestCase test = new AndroidTestCase();
        Context c = new MockContext();

        CameraConfigList testList = new CameraConfigList();
        String name = "testLensNarrowCamera";
        String name2 = "testLensNarrowCamera";
        LensType lens = LensType.TIGHT;
        CameraConfig element1 = CameraFactory.createCamera(name, true, lens);
        CameraConfig element2 = CameraFactory.createCamera(name2, false, lens);
        testList.add(element1);
        testList.add(element2);

        try
        {
            CameraConfigSaver.save(c, testList, "testLoadout");
        }
        catch (Exception e)
        {
            fail();
        }

    }
}

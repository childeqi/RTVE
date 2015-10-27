package com.rtve.coreTest;
import com.rtve.common.*;
import org.junit.Test;
import static org.junit.Assert.*;


/**
 * Created by Arjun on 10/25/2015.
 */

public class CameraConfigTest {

    @Test
    public void narrowConfigLensTest() {
        LensType lens = LensType.NARROW;
        String name = "testLensNarrowCamera";
        CameraConfig testNarrow = CameraFactory.createCamera(name, lens);
        assertEquals(LensType.NARROW, testNarrow.getLensType());
    }

    @Test
    public void narrowConfigNameTest() {
        LensType lens = LensType.NARROW;
        String name = "testLensNarrowCamera";
        CameraConfig testNarrow = CameraFactory.createCamera(name, lens);

        assertEquals("testLensNarrowCamera", testNarrow.getName());
    }


    @Test
    public void wideConfigLensTest() {
        LensType lens = LensType.WIDE;
        String name = "testLensWideCamera";
        CameraConfig testNarrow = CameraFactory.createCamera(name, lens);

        assertEquals(LensType.WIDE, testNarrow.getLensType());
    }

    @Test
    public void wideConfigNameTest() {
        LensType lens = LensType.WIDE;
        String name = "testLensWideCamera";
        CameraConfig testNarrow = CameraFactory.createCamera(name, lens);

        assertEquals("testLensWideCamera", testNarrow.getName());
    }

}

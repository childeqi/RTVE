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
        LensType lens = LensType.TIGHT;
        String name = "testLensNarrowCamera";
        CameraConfig testNarrow = CameraFactory.createCamera(name, true, lens);
        assertEquals(LensType.TIGHT, testNarrow.getLensType());
    }

    @Test
    public void narrowConfigNameTest() {
        LensType lens = LensType.TIGHT;
        String name = "testLensNarrowCamera";
        CameraConfig testNarrow = CameraFactory.createCamera(name, true, lens);

        assertEquals("testLensNarrowCamera", testNarrow.getName());
    }


    @Test
    public void wideConfigLensTest() {
        LensType lens = LensType.WIDE;
        String name = "testLensWideCamera";
        CameraConfig testNarrow = CameraFactory.createCamera(name, true, lens);

        assertEquals(LensType.WIDE, testNarrow.getLensType());
    }

    @Test
    public void wideConfigNameTest() {
        LensType lens = LensType.WIDE;
        String name = "testLensWideCamera";
        CameraConfig testNarrow = CameraFactory.createCamera(name, true, lens);

        assertEquals("testLensWideCamera", testNarrow.getName());
    }

}

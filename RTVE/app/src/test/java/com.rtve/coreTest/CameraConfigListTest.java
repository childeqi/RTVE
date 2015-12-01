package com.rtve.coreTest;
import com.rtve.common.*;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Arjun on 10/26/2015.
 */
public class CameraConfigListTest {
    @Test
    public void testAdd() {
        CameraConfigList testList = new CameraConfigList();
        String name = "testLensNarrowCamera";
        String name2 = "testLensNarrowCamera";
        String name3 = "testLensNarrowCamera";
        String name4 = "testLensNarrowCamera";
        LensType lens = LensType.TIGHT;
        CameraConfig element1 = CameraFactory.createCamera(name, true, lens);
        CameraConfig element2 = CameraFactory.createCamera(name2, true, lens);
        CameraConfig element3 = CameraFactory.createCamera(name3, false, lens);
        CameraConfig element4 = CameraFactory.createCamera(name4, false, lens);
        testList.add(element1);
        testList.add(element2);
        testList.add(element3);
        testList.add(element4);
        assertEquals(4,testList.size());
    }

    @Test
    public void testRemove() {
        CameraConfigList testList = new CameraConfigList();
        String name = "testLensNarrowCamera";
        String name2 = "testLensNarrowCamera";
        String name3 = "testLensNarrowCamera";
        String name4 = "testLensNarrowCamera";
        LensType lens = LensType.TIGHT;
        CameraConfig element1 = CameraFactory.createCamera(name, true, lens);
        CameraConfig element2 = CameraFactory.createCamera(name2, true, lens);
        CameraConfig element3 = CameraFactory.createCamera(name3, false, lens);
        CameraConfig element4 = CameraFactory.createCamera(name4, false, lens);
        testList.add(element1);
        testList.add(element2);
        testList.add(element3);
        testList.add(element4);
        testList.remove(element4);
        testList.remove(element3);
        assertEquals(2, testList.size());
    }

    @Test
    public void testClear() {
        CameraConfigList testList = new CameraConfigList();
        String name = "testLensNarrowCamera";
        String name2 = "testLensNarrowCamera";
        String name3 = "testLensNarrowCamera";
        String name4 = "testLensNarrowCamera";
        LensType lens = LensType.TIGHT;
        CameraConfig element1 = CameraFactory.createCamera(name, true, lens);
        CameraConfig element2 = CameraFactory.createCamera(name2, true, lens);
        CameraConfig element3 = CameraFactory.createCamera(name3, false, lens);
        CameraConfig element4 = CameraFactory.createCamera(name4, false, lens);
        testList.add(element1);
        testList.add(element2);
        testList.add(element3);
        testList.add(element4);
        testList.clear();
        assertEquals(0, testList.size());
    }
}

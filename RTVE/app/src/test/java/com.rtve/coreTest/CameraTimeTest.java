package com.rtve.coreTest;

import com.rtve.common.*;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Arjun on 10/26/2015.
 */

public class CameraTimeTest {
    @Test
    public void CameraTimeTest() {
        CameraTime t = new CameraTime();
        CameraTime t2 = new CameraTime();
        long testValue = t2.getMillis()-t.getMillis();
        assertEquals(testValue,t.minus(t2).getMillis());
    }
}

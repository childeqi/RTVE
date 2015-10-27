package com.rtve.coreTest;
import android.content.Context;
import android.test.AndroidTestCase;
import android.test.mock.MockContext;
import com.rtve.core.storage.*;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Arjun on 10/27/2015.
 * A lot more in depth tests will be done on this class next sprint.
 * This basically made sure convertConfigListToConfigNameList(List<java.io.File> configFileList) doesn't throw errors.
 */

public class CameraConfigLoaderTest {
    @Test
    public void configLoaderTest(){
        AndroidTestCase test = new AndroidTestCase();
        Context c = new MockContext();
        CameraConfigLoader testLoad = new CameraConfigLoader(); //Made temporarily public for testing
        assertNotNull(testLoad.getAvailableConfigNames(c));
    }
}

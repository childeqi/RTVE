package com.rtve.coreTest;

import android.content.Context;
import android.os.Environment;
import android.test.mock.MockContext;

import com.rtve.common.CameraTime;
import com.rtve.common.TimeSlot;
import com.rtve.core.XMLExporter;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

/**
 * Created by mdomonic on 10/13/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class XMLExporterTest
{
    @Mock
    TimeSlot timeSlot;

    @Mock
    CameraTime startTime;

    @Mock
    CameraTime endTime;

    String xmlFileName = "output.xml";

    //Deletes the output file if it exists already.
    @After
    public void tearDown()
    {
        File xml = new File(getExternalDir(), xmlFileName);
        if(xml.exists())
        {
            xml.delete();
        }
    }

    //Make sure save() creates an xml file when no files existed already.
    @Test
    public void test1_xmlExporter_save()
    {
        List<TimeSlot> timeSlotList = setUpTimeSlotList(0, 10);
        File output = new File(getExternalDir(), xmlFileName);
        Context c = new MockContext();
        XMLExporter export = new XMLExporter();
        export.save(timeSlotList, c);
        assertThat(output.exists(), is(true));
        assertThat(output.isFile(), is(true));
    }

    //Make sure save() still saves the xml file on discovery of an existing xml.
    @Test
    public void test2_xmlExporter_save()
    {
        List<TimeSlot> firstList = setUpTimeSlotList(0, 10);
        List<TimeSlot> secondList = setUpTimeSlotList(10, 100);

        File second = new File(getExternalDir(), xmlFileName);
        Context c = new MockContext();
        XMLExporter export = new XMLExporter();
        export.save(firstList, c);
        export.save(secondList, c);
        assertThat(second.exists(), is(true));
        assertThat(second.isFile(), is(true));
    }

    //Make sure the xml file save() creates contains time info after overwriting an existing xml.
    @Test
    public void test3_xmlExporter_save()
    {
        List<TimeSlot> firstList = setUpTimeSlotList(0, 10);
        List<TimeSlot> secondList = setUpTimeSlotList(11, 100);
        Context c = new MockContext();
        XMLExporter export = new XMLExporter();
        export.save(firstList, c);
        export.save(secondList, c);

        String str = getFileData(xmlFileName);
        System.out.println(str);
        assertNotEquals(str.indexOf("11"), -1);
        assertNotEquals(str.indexOf("100"), -1);
        assertNotEquals(str.indexOf("timeSlotString"), -1);
    }

    //Make sure the xml file save() creates contains time info.
    @Test
    public void test4_xmlExporter_save()
    {
        List<TimeSlot> timeSlotList = setUpTimeSlotList(0, 10);
        Context c = new MockContext();
        XMLExporter export = new XMLExporter();
        export.save(timeSlotList, c);

        String str = getFileData(xmlFileName);
        System.out.println(str);
        assertNotEquals(str.indexOf("10"), -1);
        assertNotEquals(str.indexOf("0"), -1);
        assertNotEquals(str.indexOf("timeSlotString"), -1);
    }

    public ArrayList<TimeSlot> setUpTimeSlotList(long begin, long end)
    {
        ArrayList<TimeSlot> timeSlotList = new ArrayList<>();
        when(timeSlot.getEndTime()).thenReturn(endTime);
        when(timeSlot.getStartTime()).thenReturn(startTime);
        when(timeSlot.toString()).thenReturn("timeSlotString");
        when(endTime.getMillis()).thenReturn(end);
        when(startTime.getMillis()).thenReturn(begin);

        timeSlotList.add(timeSlot);
        return timeSlotList;
    }

    public String getFileData(String fileName)
    {
        StringBuilder str = new StringBuilder();
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(new File(getExternalDir(), fileName)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            while(in.ready())
            {
                str.append(in.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str.toString();
    }

    public File getExternalDir()
    {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "Exported Premiere XML");

        return file;
    }
}

package com.rtve.coreTest;

import com.rtve.common.CameraTime;
import com.rtve.common.TimeSlot;
import com.rtve.core.XMLExporter;

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

    //Make sure save() creates an xml file.
    @Test
    public void test1_xmlExporter_save()
    {
        System.out.println("Working");
        List<TimeSlot> timeSlotList = new ArrayList<>();
        File output = new File("output.xml");

        when(timeSlot.getEndTime()).thenReturn(endTime);
        when(timeSlot.getStartTime()).thenReturn(startTime);
        when(timeSlot.toString()).thenReturn("timeSlotString");
        when(endTime.getMillis()).thenReturn((long) 10);
        when(startTime.getMillis()).thenReturn((long) 0);

        timeSlotList.add(timeSlot);

        XMLExporter export = new XMLExporter();
        export.save(timeSlotList);
        assertThat(output.exists(), is(true));
    }

    //Make sure the xml file save creates contains time info.
    @Test
    public void test2_xmlExporter_save()
    {
        List<TimeSlot> timeSlotList = new ArrayList<>();
        File output = new File("output.xml");

        when(timeSlot.getEndTime()).thenReturn(endTime);
        when(timeSlot.getStartTime()).thenReturn(startTime);
        when(timeSlot.toString()).thenReturn("timeSlotString");
        when(endTime.getMillis()).thenReturn((long) 10);
        when(startTime.getMillis()).thenReturn((long) 0);

        timeSlotList.add(timeSlot);

        XMLExporter export = new XMLExporter();
        export.save(timeSlotList);

        StringBuilder str = new StringBuilder();
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader("output.xml"));
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
        System.out.println(str);
        assertNotEquals(str.indexOf("10"), -1);
        assertNotEquals(str.indexOf("0"), -1);
        assertNotEquals(str.indexOf("timeSlotString"), -1);
    }
}

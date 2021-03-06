package com.rtve.core;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;

import com.rtve.common.TimeSlot;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by mdomonic on 10/8/2015.
 */
public class XMLExporter implements CoreInterface
{
    public static final String LOG_TAG = "XMLExporter";
    private String outputFileName = "RTVE_output.xml";

    public void save(List<com.rtve.common.TimeSlot> timeSlots, Context activity)
    {
        List<Clip> clips = new ArrayList<Clip>(timeSlots.size());
        List<ClipItem> clipItems = new ArrayList<ClipItem>(timeSlots.size());

        Rate rate = new Rate(30, true);
        LoggingInfo loggingInfo = new LoggingInfo("","","","");
        TimeCode timeCode = new TimeCode(rate, "00;00;00;00", 0);
        SampleCharacteristics sampleCharacteristics = new SampleCharacteristics(rate, 1024, 768);
        Format format = new Format(sampleCharacteristics);
        Video video = new Video(format, null, sampleCharacteristics);
        Media media = new Media(video);
        com.rtve.core.File file;

        int i = 0;
        for(TimeSlot timeSlot : timeSlots)
        {
            i++;
            file = new com.rtve.core.File("file"+i, timeSlot.toString(), "./dummy_icon.jpg", rate, timeCode, media);
            ClipItem clipItem = new ClipItem(timeSlot.toString(), rate, timeSlot.getStartTime().getMillis(), timeSlot.getEndTime().getMillis(), file, loggingInfo);
            clipItems.add(clipItem);
        }

        Track track = new Track(clipItems);
        video = new Video(format, track, sampleCharacteristics);
        media = new Media(video);

        for(TimeSlot timeSlot : timeSlots)
        {
            Clip clip = new Clip(rate, timeSlot.toString(), media, loggingInfo);
            clips.add(clip);
        }

        Sequence sequence = new Sequence("sequence-1", rate, "Sequence", media, timeCode);
        Children children = new Children(sequence, clips);
        Project project = new Project(children, "proj_1");
        xmeml xmeml = new xmeml(project, 4);

        Serializer serializer = new Persister();

        try
        {
            FileOutputStream outputXML = activity.openFileOutput(outputFileName, Context.MODE_PRIVATE);
            serializer.write(xmeml, outputXML);
        }
        catch(Exception e) {
            Log.e(LOG_TAG, "Error writing XML file", e);
        }
    }

    public void send(Context activity)
    {
        File xmlToSend = activity.getFileStreamPath(outputFileName);
        if(xmlToSend.exists() && xmlToSend.isFile())
        {
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setData(Uri.parse("mailto:"));
            emailIntent.setType("text/plain");
            emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(xmlToSend));
            activity.startActivity(Intent.createChooser(emailIntent, "Send email with:"));

        }
        else
        {
            Log.e(LOG_TAG, "File to send doesn't exist.");
        }
    }

    //The value of this method will be slightly off until I understand better how to account for ntsc.
    public static long getFrame(long time)
    {
        return time/30;
    }
}

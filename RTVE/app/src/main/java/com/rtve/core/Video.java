package com.rtve.core;

import org.simpleframework.xml.Element;

/**
 * Created by mdomonic on 10/9/2015.
 */
public class Video
{
    @Element (required=false)
    private Format format;

    @Element(required=false)
    private Track track;

    @Element (required=false)
    private SampleCharacteristics sampleCharacteristics;

    public Video(Format format, Track track, SampleCharacteristics sampleCharacteristics)
    {
        this.format = format;
        this.track = track;
        this.sampleCharacteristics = sampleCharacteristics;
    }
}

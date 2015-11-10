package com.rtve.core;

import org.simpleframework.xml.Element;

/**
 * Created by mdomonic on 10/9/2015.
 */
public class Clip
{
    @Element
    private Rate rate;

    @Element
    private String name;

    @Element
    private Media media;

    @Element
    private LoggingInfo loggingInfo;

    public Clip(Rate rate, String name, Media media, LoggingInfo loggingInfo)
    {
        this.rate = rate;
        this.name = name;
        this.media = media;
        this.loggingInfo = loggingInfo;

    }
}

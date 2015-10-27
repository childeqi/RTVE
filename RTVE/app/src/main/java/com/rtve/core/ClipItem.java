package com.rtve.core;

import org.simpleframework.xml.Element;

/**
 * Created by mdomonic on 10/9/2015.
 */
public class ClipItem
{
    @Element
    private String name;

    @Element
    private Rate rate;

    @Element (required=false)
    private long start;

    @Element (required=false)
    private long end;

    @Element
    private File file;

    @Element (required=false)
    private LoggingInfo loggingInfo;

    public ClipItem(String name, Rate rate, long start, long end, File file, LoggingInfo loggingInfo)
    {
        this.name = name;
        this.rate = rate;
        this.start = start;
        this.end = end;
        this.file = file;
        this.loggingInfo = loggingInfo;
    }
}

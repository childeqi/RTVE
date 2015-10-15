package com.rtve.core;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

/**
 * Created by mdomonic on 10/9/2015.
 */
public class File
{
    @Attribute
    private String id;

    @Element
    private String name;

    @Element
    private String pathurl;

    @Element
    private Rate rate;

    @Element
    private TimeCode timeCode;

    @Element
    private Media media;

    public File(String id, String name, String pathurl, Rate rate, TimeCode timeCode, Media media)
    {
        this.id = id;
        this.name = name;
        this.pathurl = pathurl;
        this.rate = rate;
        this.timeCode = timeCode;
        this.media = media;
    }
}

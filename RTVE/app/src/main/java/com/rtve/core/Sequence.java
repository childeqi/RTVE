package com.rtve.core;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

/**
 * Created by mdomonic on 10/9/2015.
 */
public class Sequence
{
    @Attribute
    private String id;

    @Element
    private Rate rate;

    @Element
    private String name;

    @Element
    private Media media;

    @Element
    private TimeCode timeCode;

    public Sequence(String id, Rate rate, String name, Media media, TimeCode timeCode)
    {
        this.id = id;
        this.rate = rate;
        this.name = name;
        this.media = media;
        this.timeCode = timeCode;
    }
}
